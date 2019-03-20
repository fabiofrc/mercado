package com.gdados.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoafisica")
@PrimaryKeyJoinColumn(name = "pessoafisica_id")
public class PessoaFisica extends Pessoa implements Serializable {

    @Column(unique = true, length = 14)
    private String cpf;

    private String sexo;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

}
