/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade.facadeImplemet;

import com.gdados.projeto.model.Produto;
import java.util.List;

/**
 *
 * @author fabio
 */
public interface ProdutoImplentQuery {

    public List<Produto> listarProdutoById(Long id);

    public List<Produto> listarProdutoBySubCategoriaById(Long id);

    public Produto buscarProdutoById(Long id);

    public Produto buscarProdutoBySubCategoriaById(Long id);

    public long contarProduto();

    public long contarProdutoById(Long id);
}
