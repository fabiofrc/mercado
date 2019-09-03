/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import javax.inject.Named;

/**
 *
 * @author fabio
 */
@Named
public class DashboardController {

    private static final long serialVersionUID = 1L;

    public DashboardController() {
    }

    public String dashbaord() {
        return "/paginas/adm/dashboard/dashboard?faces-redirect=true";
    }
}
