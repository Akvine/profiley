package ru.akvine.profiley.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.enums.FileType;
import ru.akvine.profiley.enums.ProcessState;
import ru.akvine.profiley.exceptions.common.ProfilingProcessException;
import ru.akvine.profiley.providers.DetectedDomainsServicesProvider;
import ru.akvine.profiley.providers.ProfilerServiceProvider;
import ru.akvine.profiley.services.PreprocessorService;
import ru.akvine.profiley.services.ProcessFacade;
import ru.akvine.profiley.services.ProfilerFacade;
import ru.akvine.profiley.services.domain.Process;
import ru.akvine.profiley.services.domain.domain.DetectedDomain;
import ru.akvine.profiley.services.dto.DetectedDomainsStatistic;
import ru.akvine.profiley.services.dto.ProfileAction;
import ru.akvine.profiley.services.dto.ProfileFile;
import ru.akvine.profiley.services.dto.process.CreateProcess;
import ru.akvine.profiley.services.dto.process.UpdateProcess;
import ru.akvine.profiley.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfilerFacadeImpl implements ProfilerFacade {
    private final PreprocessorService preprocessorService;
    private final ProfilerServiceProvider profilerServiceProvider;
    private final ProcessFacade processFacade;
    private final DetectedDomainsServicesProvider detectedDomainsServicesProvider;

    @Override
    public DetectedDomainsStatistic profile(ProfileFile profileFile) {
        ProfileAction profileAction = preprocessorService.preprocess(profileFile);
        CreateProcess createProcess = new CreateProcess()
                .setUserUuid(profileFile.getUserUuid())
                .setFileName(profileAction.getFileName())
                .setFileExtension(profileAction.getExtension());
        Process createdProcess = processFacade.create(createProcess);

        String pid = createdProcess.getPid();
        String userUuid = createdProcess.getOwner().getUuid();
        Process startedProcess = processFacade.start(createdProcess.getPid(), createdProcess.getOwner().getUuid());

        try {
            List<? extends DetectedDomain> detectedDomains = profilerServiceProvider
                    .profilerServices()
                    .get(profileAction.getExtension())
                    .profile(profileAction);

            Date completedDate = new Date();
            UpdateProcess updateProcess = new UpdateProcess()
                    .setUserUuid(userUuid)
                    .setPid(pid)
                    .setProcessState(ProcessState.COMPLETED_SUCCESSFUL)
                    .setCompletedDate(completedDate);
            processFacade.update(updateProcess);
            detectedDomainsServicesProvider
                    .getByType(FileType.from(profileAction.getExtension()))
                    .saveAll(pid, userUuid, detectedDomains);
            return new DetectedDomainsStatistic()
                    .setDomainsCount(detectedDomains.size())
                    .setPid(pid)
                    .setProcessState(ProcessState.COMPLETED_SUCCESSFUL)
                    .setStartedDate(DateTimeUtils.toLocalDateTime(startedProcess.getStartedDate()))
                    .setCompletedDate(DateTimeUtils.toLocalDateTime(completedDate));
        } catch (Exception exception) {
            UpdateProcess updateProcess = new UpdateProcess()
                    .setPid(pid)
                    .setUserUuid(userUuid)
                    .setProcessState(ProcessState.COMPLETED_WITH_ERROR)
                    .setErrorMessage(exception.getMessage());
            processFacade.update(updateProcess);
            throw new ProfilingProcessException("Error while profiling: " + exception.getMessage());
        }
    }
}
