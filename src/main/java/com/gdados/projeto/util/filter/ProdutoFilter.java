/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.util.filter;

/**
 *
 * @author PMBV-163979
 */
public class ProdutoFilter {

    private String titulo;
    private String categoria;
    private double precoMinimo = 0;
    private double precoMaximo = 1000;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

}
