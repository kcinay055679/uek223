package ch.bbt.uek223.ticketshop;

import jakarta.validation.*;

import java.util.Set;

public class Validator<T> {
    jakarta.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public void validate(T model) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        processViolations(violations);
    }

    private void processViolations(Set<ConstraintViolation<T>> violations) {
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
