package org.maxpri.blps.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.maxpri.blps.model.dto.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author max_pri
 */
@Service
public class AuthService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    @Autowired
    public AuthService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void addUser(SignUpRequest dto) {
        String username = dto.getUsername();
        CredentialRepresentation credential = createPasswordCredentials(dto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        UsersResource usersResource = getUsersResource();
        System.out.println(usersResource.count());
        System.out.println(usersResource.list());
        usersResource.create(user);
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
