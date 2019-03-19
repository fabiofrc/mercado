package com.gdados.projeto.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.gdados.projeto.facade.PessoaFisicaFacade;
import com.gdados.projeto.model.PessoaFisica;

@Named
@ViewScoped
public class SelecaoClienteController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PessoaFisicaFacade clientes;

    private String nome;

    private List<PessoaFisica> clientesFiltrados;

    public void pesquisar() {
        clientesFiltrados = clientes.porNome(nome);
    }

    public void selecionar(PessoaFisica cliente) {
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", false);
        opcoes.put("draggable", false);
        opcoes.put("resizable", false);
        opcoes.put("showHeader", false);
        opcoes.put("responsive", true);
        opcoes.put("minimizable", true);
        opcoes.put("closeOnEscape", true);
        opcoes.put("responsive", true);
        opcoes.put("fitViewport", true);
        opcoes.put("showEffect", "fade");
        opcoes.put("hideEffect", "fade");
        opcoes.put("minHeight", 300);
        opcoes.put("height", 400);

        RequestContext.getCurrentInstance().openDialog("/dialogos/SelecaoCliente", opcoes, null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<PessoaFisica> getClientesFiltrados() {
        return clientesFiltrados;
    }

}
