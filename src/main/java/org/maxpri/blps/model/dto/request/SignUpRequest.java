package org.maxpri.blps.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Size(min = 5, max = 50, message = "Пароль должен содержать от 5 до 50 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Size(min = 5, max = 50, message = "Никнейм должен содержать от 5 до 50 символов")
    @NotBlank(message = "Никнейм не может быть пустым")
    private String nickname;
}
