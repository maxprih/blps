package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.maxpri.blps.model.dto.request.SignUpRequest;
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

    @PostMapping("/sign-up")
    @Operation(summary = "Регистрация")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.addUser(signUpRequest);
        return ResponseEntity.ok().build();
    }
}
