/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import com.gdados.projeto.service.NegocioException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "codigoBarra")
    private String codigoBarra;
    
    @Column(name = "unidade")
    private String unidade;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "subtitulo")
    private String subTitulo;

    @Column(name = "status")
    private boolean status;

    @Column(name = "destaque")
    private boolean destaque;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @Column(name = "dataregistro")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataRegistro;

    @Column(name = "dataatualizacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataatuAlizacao;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private double preco;
    
    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "precototal", nullable = false, precision = 10, scale = 2)
    private double precoTotal;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategoria subCategoria;

    @OneToMany(mappedBy = "produto")
    private List<Comentario> comentarios;

    @ManyToOne
    private PessoaJuridica pessoaJuridica;

    @OneToMany(mappedBy = "produto")
    private List<Arquivo> arquivos;

    @ManyToOne
    private Promocao promocao;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
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

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
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
    
}
