package org.jolly.multiplication.challenge.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jolly
 */
@Configuration
public class JsonConfig {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5JakartaModule();
    }
}
