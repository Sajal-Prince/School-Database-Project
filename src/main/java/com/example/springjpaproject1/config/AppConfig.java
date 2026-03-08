package com.example.springjpaproject1.config;

import com.example.springjpaproject1.auth.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getCurrentAuditor")
public class AppConfig {
    @Bean
    public AuditorAware<String> getCurrentAuditor(){
        return new AuditorAwareImpl();
    }
}
