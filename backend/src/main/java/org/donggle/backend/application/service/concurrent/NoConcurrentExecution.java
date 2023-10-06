package org.donggle.backend.application.service.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoConcurrentExecution {

    Class<? extends RuntimeException> value() default ConcurrentAccessException.class;
}
