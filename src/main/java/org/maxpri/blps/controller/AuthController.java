package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.maxpri.blps.model.dto.request.SignInRequest;
import org.maxpri.blps.model.dto.request.SignUpRequest;
import org.maxpri.blps.model.dto.response.JwtResponse;
import org.maxpri.blps.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<JwtResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
}
