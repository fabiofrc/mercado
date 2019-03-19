package com.gdados.projeto.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoajuridica")
@PrimaryKeyJoinColumn(name = "pessoajuridica_id")
@SuppressWarnings("ConsistentAccessType")
public class PessoaJuridica extends Pessoa implements Serializable {

    private String razaoSocial;

    @Column(unique = true, length = 20)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "contabancaria_id")
    private ContaBancaria contaBancaria;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

}
