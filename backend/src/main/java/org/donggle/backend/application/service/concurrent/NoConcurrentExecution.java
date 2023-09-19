package org.donggle.backend.application.service.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface NoConcurrentExecution {

    Class<? extends RuntimeException> value() default ConcurrentAccessException.class;
}
