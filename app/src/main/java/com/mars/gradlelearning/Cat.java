package com.mars.gradlelearning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JohnnySwordMan on 2021/3/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Cat {
}
