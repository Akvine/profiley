package ru.akvine.profiley.rest.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequest {
    @ToString.Exclude
    @NotBlank
    protected String email;

    @ToString.Exclude
    @NotBlank
    protected String password;
}
