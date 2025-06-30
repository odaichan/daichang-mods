package net.daichang.dcmods.utils;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface  ModDisblerMixin {
    String value() default "dc_m";
}
