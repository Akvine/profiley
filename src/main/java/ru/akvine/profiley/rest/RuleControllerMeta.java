package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.rule.CreateRuleRequest;
import ru.akvine.profiley.rest.dto.rule.UpdateRuleRequest;

@RequestMapping(value = "/rules")
public interface RuleControllerMeta {
    @GetMapping
    Response list(@RequestParam("domainName") String domainName);

    @PostMapping
    Response create(@RequestBody @Valid CreateRuleRequest request);

    @PatchMapping
    Response update(@RequestBody @Valid UpdateRuleRequest request);

    @DeleteMapping
    Response delete(@RequestParam("uuid") String uuid);
}
