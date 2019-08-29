package dev.rija.services;

import dev.rija.entities.AppRole;
import dev.rija.entities.AppUser;

public interface AccountService {
    public AppUser saveUser(AppUser user);

    public AppRole saveRole(AppRole role);

    public void addRoleToUser(String username, String roleName);

    public AppUser findUserByUsername(String username);

}
