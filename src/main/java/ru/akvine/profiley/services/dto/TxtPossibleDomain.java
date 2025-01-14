package ru.akvine.profiley.services.dto;


import lombok.Getter;

@Getter
public class TxtPossibleDomain extends PossibleDomain {
    private final int lineNumber;

    public TxtPossibleDomain(String domainName, int lineNumber) {
        this(domainName, lineNumber, true);
    }

    public TxtPossibleDomain(String domainName, int lineNumber, boolean correct) {
        super(domainName, correct);
        this.lineNumber = lineNumber;
    }
}
