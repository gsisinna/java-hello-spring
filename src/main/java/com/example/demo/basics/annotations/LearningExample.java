package com.example.demo.basics.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// Custom annotation used to tag classes with the concepts they are meant to teach.
public @interface LearningExample {

	String title();

	String[] concepts();
}
