/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.CategoriaFacade;
import com.gdados.projeto.facade.SubCategoriaFacade;
import com.gdados.projeto.model.Categoria;
import com.gdados.projeto.model.SubCategoria;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class SubCategoriaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private SubCategoria subCategoria;
    @Inject
    private SubCategoriaFacade subCategoriaFacade;

    @Inject
    private CategoriaFacade categoriaFacade;

    private List<SubCategoria> subCategorias;

    private List<SubCategoria> subCategoriaSelecionada;

    private List<Categoria> categorias;

    private Categoria categoria;

    public SubCategoriaController() {
        if (subCategoria == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            LocalDate dataRegistro = LocalDate.now();
            if (subCategoria.getId() == null) {
                subCategoria.setDataRegistro(dataRegistro);
                subCategoriaFacade.save(subCategoria);
                limpaCampo();
                return "lista?faces-redirect=true";
            } else {
                subCategoria.setDataRegistro(dataRegistro);
                subCategoriaFacade.update(subCategoria);
                limpaCampo();
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String view(Long id) {
        try {
            subCategoria = subCategoriaFacade.getById(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            subCategoria = subCategoriaFacade.getById(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(SubCategoria subCategoria) {
        try {
            subCategoriaFacade.delete(subCategoria);
            getSubCategorias();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void buscarSubCategoria() {
        try {
            System.out.println("Id: " + categoria.getId());
            subCategoriaSelecionada = subCategoriaFacade.listaSubCategoriaByCategoria(categoria.getId());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void buscarSubCategoriaById(Long id) {
        try {
            subCategoriaSelecionada = subCategoriaFacade.listaSubCategoriaByCategoria(id);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void limpaCampo() {
        subCategoria = new SubCategoria();
        subCategoria.setCategoria(new Categoria());
    }

    public String lista() {
        return "/paginas/adm/subcategoria/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/subcategoria/cadastro?faces-redirect=true";
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public SubCategoriaFacade getSubCategoriaFacade() {
        return subCategoriaFacade;
    }

    public void setSubCategoriaFacade(SubCategoriaFacade subCategoriaFacade) {
        this.subCategoriaFacade = subCategoriaFacade;
    }

    public List<SubCategoria> getSubCategorias() {
        subCategorias = subCategoriaFacade.getAll();
        return subCategorias;
    }

    public int getContador() {
        return subCategoriaFacade.count();
    }

    public List<SubCategoria> getSubCategoriaSelecionada() {
        return subCategoriaSelecionada;
    }

    public void setSubCategoriaSelecionada(List<SubCategoria> subCategoriaSelecionada) {
        this.subCategoriaSelecionada = subCategoriaSelecionada;
    }

    public List<Categoria> getCategorias() {
        categorias = categoriaFacade.getAll();
        return categorias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
