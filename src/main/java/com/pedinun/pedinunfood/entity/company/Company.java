package com.pedinun.pedinunfood.entity.company;


import com.pedinun.pedinunfood.entity.usuario.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 100)
    private String razaoSocial;

    private String nomeFantasia;

    @Column(nullable = false, length = 700)
    private String descricao;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(name = "slug", nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, unique = true, length = 260)
    private String email;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> endereco;

    private String imageUrl;

    private String bannerUrl;

    private Boolean aberto = false;

    private Boolean aprovado = false;

    private Boolean ativo = true;

    private BigDecimal taxaEntregaPadrao = BigDecimal.ZERO;
    private Integer tempoEntregaEstimado;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
}
