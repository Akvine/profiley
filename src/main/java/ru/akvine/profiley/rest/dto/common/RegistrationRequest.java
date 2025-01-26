package ru.akvine.profiley.rest.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RegistrationRequest extends AuthRequest {
}
