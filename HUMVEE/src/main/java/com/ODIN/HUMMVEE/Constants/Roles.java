package com.ODIN.HUMMVEE.Constants;

import java.util.Set;

public enum Roles {

    ADMIN(Set.of(Permissions.GADGET_READ,Permissions.GADGET_WRITE,Permissions.GADGET_DELETE)),
    USER(Set.of(Permissions.GADGET_READ));

    private final Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions>getPermissions(){
        return this.permissions;
    }

}
