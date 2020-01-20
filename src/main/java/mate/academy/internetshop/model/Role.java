package mate.academy.internetshop.model;

import mate.academy.internetshop.lib.IdGenerator;

public class Role {
    private final Long id;
    private RoleName roleName;

    public Role() {
        this.id = IdGenerator.getRoleId();
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
