package com.gdados.projeto.service;

import com.gdados.projeto.facade.PedidoFacade;
import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.PedidoItem;
import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class EstoqueService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PedidoFacade pedidoFacade;

    @Transactional
    public void baixarItensEstoque(Pedido pedido) throws NegocioException {
        pedido = this.pedidoFacade.porId(pedido.getId());
        for (PedidoItem item : pedido.getPedidoItems()) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
    }

    public void retornarItensEstoque(Pedido pedido) {
        pedido = this.pedidoFacade.porId(pedido.getId());
        for (PedidoItem item : pedido.getPedidoItems()) {
            item.getProduto().adicionarEstoque(item.getQuantidade());
        }
    }

}
