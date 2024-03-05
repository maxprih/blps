package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
@Getter
public class UsernameNotFoundException extends RuntimeException {
    private final String username;

    public UsernameNotFoundException(String username) {
        this.username = username;
    }
}
