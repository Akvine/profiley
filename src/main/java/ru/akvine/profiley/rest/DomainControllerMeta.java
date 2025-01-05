package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.domain.CreateDomainRequest;
import ru.akvine.profiley.rest.dto.domain.ListDomainRequest;

@RequestMapping(value = "/domains")
public interface DomainControllerMeta {
    @GetMapping
    Response list(@RequestBody @Valid ListDomainRequest request);

    @PostMapping
    Response create(@RequestBody @Valid CreateDomainRequest request);
}
