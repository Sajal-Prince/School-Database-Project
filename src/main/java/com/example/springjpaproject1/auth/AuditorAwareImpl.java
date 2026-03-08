package com.example.springjpaproject1.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //get security context
        //get authentication
        //get principle
        //get Username
        return Optional.of("Sajal Shrivastava");
    }
}
