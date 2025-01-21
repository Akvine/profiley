package ru.akvine.profiley.rest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.domain.*;
import ru.akvine.profiley.services.domain.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.domain.UpdateDomain;
import ru.akvine.profiley.utils.Asserts;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainConverter {
    private final SecurityManager securityManager;

    public ListDomains convertToListDomains(ListDomainRequest request) {
        Asserts.isNotNull(request, "ListDomainRequest is null");
        return new ListDomains()
                .setUserUuid(securityManager.getCurrentUser().getUuid())
                .setIncludeSystem(request.isIncludeSystems());
    }

    public ListDomainResponse convertToListDomainResponse(List<Domain> domains) {
        Asserts.isNotNull(domains, "domains is null");
        return new ListDomainResponse().setDomains(domains.stream().map(this::buildDomainDto).toList());
    }

    public CreateDomain convertToCreateDomain(CreateDomainRequest request) {
        Asserts.isNotNull(request, "request is null");
        return new CreateDomain()
                .setName(request.getName())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public UpdateDomain convertToUpdateDomain(UpdateDomainRequest request) {
        Asserts.isNotNull(request, "request is null");
        return new UpdateDomain()
                .setDomainName(request.getDomainName())
                .setNewDomainName(request.getNewDomainName())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    private DomainDto buildDomainDto(Domain domainToDto) {
        return new DomainDto()
                .setName(domainToDto.getName())
                .setSystem(domainToDto.isSystem());
    }
}
