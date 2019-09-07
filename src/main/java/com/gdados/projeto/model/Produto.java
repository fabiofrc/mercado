/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import com.gdados.projeto.service.NegocioException;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
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
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataRegistro;

    @Column(name = "data_atualizacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataatuAlizacao;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private double preco;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "preco_total", nullable = false, precision = 10, scale = 2)
    private double precoTotal;

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
    private PessoaJuridica pessoaJuridica;

    @ManyToMany(mappedBy = "produtos")
    private List<Promocao> promocaos;

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

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Date getDataatuAlizacao() {
        return dataatuAlizacao;
    }

    public void setDataatuAlizacao(Date dataatuAlizacao) {
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
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

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
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

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public List<Promocao> getPromocaos() {
        return promocaos;
    }

    public void setPromocaos(List<Promocao> promocaos) {
        this.promocaos = promocaos;
    }

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
