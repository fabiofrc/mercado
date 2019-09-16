/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fatura")
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_fatura")
    private String numeroFatura;

    @Column(name = "data_registro")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataRegistro;

    @Column(name = "data_emissao")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataEmissao;

    @Column(name = "data_pagamento")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataPagamento;

    @Column(name = "data_pedido_cancelamento")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataPedidoCancelamento;

    @Column(name = "data_cancelamento")
//    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataCancelamento;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private LocalDate valorPago;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "status", nullable = false)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFatura() {
        return numeroFatura;
    }

    public void setNumeroFatura(String numeroFatura) {
        this.numeroFatura = numeroFatura;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataPedidoCancelamento() {
        return dataPedidoCancelamento;
    }

    public void setDataPedidoCancelamento(LocalDate dataPedidoCancelamento) {
        this.dataPedidoCancelamento = dataPedidoCancelamento;
    }

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDate dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public LocalDate getValorPago() {
        return valorPago;
    }

    public void setValorPago(LocalDate valorPago) {
        this.valorPago = valorPago;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        Fatura other = (Fatura) obj;
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
