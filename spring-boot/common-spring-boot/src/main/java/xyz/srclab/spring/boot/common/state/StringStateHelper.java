package xyz.srclab.spring.boot.common.state;

import org.springframework.lang.Nullable;

public class StringStateHelper {

    public static String buildMoreDescription(@Nullable String description, String moreDescription) {
        return description == null ? moreDescription : description + "[" + moreDescription + "]";
    }

    public static <T extends StringState<T>> String toString(T state) {
        String code = state.getCode();
        String description = state.getDescription();
        return description == null ? code : code + ": " + description;
    }
}
