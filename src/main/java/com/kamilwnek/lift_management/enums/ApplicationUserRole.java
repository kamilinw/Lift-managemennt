package com.kamilwnek.lift_management.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import static  com.kamilwnek.lift_management.enums.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE)),
    EMPLOYEE(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return simpleGrantedAuthorities;
    }
}
