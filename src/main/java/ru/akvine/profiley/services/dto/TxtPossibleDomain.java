package ru.akvine.profiley.services.dto;


import lombok.Getter;

@Getter
public class TxtPossibleDomain extends PossibleDomain {
    private final int lineNumber;

    public TxtPossibleDomain(String domainName, int lineNumber) {
        super(domainName);
        this.lineNumber = lineNumber;
    }
}
