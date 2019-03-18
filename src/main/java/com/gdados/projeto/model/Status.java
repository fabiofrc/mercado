package com.gdados.projeto.model;

public enum Status {

    PENDENTE("Pendente"),
    CANCELADO("Cancelado"),
    PAGO("Pago");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
