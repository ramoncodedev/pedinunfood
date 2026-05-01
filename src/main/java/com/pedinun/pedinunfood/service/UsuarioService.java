package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.entity.usuario.Endereco;
import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import com.pedinun.pedinunfood.entity.usuario.Usuario;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.repository.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


     // criar um novo usuário, verificando se o email já existe e se os dados estão corretos
    public Usuario saveUsuario(Usuario usuario) {

        verificarEmail(usuario.getEmail());

        if (usuario.getTipo() != TipoUsuario.CLIENTE) {
            if (usuario.getCompany() == null) {
                throw new ConflictionException("A empresa é obrigatória para o tipo: " + usuario.getTipo());
            }
        }

        if (usuario.getEnderecos() != null) {
            for (Endereco endereco : usuario.getEnderecos()) {
                endereco.setUsuario(usuario);
            }
        }

        return usuarioRepository.save(usuario);
    }

    public void verificarEmail(String email) {
        if (existByEmail(email)) {
            throw new ConflictionException("O email já está em uso.");
        }
    }

    public Boolean existByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new IllegalStateException("Nenhum usuário encontrado.");
        }

        return usuarios;
    }

    public Usuario findByTelefone(String telefone){
        Usuario usuario = usuarioRepository.findByTelefone(telefone);

        if (usuario == null) {
            throw new IllegalStateException("Nenhum usuário encontrado com o telefone: " + telefone);
        }

        return usuario;

    }

    @Transactional
    public void deleteByEmail(String email) {
        if (!usuarioRepository.existsByEmail(email)) {
            throw new IllegalStateException("Nenhum usuário encontrado com o email: " + email);
        }
        usuarioRepository.deleteByEmail(email);
    }





}