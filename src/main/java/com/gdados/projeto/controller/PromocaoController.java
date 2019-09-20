/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.PromocaoFacade;
import com.gdados.projeto.model.Promocao;
import com.gdados.projeto.util.msg.Msg;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
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
public class PromocaoController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Promocao promocao;
    @Inject
    private PromocaoFacade promocaoFacade;
    private List<Promocao> promocaos;
    private List<Promocao> promocaoAtivaByData;

    public PromocaoController() {
        if (promocao == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            if (promocao.getId() == null) {
                promocaoFacade.save(promocao);
                Msg.addMsgInfo("Operação realizada com sucesso!");
                limpaCampo();
                return "lista?faces-redirect=true";
            } else {
                promocaoFacade.update(promocao);
                Msg.addMsgInfo("Operação realizada com sucesso!");
                limpaCampo();
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String view(Long id) {
        try {
            promocao = promocaoFacade.getById(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            promocao = promocaoFacade.getById(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Promocao promocao) {
        try {
            promocaoFacade.delete(promocao);
            getPromocaos();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        byte[] content = event.getFile().getContents();
        System.out.println(content.length);
        promocao.setArquivo(content);
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
            promocao = promocaoFacade.getById(Long.valueOf(studentId));
            return new DefaultStreamedContent(new ByteArrayInputStream(promocao.getArquivo()));
        }
    }

    public String lista() {
        return "/paginas/adm/promocao/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/promocao/cadastro?faces-redirect=true";
    }

    private void limpaCampo() {
        promocao = new Promocao();
    }

    public int getContador() {
        return promocaoFacade.count();
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

    public PromocaoFacade getPromocaoFacade() {
        return promocaoFacade;
    }

    public void setPromocaoFacade(PromocaoFacade promocaoFacade) {
        this.promocaoFacade = promocaoFacade;
    }

    public List<Promocao> getPromocaos() {
        promocaos = promocaoFacade.getAll();
        return promocaos;
    }

    public void setPromocaos(List<Promocao> promocaos) {
        this.promocaos = promocaos;
    }

    public List<Promocao> getPromocaoAtivaByData() {
        promocaoAtivaByData = promocaoFacade.listaPromocaoAtivaByData();
        return promocaoAtivaByData;
    }

}
