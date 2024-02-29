package frc.lib.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Untested marks code that has not been tested on a robot. If a class is marked as untested, its methods do not need to
 * also be marked as untested.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Untested {

}