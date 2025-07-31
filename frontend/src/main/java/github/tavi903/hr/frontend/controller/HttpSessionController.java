package github.tavi903.hr.frontend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("all")
@RestController
@RequestMapping("/session")
public class HttpSessionController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setAttributes(@RequestBody List<Attribute> attributes, HttpSession httpSession) {
        attributes.forEach(attr -> httpSession.setAttribute(attr.getName(), attr.getValue()));
    }

    @Value
    private static class Attribute {
        String name;
        String value;
    }

}
