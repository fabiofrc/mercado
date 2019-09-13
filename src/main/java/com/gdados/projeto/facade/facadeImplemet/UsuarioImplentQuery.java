/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade.facadeImplemet;

import com.gdados.projeto.model.Produto;
import com.gdados.projeto.model.Usuario;
import java.util.Optional;

/**
 *
 * @author fabio
 */
public interface UsuarioImplentQuery {

    public Optional<Usuario> listarUsuarioById(Long id);

    public Usuario buscarProdutoById(Long id);

    public Produto buscarProdutoBySubCategoriaById(Long id);

    public long contarUsuario();

    public long contarUsuarioById(Long id);
}
