package com.gdados.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente"))
public class Cliente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cpf", unique = true, length = 14)
    private String cpf;

    @Column(name = "sexo")
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
