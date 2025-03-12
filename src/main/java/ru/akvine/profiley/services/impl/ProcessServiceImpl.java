package ru.akvine.profiley.services.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.exceptions.common.PIDGenerationException;
import ru.akvine.profiley.exceptions.process.ProcessNotFoundException;
import ru.akvine.profiley.repository.ProcessRepository;
import ru.akvine.profiley.repository.entity.ProcessEntity;
import ru.akvine.profiley.repository.entity.UserEntity;
import ru.akvine.profiley.services.ProcessService;
import ru.akvine.profiley.services.UserService;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.dto.process.CreateProcess;
import ru.akvine.profiley.services.dto.process.ListProcess;
import ru.akvine.profiley.services.dto.process.UpdateProcess;
import ru.akvine.profiley.utils.Asserts;
import ru.akvine.profiley.utils.generators.PIDGenerator;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {
    private final ProcessRepository processRepository;

    private final UserService userService;

    @Value("${process.pid.generation.max.attempts}")
    private int generationMaxAttempts;
    @Value("${process.pid.generation.length}")
    private int pidLength;

    @Override
    public Process get(String userUuid, String pid) {
        Asserts.isNotNull(userUuid, "userUuid is null");
        Asserts.isNotNull(pid, "pid is null");
        return new Process(verifyExists(pid, userUuid));
    }

    @Override
    public List<Process> list(ListProcess listProcess) {
        Asserts.isNotNull(listProcess, "listProcess is null");

        String userUuid = listProcess.getUserUuid();
        List<String> pids = listProcess.getPids();
        ProcessState state = listProcess.getState();

        Pageable pageable = PageRequest.of(listProcess.getPage(), listProcess.getSize());

        List<Process> processes = processRepository
                .findAll(userUuid, pageable)
                .stream()
                .map(Process::new)
                .toList();

        if (state != null) {
            processes = processes.stream()
                    .filter(process -> process.getProcessState() == state)
                    .toList();
        }

        if (CollectionUtils.isNotEmpty(pids)) {
            return processes.stream()
                    .filter(process -> pids.contains(process.getPid()))
                    .toList();
        }
        return processes;
    }

    @Override
    public Process create(CreateProcess createProcess) {
        Asserts.isNotNull(createProcess, "createProcess is null");

        String userUuid = createProcess.getUserUuid();
        UserEntity user = userService.verifyExistsByUuid(userUuid);

        String pid = generatePid(userUuid);

        ProcessEntity processEntity = new ProcessEntity()
                .setPid(pid)
                .setFileName(createProcess.getFileName())
                .setFileExtension(createProcess.getFileExtension())
                .setProcessState(ProcessState.CREATED)
                .setUser(user);

        return new Process(processRepository.save(processEntity));
    }

    @Override
    public Process update(UpdateProcess updateProcess) {
        Asserts.isNotNull(updateProcess, "updateProcess is null");

        String userUuid = updateProcess.getUserUuid();
        String pid = updateProcess.getPid();

        ProcessEntity processToUpdate = verifyExists(pid, userUuid);

        if (updateProcess.getProcessState() != null &&
                updateProcess.getProcessState() != processToUpdate.getProcessState()) {
            processToUpdate.setProcessState(updateProcess.getProcessState());
        }

        if (StringUtils.isNotBlank(updateProcess.getErrorMessage())) {
            processToUpdate.setErrorMessage(updateProcess.getErrorMessage());
        }

        if (updateProcess.getStartedDate() != null) {
            processToUpdate.setStartedDate(updateProcess.getStartedDate());
        }

        if (updateProcess.getCompletedDate() != null) {
            processToUpdate.setCompletedDate(updateProcess.getCompletedDate());
        }

        processToUpdate.setUpdatedDate(new Date());
        return new Process(processRepository.save(processToUpdate));
    }

    @Override
    public ProcessEntity verifyExists(String byPid, String byUserUuid) {
        Asserts.isNotNull(byPid, "byPis is null");
        Asserts.isNotNull(byUserUuid, "byUserUuid is null");
        return processRepository
                .find(byPid, byUserUuid)
                .orElseThrow(() -> {
                    String errorMessage = String.format(
                            "Process with PID = [%s] and user uuid = [%s] not found!",
                            byPid, byUserUuid
                    );
                    return new ProcessNotFoundException(errorMessage);
                });
    }

    private String generatePid(String userUuid) {
        for (int i = 0; i < generationMaxAttempts; ++i) {
            String pid = PIDGenerator.generate(pidLength);

            Optional<ProcessEntity> processEntityOptional = processRepository.find(pid, userUuid);
            if (processEntityOptional.isEmpty()) {
                return pid;
            }
        }

        String errorMessage = String.format(
                "Couldn't generate the PID for user with uuid = [%s]. Generation max attempts = [%s] have dried up",
                userUuid, generationMaxAttempts
        );
        throw new PIDGenerationException(errorMessage);
    }
}
