package xyz.srclab.spring.boot.common.exception;

import xyz.srclab.spring.boot.common.state.StringState;
import xyz.srclab.spring.boot.common.state.StringStateHelper;

public interface ExceptionStatus extends StringState<ExceptionStatus> {

    @Override
    default ExceptionStatus withMoreDescription(String moreDescription) {
        return new ExceptionStatus() {

            @Override
            public String getCode() {
                return ExceptionStatus.this.getCode();
            }

            @Override
            public String getDescription() {
                return StringStateHelper.buildMoreDescription(ExceptionStatus.this.getDescription(), moreDescription);
            }

            @Override
            public String toString() {
                return StringStateHelper.toString(this);
            }
        };
    }
}
