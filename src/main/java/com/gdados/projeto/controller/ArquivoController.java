/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.ArquivoFacade;
import com.gdados.projeto.model.Arquivo;
import com.gdados.projeto.util.msg.Msg;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Usuario
 */
@Named
@SessionScoped
public class ArquivoController implements Serializable {

    @Inject
    private ArquivoFacade arquivoFacade;
    private Arquivo arquivo;
    private List<Arquivo> arquivos;
    private String imagem;
    private List<Arquivo> arquivosByProdutos;

    private DefaultStreamedContent file;

    public ArquivoController() {
        if (arquivo == null) {
            arquivo = new Arquivo();
        }
        if (arquivoFacade == null) {
            arquivoFacade = new ArquivoFacade();
        }

        if (arquivos == null) {
            arquivos = new ArrayList<>();
        }
    }

    public String salvar() {
        try {
            if (arquivo.getId() == null) {
                arquivoFacade.save(arquivo);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso!");
                return "/paginas/adm/arquivo/lista?faces-redirect=true";
            } else {
                arquivoFacade.update(arquivo);
                Msg.addMsgInfo("Operação atualizada com sucesso.");
                return "/paginas/adm/arquivo/lista?faces-redirect=true";
            }
        } catch (Exception e) {
            Msg.addErrorMessage("Operação não realizada!");
        }
        return null;
    }

    public String view(Long id) {
        try {
            arquivo = arquivoFacade.getAllByCodigo(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public String viewTudo() {
        try {
            return "/paginas/adm/arquivo/arquivo?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public String editar(Long id) {
        try {
            arquivo = arquivoFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public void deletar(Arquivo arquivo) {
        try {
            arquivoFacade.delete(arquivo);
            getArquivos();
        } catch (Exception e) {
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        byte[] content = event.getFile().getContents();
        System.out.println(content.length);
        arquivo.setArquivo(content);
    }

    public StreamedContent getImage1() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String studentId = context.getExternalContext().getRequestParameterMap().get("id");
            arquivo = arquivoFacade.getAllByCodigo(Long.valueOf(studentId));
            return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getArquivo()));
        }
    }

    private void limpaCampo() {
        arquivo = new Arquivo();
    }

    public String novo() {
        arquivo = new Arquivo();
        return "/paginas/adm/arquivo/cadastro?faces-redirect=true";
    }

    public String listar() {
        return "/paginas/adm/arquivo/lista?faces-redirect=true";
    }

    public ArquivoFacade getArquivoFacade() {
        return arquivoFacade;
    }

    public void setArquivoFacade(ArquivoFacade arquivoFacade) {
        this.arquivoFacade = arquivoFacade;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

    public List<Arquivo> getArquivos() {
        arquivos = arquivoFacade.getAll();
        return arquivos;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setFile(DefaultStreamedContent file) {
        this.file = file;
    }

    public List<Arquivo> getArquivosByProdutos() {
        arquivosByProdutos = arquivoFacade.arquivoByProtudo(4L);
        return arquivosByProdutos;
    }

}
