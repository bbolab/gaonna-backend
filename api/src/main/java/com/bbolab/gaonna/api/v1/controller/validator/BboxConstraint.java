package com.bbolab.gaonna.api.v1.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = BboxConstraintValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BboxConstraint {
    String message() default "check bbox coordinates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
