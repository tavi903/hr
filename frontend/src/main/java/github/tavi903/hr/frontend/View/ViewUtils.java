package github.tavi903.hr.frontend.View;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public final class ViewUtils {

    private ViewUtils() {}

    public static Integer getPageAttribute(HttpSession httpSession, String name) {
        return Optional.ofNullable(httpSession.getAttribute(name))
                    .map(value -> Integer.valueOf((String) value))
                .orElse(0);
    }

}
