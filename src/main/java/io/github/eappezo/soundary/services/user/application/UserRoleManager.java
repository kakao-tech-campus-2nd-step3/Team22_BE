package io.github.eappezo.soundary.services.user.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;

import java.util.List;

public interface UserRoleManager {

    void appendRole(Identifier userId, UserRole role);

    void removeRole(Identifier userId, UserRole role);

    void setRoles(Identifier userId, List<UserRole> roles);

    List<UserRole> getRolesOf(Identifier userId);

}
