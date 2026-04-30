package com.pedinun.pedinunfood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 260)
    private String email;

    @Column(name = "senha", nullable = false, length = 260)
    private String senha;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column( name = "tipo_usuario", nullable = false)
    private TipoUsuario tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @PrePersist
    @PreUpdate
    private void validarTipo() {
        if (this.tipo != TipoUsuario.LOGISTA && this.tipo != TipoUsuario.ADMIN) {
            this.company = null;
        }
    }

   @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    private Boolean ativo = true;


    @CreationTimestamp
    private LocalDateTime dataCadastro;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setUsuario(this);
    }
}
