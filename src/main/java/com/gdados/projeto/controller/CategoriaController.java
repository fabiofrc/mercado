/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.CategoriaFacade;
import com.gdados.projeto.model.Categoria;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class CategoriaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Categoria categoria;
    @Inject
    private CategoriaFacade categoriaFacade;
    private List<Categoria> categorias;

    public CategoriaController() {
        if (categoria == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            Date dataRegsitro = new Date();
            if (categoria.getId() == null) {
                categoria.setDataRegistro(dataRegsitro);
                categoriaFacade.save(categoria);
                limpaCampo();
                return "lista?faces-redirect=true";
            } else {
                categoria.setDataRegistro(dataRegsitro);
                categoriaFacade.update(categoria);
                limpaCampo();
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String view(Long id) {
        try {
            categoria = categoriaFacade.getAllByCodigo(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            categoria = categoriaFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Categoria categoria) {
        try {
            categoriaFacade.delete(categoria);
            getCategorias();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        byte[] content = event.getFile().getContents();
        System.out.println(content.length);
        categoria.setArquivo(content);
    }

    public StreamedContent getImage1() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            context.getExternalContext().setResponseStatus(200);
            String studentId = context.getExternalContext().getRequestParameterMap().get("id");
            categoria = categoriaFacade.getAllByCodigo(Long.valueOf(studentId));
            return new DefaultStreamedContent(new ByteArrayInputStream(categoria.getArquivo()));
        }
    }

    public String lista() {
        return "/paginas/adm/categoria/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/categoria/cadastro?faces-redirect=true";
    }

    private void limpaCampo() {
        categoria = new Categoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaFacade getCategoriaFacade() {
        return categoriaFacade;
    }

    public void setCategoriaFacade(CategoriaFacade categoriaFacade) {
        this.categoriaFacade = categoriaFacade;
    }

    public List<Categoria> getCategorias() {
        categorias = categoriaFacade.getAll();
        return categorias;
    }

    public int getContador() {
        return categoriaFacade.count();
    }
}
