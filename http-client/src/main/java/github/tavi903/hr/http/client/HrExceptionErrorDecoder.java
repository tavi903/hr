package github.tavi903.hr.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import github.tavi903.hr.exception.HrException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class HrExceptionErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 500) {
            ProblemDetail problemDetail = objectMapper.reader().readValue(
                    response.body().asReader(StandardCharsets.UTF_8), ProblemDetail.class
            );
            return new HrException(problemDetail.getDetail());
        }
        return new HrException("Undefined Error!");
    }
}
