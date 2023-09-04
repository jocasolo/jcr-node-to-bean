package es.jocasolo.nodetobean.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertProperty {
	public String name() default "";
}
