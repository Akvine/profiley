package ru.akvine.profiley.rest.dto.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NextPage {
    @Min(0)
    private int page;

    @Min(1)
    @Max(20)
    private int size;
}
