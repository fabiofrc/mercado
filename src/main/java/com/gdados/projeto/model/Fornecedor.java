package com.gdados.projeto.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "fornecedor")
@PrimaryKeyJoinColumn(name = "fornecedor_id", foreignKey = @ForeignKey(name = "fk_fornecedor"))
public class Fornecedor extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "cnpj", unique = true, length = 20)
    private String cnpj;

//    @ManyToMany
//    @JoinTable(name = "fornecedor_produto", joinColumns = {
//        @JoinColumn(name = "fornecedor_id")}, inverseJoinColumns = {
//        @JoinColumn(name = "produto_id")})
//    private List<Produto> produtos;

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

//    public List<Produto> getProdutos() {
//        return produtos;
//    }
//
//    public void setProdutos(List<Produto> produtos) {
//        this.produtos = produtos;
//    }

}
