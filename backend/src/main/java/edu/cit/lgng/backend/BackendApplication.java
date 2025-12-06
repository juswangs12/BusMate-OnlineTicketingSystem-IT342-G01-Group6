package edu.cit.lgng.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("GOOGLE_CLIENT_ID=" + System.getenv("GOOGLE_CLIENT_ID"));
        System.out.println("GOOGLE_CLIENT_SECRET=" + System.getenv("GOOGLE_CLIENT_SECRET"));
    }

    @Bean
    public WebMvcConfigurer forwardToIndex() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/{x:[^\\.]*}")
                        .setViewName("forward:/index.html");
                registry.addViewController("/**/{x:[^\\.]*}")
                        .setViewName("forward:/index.html");
            }
        };
    }
}
