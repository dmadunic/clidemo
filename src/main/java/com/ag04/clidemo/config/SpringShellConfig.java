package com.ag04.clidemo.config;

import com.ag04.clidemo.shell.ShellHelper;
import org.jline.reader.LineReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SpringShellConfig {

    @Bean
    public ShellHelper shellHelper(@Lazy LineReader lineReader) {
            return new ShellHelper(lineReader);
    }

}
