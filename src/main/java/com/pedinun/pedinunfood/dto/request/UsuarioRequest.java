package com.pedinun.pedinunfood.dto.request;

import com.pedinun.pedinunfood.entity.company.Company;
import com.pedinun.pedinunfood.entity.usuario.Endereco;
import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;

import java.util.List;

public record UsuarioRequest(
        String nome,
        @Email(message = "O email deve ser válido")
        String email,
        String senha,
        String telefone,
        String cpf,
        TipoUsuario tipo,
        Company company,
        List<Endereco> enderecos
) {
}
