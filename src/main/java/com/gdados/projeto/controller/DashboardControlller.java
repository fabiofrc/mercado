/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import javax.faces.annotation.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author PMBV-164029
 */
@Named
public class DashboardControlller {

    @ManagedProperty("#{participanteController}")
    private PessoaFisicaController participanteController;
    @ManagedProperty("#{usuarioController}")
    private UsuarioController usuarioController;
    @ManagedProperty("#{noticiaController}")
    private ProdutoController noticiaController;

    public DashboardControlller() {
        participanteController = new PessoaFisicaController();
        usuarioController = new UsuarioController();
        noticiaController = new ProdutoController();
    }

    public String dashboard() {
        return "/paginas/adm/dashboard/dashboard?faces-redirect=true";
    }

    public PessoaFisicaController getParticipanteController() {
        return participanteController;
    }

    public void setParticipanteController(PessoaFisicaController participanteController) {
        this.participanteController = participanteController;
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public ProdutoController getNoticiaController() {
        return noticiaController;
    }

    public void setNoticiaController(ProdutoController noticiaController) {
        this.noticiaController = noticiaController;
    }

}
