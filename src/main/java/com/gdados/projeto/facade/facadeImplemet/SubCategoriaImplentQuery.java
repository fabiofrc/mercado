/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade.facadeImplemet;

import com.gdados.projeto.model.SubCategoria;
import java.util.List;

/**
 *
 * @author fabio
 */
public interface SubCategoriaImplentQuery {

    public List<SubCategoria> listaSubCategoriaByCategoria(Long id);

    public List<SubCategoria> listarSubCategoriaById(Long id);

    public SubCategoria buscarSubCategoriaByCategoriaById(Long id);

    public SubCategoria buscarSubCategoriaById(Long id);

    public long contarSubCategoria();

    public long contarSubCategoriaById(Long id);
}
