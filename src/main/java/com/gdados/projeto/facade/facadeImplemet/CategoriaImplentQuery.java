/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade.facadeImplemet;

import com.gdados.projeto.model.Categoria;
import java.util.List;

/**
 *
 * @author fabio
 */
public interface CategoriaImplentQuery {

    public List<Categoria> listarSubCategoriaById(Long id);

    public Categoria buscarCategoriaById(Long id);

    public long contarCategoria();

    public long contarCategoriaById(Long id);
}
