package ru.akvine.profiley.rest.dto.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationRequest extends AuthRequest {
}
