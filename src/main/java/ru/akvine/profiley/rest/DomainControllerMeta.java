package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.domain.CreateDomainRequest;
import ru.akvine.profiley.rest.dto.domain.ListDomainRequest;
import ru.akvine.profiley.rest.dto.domain.UpdateDomainRequest;

@RequestMapping(value = "/domains")
public interface DomainControllerMeta {
    @GetMapping
    Response list(@RequestBody @Valid ListDomainRequest request);

    @PostMapping
    Response create(@RequestBody @Valid CreateDomainRequest request);

    @PatchMapping
    Response update(@RequestBody @Valid UpdateDomainRequest request);

    @DeleteMapping
    Response delete(@RequestParam("domainName") String domainName);
}
