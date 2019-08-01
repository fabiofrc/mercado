/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedidoitem")
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class PedidoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private double valorUnitario;

    @NotNull
    @Column(nullable = false, length = 3)
    private Integer quantidade = 1;

    @Column(name = "dataregistro")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataRegistro;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
   
    @Transient
    public double getValorTotal() {
        return this.getValorUnitario() * this.getQuantidade();
    }

    @Transient
    public boolean isProdutoAssociado() {
        return this.getProduto() != null && this.getProduto().getId() != null;
    }

    @Transient
    public boolean isEstoqueSuficiente() {
        return this.getProduto().getId() == null
                || this.getQuantidade() > 0;
    }

    @Transient
    public boolean isEstoqueInsuficiente() {
        return !this.isEstoqueSuficiente();
    }

}
