package ru.akvine.profiley.services.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.profiley.services.domain.base.Model;
import ru.akvine.profiley.repository.entity.RuleEntity;

import java.util.regex.Pattern;

@Data
@Accessors(chain = true)
public class Rule extends Model<Long> {
    @Nullable
    private String alias;
    private Pattern pattern;
    private Domain domain;

    public Rule(RuleEntity rule) {
        super(rule);

        this.alias = rule.getAlias();
        this.pattern = Pattern.compile(rule.getPattern());
        this.domain = new Domain(rule.getDomain());
    }
}
