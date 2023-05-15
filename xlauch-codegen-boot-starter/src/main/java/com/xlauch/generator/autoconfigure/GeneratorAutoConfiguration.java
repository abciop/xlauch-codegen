package com.xlauch.generator.autoconfigure;

import lombok.AllArgsConstructor;
import com.xlauch.generator.config.template.GeneratorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot starter AutoConfiguration
 *
 * @author 阿沐 babamu@126.com
 */
@Configuration
@AllArgsConstructor
@ComponentScan(basePackages = {"com.xlauch.generator"})
public class GeneratorAutoConfiguration {

    @Bean
    GeneratorConfig generatorConfig() {
        return new GeneratorConfig("");
    }

}
