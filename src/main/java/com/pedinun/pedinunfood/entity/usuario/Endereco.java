package com.pedinun.pedinunfood.entity.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pedinun.pedinunfood.entity.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 100, nullable = false)
    private String complemento;


    @Column( nullable = false, length = 150)
    private String pontoReferencia;

    @Column(nullable = false, length = 60)
    private String bairro;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 60)
    private String cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @Column(name = "endereco_principal", nullable = false)
    private Boolean Enderecoprincipal = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCadastro;



}
