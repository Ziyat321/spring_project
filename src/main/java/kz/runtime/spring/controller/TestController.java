package kz.runtime.spring.controller;

import kz.runtime.spring.pojo.Human;
import kz.runtime.spring.pojo.Humans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test_controller")
public class TestController {
    private final Human[] humans = Humans.getHumans();

    @GetMapping(path = "/resource_1", produces = "text/html")
    public String firstResource(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "age", required = false) Integer age
    ) {
        String result = """
                <p>Имя: <b>%s</b></p>
                <p>Возраст: <b>%d</b></p>
                """;
        return result.formatted(name, age);
    }

    @GetMapping(path = "/resource_2", produces = "text/plain")
    public String secondResource(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "to", required = false) Integer to) {
        StringBuilder stringBuilder = new StringBuilder();

        if (from == null && to == null) {
            for (Human human : humans) {
                String name = human.getName();
                int age = human.getAge();
                stringBuilder.append(name).append(": ").append(age).append("\n");
            }
        } else {
            if (from != null && to != null) {
                for (Human human : humans) {
                    String name = human.getName();
                    int age = human.getAge();
                    if (age >= from && age <= to) {
                        stringBuilder.append(name).append(": ").append(age).append("\n");
                    }
                }
            } else if (from != null) {
                for (Human human : humans) {
                    String name = human.getName();
                    int age = human.getAge();
                    if (age >= from) {
                        stringBuilder.append(name).append(": ").append(age).append("\n");
                    }
                }
            } else {
                for (Human human : humans) {
                    String name = human.getName();
                    int age = human.getAge();
                    if (age <= to) {
                        stringBuilder.append(name).append(": ").append(age).append("\n");
                    }
                }
            }
        }

        return stringBuilder.toString();
    }
}
