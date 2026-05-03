package com.pedinun.pedinunfood.mapper;

import com.pedinun.pedinunfood.dto.request.UsuarioRequest;
import com.pedinun.pedinunfood.dto.response.UsuarioResponse;
import com.pedinun.pedinunfood.entity.usuario.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .nome(usuarioRequest.nome())
                .email(usuarioRequest.email())
                .senha(usuarioRequest.senha())
                .telefone(usuarioRequest.telefone())
                .cpf(usuarioRequest.cpf())
                .tipo(usuarioRequest.tipo())
                .company(usuarioRequest.company())
                .enderecos(usuarioRequest.enderecos())
                .build();
    }

    public UsuarioResponse toResponse(Usuario usuario){
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .cpf(usuario.getCpf())
                .tipo(usuario.getTipo())
                .companyId(usuario.getCompany() != null ? usuario.getCompany().getId() : null)
                .enderecos(usuario.getEnderecos())
                .ativo(usuario.getCompany() != null ? usuario.getCompany().getAtivo() : null)
                .dataCadastro(usuario.getDataCadastro())
                .dataAtualizacao(usuario.getDataAtualizacao())
                .build();
    }
}
