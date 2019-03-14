package com.gdados.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoafisica")
@PrimaryKeyJoinColumn(name = "pessoafisica_id")
public class PessoaFisica extends  Pessoa implements Serializable {

    private String cpf;
  

    @Column(unique = true, length = 14)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
  
}
