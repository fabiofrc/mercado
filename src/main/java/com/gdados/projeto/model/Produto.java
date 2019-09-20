/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import com.gdados.projeto.service.NegocioException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author frc
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "unidade")
    private String unidade;

    @Column(name = "nome")
    private String nome;

    @Column(name = "status")
    private boolean status;

    @Column(name = "destaque")
    private boolean destaque;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @Column(name = "data_atualizacao")
    private LocalDate dataatuAlizacao;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "preco_venda", nullable = false, precision = 10, scale = 2)
    private double precoVenda;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategoria subCategoria;

    @OneToMany(mappedBy = "produto")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "produto")
    private List<Arquivo> arquivos;

    @ManyToOne
    @JoinColumn(name = "pessoajuridica_id")
    private Loja pessoaJuridica;

    @ManyToMany(mappedBy = "produtos", targetEntity = Promocao.class)
    private List<Promocao> promocaos;

//    @ManyToMany//(mappedBy = "produtos")
//    private List<Fornecedor> fornecedors;

    @OneToOne
    private Estoque estoque = new Estoque();

    public void baixarEstoque(Integer quantidade) throws NegocioException {
        int novaQuantidade = this.getQuantidade() - quantidade;
        if (novaQuantidade < 0) {
            throw new NegocioException("Não há disponibilidade no estoque de " + quantidade + " itens do produto " + this.getId() + ".");
        }
        this.setQuantidade(novaQuantidade);
    }

    public void adicionarEstoque(Integer quantidade) {
        this.setQuantidade(getQuantidade() + quantidade);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataatuAlizacao() {
        return dataatuAlizacao;
    }

    public void setDataatuAlizacao(LocalDate dataatuAlizacao) {
        this.dataatuAlizacao = dataatuAlizacao;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Loja getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(Loja pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public List<Promocao> getPromocaos() {
        return promocaos;
    }

    public void setPromocaos(List<Promocao> promocaos) {
        this.promocaos = promocaos;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

//    public List<Fornecedor> getFornecedors() {
//        return fornecedors;
//    }
//
//    public void setFornecedors(List<Fornecedor> fornecedors) {
//        this.fornecedors = fornecedors;
//    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Produto other = (Produto) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
