package com.gdados.projeto.controller;

import com.gdados.projeto.facade.PedidoFacade;
import com.gdados.projeto.facade.PessoaFisicaFacade;
import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.PedidoItem;
import com.gdados.projeto.model.PessoaFisica;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.security.CurrentUser;
import com.gdados.projeto.security.UsuarioSistema;
import com.gdados.projeto.util.msg.Msg;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.primefaces.event.SelectEvent;

@Named
@SessionScoped
public class PedidoController implements Serializable {

    private static final long serialVersionUID = 1L;

    private UsuarioSistema usuario;

    private Pedido carrinho;

    @Inject
    private PessoaFisica pessoaFisica;

    @Inject
    private PessoaFisicaFacade pessoaFisicaFacade;

    @Inject
    private PedidoFacade pedidoFacade;

    private PedidoItem item;

    public PedidoController() {
        limpar();
    }

    public void inicializar() {
        if (this.carrinho == null) {
            limpar();
        }

        this.recalcularCarrinho();
    }

    private void limpar() {
        item = new PedidoItem();
        carrinho = new Pedido();
    }

    public PedidoItem getItem() {
        return item;
    }

    public void setItem(PedidoItem item) {
        this.item = item;
    }

//    public List<PessoaFisica> completarCliente(String nome) {
//        return this.pessoaFisica.porNome(nome);
//    }
    public Pedido getCarrinho() {
        return carrinho;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    @NotBlank
    public String getNomeCliente() {
        return carrinho.getPessoaFisica() == null ? null : carrinho.getPessoaFisica().getNome();
    }

    public void setNomeCliente(String nome) {
    }

    public void adicionarItem(Produto produto) {
        if (existeItemComProduto(produto)) {
            Msg.addMsgWarn("O produto já está no carrinho!");
        } else {
            item = new PedidoItem();
            item.setProduto(produto);
            item.setQuantidade(1);
            item.setValorUnitario(produto.getPreco());
            item.setPedido(carrinho);
            carrinho.getPedidoItems().add(item);
            Msg.addMsgInfo("Produto adicionado!");
        }

        recalcularCarrinho();
    }

    public String adicionar(Produto produto) {
        try {
            if (existeItemComProduto(produto)) {
                Msg.addMsgWarn("O produto já está no carrinho!");
            } else {
                item = new PedidoItem();
                item.setProduto(produto);
                item.setQuantidade(1);
                item.setValorUnitario(produto.getPreco());
                item.setPedido(carrinho);
                carrinho.getPedidoItems().add(item);
                Msg.addMsgInfo("Produto adicionado!");
            }
            recalcularCarrinho();
            return "/paginas/plb/pedido/lista?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;

    }

    public String removerItem(PedidoItem index) {
        carrinho.getPedidoItems().remove(index);
        recalcularCarrinho();
        return null;
    }

    public void produtoSelecionado(SelectEvent event) {
        adicionarItem((Produto) event.getObject());

    }

    public void clienteSelecionado(SelectEvent event) {
        this.carrinho.setPessoaFisica((PessoaFisica) event.getObject());
    }

    public void salvar() {
        try {
            if (carrinho.getPessoaFisica() == null) {
                carrinho.setPessoaFisica((PessoaFisica) usuario.getUsuario().getPessoa());
            }
            carrinho = this.pedidoFacade.save(carrinho);
            Msg.addInfoMessage("Carrinho atualizado com sucesso!");
        } catch (Exception ne) {
            Msg.addErrorMessage(ne.getMessage());
        } finally {

        }
    }

    public String finalizar() {
        if (usuario != null && !carrinho.isVazio()) {
            salvar();
            return "Pagamento";
        } else if (usuario == null && !carrinho.isVazio()) {
            Msg.addInfoMessage("Faça login para continuar.");
            return "";
        } else {
            Msg.addInfoMessage("O carrinho deve possuir pelo menos 1 item.");
            return "";
        }
    }

    public void recalcularCarrinho() {
        if (this.carrinho != null) {
            this.carrinho.recalcularValorTotal();
        }
    }

    private boolean existeItemComProduto(Produto produto) {
        boolean existeItem = false;

        for (PedidoItem items : this.getCarrinho().getPedidoItems()) {
            if (produto.equals(items.getProduto())) {
                existeItem = true;
                break;
            }
        }

        return existeItem;
    }

    public void atualizarQuantidade(PedidoItem item, int linha) {
        if (item.getQuantidade() < 1) {
            if (linha == 0) {
                item.setQuantidade(1);
            } else {
                this.getCarrinho().getPedidoItems().remove(linha);
            }
        }

        this.carrinho.recalcularValorTotal();
    }

    public boolean isEditando() {
        return this.carrinho.getId() != null;
    }

    public void sucesso() {
        limpar();
    }

    private void enviarBoleto(byte[] bPDF) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Boleto_" + carrinho.getId() + "_CCTI.pdf");

        try {
            response.setContentLength(bPDF.length);
            ServletOutputStream output = response.getOutputStream();
            output.write(bPDF, 0, bPDF.length);
            response.flushBuffer();
            System.out.println("Numero do pedido: " + carrinho.getId() + "\nBoleto emitido com sucesso! ");
            FacesContext.getCurrentInstance().addMessage("messages",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Boleto emitido com sucesso", null));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar boleto", e);
        }

        FacesContext.getCurrentInstance().responseComplete();

    }

    public Date getDataHoje() {
        Calendar cal = Calendar.getInstance();
        Date dataHoje = cal.getTime();
        return dataHoje;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public PessoaFisicaFacade getPessoaFisicaFacade() {
        return pessoaFisicaFacade;
    }

    public void setPessoaFisicaFacade(PessoaFisicaFacade pessoaFisicaFacade) {
        this.pessoaFisicaFacade = pessoaFisicaFacade;
    }

    public PedidoFacade getPedidoFacade() {
        return pedidoFacade;
    }

    public void setPedidoFacade(PedidoFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

}
