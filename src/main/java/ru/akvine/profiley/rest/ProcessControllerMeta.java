package ru.akvine.profiley.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.common.Response;

@RequestMapping(value = "/processes")
public interface ProcessControllerMeta {
    @GetMapping
    Response list();
}
