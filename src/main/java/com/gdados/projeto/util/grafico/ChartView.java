/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.util.grafico;

import java.io.Serializable;
import javax.inject.Named;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author fabio
 */
@Named
public class ChartView implements Serializable {

    private CartesianChartModel cartesianChartModel;

    public void preRender() {
        this.cartesianChartModel = new CartesianChartModel();
        adicionarSerie("Todos os pedidos");
        adicionarSerie("Mesu pedidos");
    }

    private void adicionarSerie(String rotulos) {
        ChartSeries series = new ChartSeries(rotulos);
        series.set("1", Math.random() * 1000);
        series.set("2", Math.random() * 1000);
        series.set("3", Math.random() * 1000);
        series.set("4", Math.random() * 1000);
        series.set("5", Math.random() * 1000);

        this.cartesianChartModel.addSeries(series);
    }

    public CartesianChartModel getCartesianChartModel() {
        return cartesianChartModel;
    }

}
