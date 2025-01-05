package ru.akvine.profiley.rest.dto.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDomainRequest {
    @NotBlank
    private String name;
}
