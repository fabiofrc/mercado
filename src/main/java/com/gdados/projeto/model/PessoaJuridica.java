package com.gdados.projeto.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoajuridica")
@PrimaryKeyJoinColumn(name = "pessoajuridica_id")
public class PessoaJuridica extends Pessoa implements Serializable {

    private String razaoSocial;
    private String cnpj;

  
    @Column(unique = true, length = 14)
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
}
