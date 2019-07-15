package com.homoloa;

import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.SyntaxError;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Parser parser() {
        return new Parser() {
            public ParsedLine parse(String var1, int var2,
                                    Parser.ParseContext var3) throws SyntaxError {
                return null;
            }
        };
    }
}
