package com.kamilwnek.lift_management.enums;

public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
