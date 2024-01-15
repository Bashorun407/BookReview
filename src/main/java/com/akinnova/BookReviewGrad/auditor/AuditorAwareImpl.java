package com.akinnova.BookReviewGrad.auditor;

import org.springframework.data.domain.AuditorAware;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(Arrays.asList("bash007", "ade212", "olash77")
                .get(new Random().nextInt()));
    }
}
