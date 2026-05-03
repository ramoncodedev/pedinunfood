package com.pedinun.pedinunfood.dto.request;

import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha,
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        @NotNull(message = "O tipo de usuário é obrigatório")
        TipoUsuario tipo
) {
}