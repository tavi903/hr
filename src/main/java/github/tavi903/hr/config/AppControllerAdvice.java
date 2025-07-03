package github.tavi903.hr.config;

import github.tavi903.hr.HrException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * See RFC 7807 For ProblemDetail & ErrorResponse
 */
@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler
    public ErrorResponse dealWithException(Exception ex, WebRequest request) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        if (ex instanceof HrException) {
            errorMessageBuilder.append(ex.getMessage());
        }

        extractValidationErrorMessage(ex, errorMessageBuilder);
        extractGenericErrorMessage(ex, errorMessageBuilder);
        return ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, errorMessageBuilder.toString())
                .type(URI.create(request.getContextPath()))
                .build();
    }

    private static void extractGenericErrorMessage(Exception ex, StringBuilder errorMessageBuilder) {
        if (errorMessageBuilder.isEmpty()) {
            ex.printStackTrace();
            Throwable rootCause = NestedExceptionUtils.getRootCause(ex);
            errorMessageBuilder.append(rootCause != null ? rootCause.getMessage() : ex.getMessage());
        }
    }

    private static void extractValidationErrorMessage(Exception ex, StringBuilder errorMessageBuilder) {
        if (ex instanceof MissingServletRequestParameterException) {
            errorMessageBuilder.append(ex.getMessage());
        }
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getAllErrors();
            errorMessageBuilder.append(
                    allErrors.stream().map(error -> error.getObjectName() + ": " + ((FieldError) error).getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining("\n"))
            );
        }
    }
}
