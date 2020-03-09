package xyz.srclab.spring.boot.common.state;

import org.springframework.lang.Nullable;

public interface State<Code, Description, T extends State<Code, Description, T>> {

    Code getCode();

    @Nullable
    Description getDescription();

    T withMoreDescription(Description moreDescription);
}
