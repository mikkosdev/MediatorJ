package org.example.annotations;

/**
 * Marks a class as an aspect and gives it an index
 */
public @interface AspectClass {
    int index() default 0;
}
