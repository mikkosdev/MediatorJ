package org.example.annotations;

/**
 * This annotation can be used to mark an aspect as the one that must come before all others.
 * Nothing must precede the marked aspect. The use case here could be for example a check that the user is logged in, or that a security token
 */
public @interface MustRunFirst {
}
