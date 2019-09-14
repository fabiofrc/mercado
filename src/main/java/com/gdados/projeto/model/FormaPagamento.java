package com.gdados.projeto.model;

public enum FormaPagamento {

    PAYPAL("Paypal"),
    PAGUE_SEGURO("Pague Seguro"),
    CARTAO_CREDITO("Cartão de crédito"),
    BOLETO_BANCARIO("Boleto bancário"),
    TRANSFERENCIA_BANCARIA("Transferência bancária");

    private String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
