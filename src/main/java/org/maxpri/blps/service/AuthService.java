package org.maxpri.blps.service;

import org.maxpri.blps.model.dto.request.SignInRequest;
import org.maxpri.blps.model.dto.request.SignUpRequest;
import org.maxpri.blps.model.dto.response.JwtResponse;
import org.maxpri.blps.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author max_pri
 */
@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserService userService, JwtService jwtService, RoleService roleService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ));

        UserDetails user = userService.loadUserByUsername(signInRequest.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }

    public JwtResponse signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getNickname())
                .roles(Set.of(roleService.getUserRole()))
                .build();

        userService.create(user);

        String jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }
}
