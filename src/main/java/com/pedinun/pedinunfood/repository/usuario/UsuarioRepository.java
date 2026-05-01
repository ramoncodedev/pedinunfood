package com.pedinun.pedinunfood.repository.usuario;

import com.pedinun.pedinunfood.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

     Usuario findByEmail(String email);

     boolean existsByEmail(String email);

     Usuario findByTelefone(String telefone);

     void deleteByEmail(String email);
}
