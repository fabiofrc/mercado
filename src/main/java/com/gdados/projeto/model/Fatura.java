/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataRegistro;

    @Column(name = "data_emissao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataEmissao;

    @Column(name = "data_pagamento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataPagamento;

    @Column(name = "data_pedido_cancelamento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataPedidoCancelamento;

    @Column(name = "data_cancelamento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate dataCancelamento;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private LocalDate valorPago;

    @NotNull(message = "Valor unitário é obrigatório")
    @Column(name = "status", nullable = false)
    private String status;

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
