package com.yoru.qingxintutor.annotation.auth;

public final class Roles {
    public static final String STUDENT = "STUDENT";
    public static final String TEACHER = "TEACHER";
    public static final String IS_STUDENT = "hasRole('" + STUDENT + "')";
    public static final String IS_TEACHER = "hasRole('" + TEACHER + "')";

    private Roles() {
    }
}
