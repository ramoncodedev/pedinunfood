package com.pedinun.pedinunfood.entity.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pedinun.pedinunfood.entity.company.Company;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TenantId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @TenantId
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 260)
    private String email;

    @Column(name = "senha", nullable = false, length = 260)
    private String senha;

    @Column(name = "telefone", nullable = false, unique = true, length = 20)
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
   @JsonManagedReference
    private List<Endereco> enderecos = new ArrayList<>();

    private Boolean ativo = true;


    @CreationTimestamp
    private LocalDateTime dataCadastro;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    public void adicionarEndereco(Endereco endereco) {
        endereco.setUsuario(this);
        this.enderecos.add(endereco);
    }
}
