package ${_package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ${_artifactId?cap_first}Application {

    public static void main(String[] args) {
        SpringApplication.run(${_artifactId?cap_first}Application.class, args);
    }

}
