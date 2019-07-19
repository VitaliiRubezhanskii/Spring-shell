package com.homoloa;

//import org.jline.utils.AttributedString;
//import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
@EnableWebMvc
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public PromptProvider myPromptProvider() {
//        return () -> new AttributedString("Please for parsing csv write command (parse or parse-zip) and paths: csv file (or zip file) path, output json file path :>",
//                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
//    }
}
