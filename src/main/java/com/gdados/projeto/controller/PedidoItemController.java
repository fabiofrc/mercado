/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.PedidoItemFacade;
import com.gdados.projeto.model.PedidoItem;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PedidoItemController implements Serializable {

    private PedidoItem pedidoItem;
    @Inject
    private PedidoItemFacade pedidoItemFacade;
    private List<PedidoItem> pedidoItems;
    private List<PedidoItem> meusPedidoItems;

    public PedidoItemController() {
        if (pedidoItem == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            if (pedidoItem.getId() == null) {
                pedidoItemFacade.save(pedidoItem);
                limpaCampo();
                return "lista?faces-redirect=true";
            } else {
                pedidoItemFacade.update(pedidoItem);
                limpaCampo();
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("com.gdados.projeto.controller.CategoriaController.salvar()");
        }
        return null;
    }

    public String view(Long id) {
        try {
            pedidoItem = pedidoItemFacade.getAllByCodigo(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public String editar(Long id) {
        try {
            pedidoItem = pedidoItemFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(PedidoItem pedidoItem) {
        try {
            pedidoItemFacade.delete(pedidoItem);
            getPedidoItems();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
    }

    public void meusItensPedidos(Long id) {
        try {
            meusPedidoItems = pedidoItemFacade.listaPedidoItemByPedido(id);
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
    }

    public String lista() {
        return "/paginas/adm/pedido_item/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/pedido_item/cadastro?faces-redirect=true";
    }

    private void limpaCampo() {
        pedidoItem = new PedidoItem();
    }

    public PedidoItem getPedidoItem() {
        return pedidoItem;
    }

    public void setPedidoItem(PedidoItem pedidoItem) {
        this.pedidoItem = pedidoItem;
    }

    public PedidoItemFacade getPedidoItemFacade() {
        return pedidoItemFacade;
    }

    public void setPedidoItemFacade(PedidoItemFacade pedidoItemFacade) {
        this.pedidoItemFacade = pedidoItemFacade;
    }

    public List<PedidoItem> getPedidoItems() {
        pedidoItems = pedidoItemFacade.getAll();
        return pedidoItems;
    }

    public List<PedidoItem> getMeusPedidoItems() {
        return meusPedidoItems;
    }

}
