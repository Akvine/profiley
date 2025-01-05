package ru.akvine.profiley.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.profiley.rest.DomainControllerMeta;
import ru.akvine.profiley.rest.converter.DomainConverter;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.domain.CreateDomainRequest;
import ru.akvine.profiley.rest.dto.domain.ListDomainRequest;
import ru.akvine.profiley.services.DomainService;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DomainController implements DomainControllerMeta {
    private final DomainConverter domainConverter;
    private final DomainService domainService;

    @Override
    public Response list(@Valid ListDomainRequest request) {
        ListDomains listDomains = domainConverter.convertToListDomains(request);
        List<Domain> domains = domainService.get(listDomains);
        return domainConverter.convertToListDomainResponse(domains);
    }

    @Override
    public Response create(@Valid CreateDomainRequest request) {
        CreateDomain createDomain = domainConverter.convertToCreateDomain(request);
        Domain createdDomain = domainService.create(createDomain);
        return domainConverter.convertToListDomainResponse(List.of(createdDomain));
    }
}
