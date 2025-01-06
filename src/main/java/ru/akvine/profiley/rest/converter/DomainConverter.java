package ru.akvine.profiley.rest.converter;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.profiley.components.SecurityManager;
import ru.akvine.profiley.rest.dto.domain.*;
import ru.akvine.profiley.services.domain.Domain;
import ru.akvine.profiley.services.dto.domain.CreateDomain;
import ru.akvine.profiley.services.dto.domain.ListDomains;
import ru.akvine.profiley.services.dto.domain.UpdateDomain;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainConverter {
    private final SecurityManager securityManager;

    public ListDomains convertToListDomains(ListDomainRequest request) {
        Preconditions.checkNotNull(request, "ListDomainRequest is null");
        return new ListDomains()
                .setUserId(securityManager.getCurrentUser().getId())
                .setIncludeSystem(request.isIncludeSystems());
    }

    public ListDomainResponse convertToListDomainResponse(List<Domain> domains) {
        Preconditions.checkNotNull(domains, "domains is null");
        return new ListDomainResponse().setDomains(domains.stream().map(this::buildDomainDto).toList());
    }

    public CreateDomain convertToCreateDomain(CreateDomainRequest request) {
        Preconditions.checkNotNull(request, "request is null");
        return new CreateDomain()
                .setName(request.getName())
                .setUserUuid(securityManager.getCurrentUser().getUuid());
    }

    public UpdateDomain convertToUpdateDomain(UpdateDomainRequest request) {
        Preconditions.checkNotNull(request, "request is null");
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
