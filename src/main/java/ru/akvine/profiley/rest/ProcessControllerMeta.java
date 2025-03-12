package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.process.ListProcessRequest;

@RequestMapping(value = "/processes")
public interface ProcessControllerMeta {
    @GetMapping(value = "/{pid}")
    Response get(@PathVariable("pid") String pid);

    @GetMapping
    Response list(@RequestBody @Valid ListProcessRequest request);
}
