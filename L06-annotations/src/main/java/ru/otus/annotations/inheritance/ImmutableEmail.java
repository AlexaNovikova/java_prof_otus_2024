package ru.otus.annotations.inheritance;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@Email
@Immutable
@Inherited
public @interface ImmutableEmail {}
