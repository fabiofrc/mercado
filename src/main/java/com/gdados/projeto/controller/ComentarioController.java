/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.ComentarioFacade;
import com.gdados.projeto.facade.ProdutoFacade;
import com.gdados.projeto.model.Categoria;
import com.gdados.projeto.model.Comentario;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.security.UsuarioLogado;
import com.gdados.projeto.security.UsuarioSistema;
import com.gdados.projeto.util.Relatorio.ContadorComentariosByNoticia;
import com.gdados.projeto.util.msg.Msg;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@ApplicationScoped
public class ComentarioController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Comentario comentario;
    @Inject
    private ComentarioFacade comentarioFacade;

    private List<Comentario> comentarios;
    private List<Comentario> comentarioByNoticia;

    private UsuarioSistema usuario;
    private Produto noticia = new Produto();

    private Long contadorComentarioByNoticia;

    private PieChartModel pieModeloComentario;
    private LineChartModel dateModeloComentario;

    @ManagedProperty(value = "#{produtoController}")
    private ProdutoController produtoController;

//    public ComentarioController() {
//        if (comentario == null) {
//            limpaCampo();
//        }
//        if (noticia == null) {
//            noticia = new Noticia();
//        }
//    }
    public void inicializar() {
        System.out.println("iniciando.....");
        if (noticia == null) {
            limpaCampo();
        }
    }

    @PostConstruct
    public void init() {
        if (comentario == null) {
            limpaCampo();
        }
        if (noticia == null) {
            noticia = new Produto();
        }
        createPieModels();
    }

    public String salvar() {
        try {
            usuario = getUsuarioLogado();

            Date date = new Date(System.currentTimeMillis());
            if (comentario.getId() == null) {
                comentario.setDataRegistro(date);
                comentario.setUsuario(usuario.getUsuario());
                comentarioFacade.save(comentario);
                limpaCampo();
                Msg.addMsgInfo("Sua mensagem foi enviada com sucesso!");
                return "lista?faces-redirect=true";
            } else {
                comentarioFacade.update(comentario);
                limpaCampo();
                Msg.addMsgInfo("Sua mensagem foi enviada com sucesso!");
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String enviarComentario(Long id) {
        try {
            ProdutoFacade pf = new ProdutoFacade();
            noticia = pf.getAllByCodigo(id);
            usuario = getUsuarioLogado();

            Date date = new Date();
            if (comentario.getId() == null) {
                comentario.setDataRegistro(date);
                comentario.setUsuario(usuario.getUsuario());
                comentario.setProduto(noticia);
                comentarioFacade.save(comentario);
                limpaCampo();
                Msg.addMsgInfo("Sua mensagem foi enviada com sucesso!");
                return "cadastro_sucesso?faces-redirect=true";
            } else {
                comentarioFacade.update(comentario);
                limpaCampo();
                Msg.addMsgInfo("Sua mensagem foi enviada com sucesso!");
                return "cadastro_sucesso?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "/login?faces-redirect=true";
        }
    }

    public String view(Long id) {
        try {
            comentario = comentarioFacade.getAllByCodigo(id);
            return "detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            comentario = comentarioFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Categoria categoria) {
        try {
            comentarioFacade.delete(comentario);
            getComentarios();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public List<Comentario> buscaNoticia(Long id) {
        comentarioByNoticia = comentarioFacade.listaComentarioByNoticia(id);
        return comentarioByNoticia;
    }

    @Produces
    @UsuarioLogado
    public UsuarioSistema getUsuarioLogado() {
        usuario = null;
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }
        return usuario;
    }

    public boolean verificarUsuarioExistente() {
        UsuarioSistema u = getUsuarioLogado();
        return u != null;
    }

    private void createPieModels() {
        createPieModel(getNoticia());
        createDateModel();
    }

    public void createPieModel(Produto noticia) {
        try {
            if (noticia != null) {
                long totalByNoticia = comentarioFacade.contaComentarioByProduto(noticia.getId());
                long total = ContadorComentariosByNoticia.contaComentarioTotal(comentarioFacade);
                double porcentagem = ContadorComentariosByNoticia.porcentagemComentarioByNoticia(totalByNoticia, total);
                double diferenca = ContadorComentariosByNoticia.diferenca(porcentagem);

                pieModeloComentario = new PieChartModel();
                pieModeloComentario.set("Comentários", porcentagem);
                pieModeloComentario.set("Outros", diferenca);

                pieModeloComentario.setTitle(noticia.getNome());
                pieModeloComentario.setLegendPosition("e");
                pieModeloComentario.setFill(false);
                pieModeloComentario.setShowDataLabels(true);
                pieModeloComentario.setDiameter(150);
                pieModeloComentario.setShadow(false);
            } else {
                long totalByNoticia = comentarioFacade.contaComentarioByProduto(1L);
                long total = ContadorComentariosByNoticia.contaComentarioTotal(comentarioFacade);
                double porcentagem = ContadorComentariosByNoticia.porcentagemComentarioByNoticia(totalByNoticia, total);
                double diferenca = ContadorComentariosByNoticia.diferenca(porcentagem);

                pieModeloComentario = new PieChartModel();
                pieModeloComentario.set("Comentários", porcentagem);
                pieModeloComentario.set("Outros", diferenca);

                pieModeloComentario.setTitle("Comentário total");
                pieModeloComentario.setLegendPosition("e");
                pieModeloComentario.setFill(false);
                pieModeloComentario.setShowDataLabels(true);
                pieModeloComentario.setDiameter(150);
                pieModeloComentario.setShadow(false);

            }
        } catch (Exception e) {
            System.out.println("erro" + e.getLocalizedMessage());
        }
    }

    private void createDateModel() {
        dateModeloComentario = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        comentarios = new ArrayList<>();
        for (Comentario c : comentarios) {
            dateModeloComentario = new LineChartModel();
//            series1.set(c.getDataRegistro(), c.getId());
            series1.set("2014-01-01", 51);
//            dateModeloComentario.addSeries(series1);
        }
//        series1.set("2014-01-01", 51);
//        series1.set("2014-01-06", 22);
//        series1.set("2014-08-12", 65);
//        series1.set("2015-01-18", 74);
//        series1.set("2015-01-24", 60);
//        series1.set("2014-01-30", 51);

        dateModeloComentario.addSeries(series1);

        dateModeloComentario.setTitle("Zoom for Details");
        dateModeloComentario.setZoom(true);
        dateModeloComentario.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2015-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModeloComentario.getAxes().put(AxisType.X, axis);
    }

    public PieChartModel getPieModeloComentario() {
        return pieModeloComentario;
    }

    public LineChartModel getDateModeloComentario() {
        return dateModeloComentario;
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item seleciondo",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String lista() {
        return "/paginas/adm/comentario/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/plb/comentario/cadastro?faces-redirect=true";
    }

    public String comentariosDetalhes() {
        return "/paginas/adm/comentario/comentario?faces-redirect=true";
    }

    public String timeline() {
        return "/paginas/adm/comentario/timeline?faces-redirect=true";
    }

    public String exportarComentario() {
        return "/paginas/adm/comentario/exportar_comentario?faces-redirect=true";
    }

    public String grafico() {
        return "/paginas/adm/comentario/grafico?faces-redirect=true";
    }

    private void limpaCampo() {
        comentario = new Comentario();
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public ComentarioFacade getComentarioFacade() {
        return comentarioFacade;
    }

    public void setComentarioFacade(ComentarioFacade comentarioFacade) {
        this.comentarioFacade = comentarioFacade;
    }

    public List<Comentario> getComentarios() {
        comentarios = comentarioFacade.getAll();
        return comentarios;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public Produto getNoticia() {
        return noticia;
    }

    public void setNoticia(Produto noticia) {
        this.noticia = noticia;
    }

    public List<Comentario> getComentarioByNoticia() {
        comentarioByNoticia = comentarioFacade.listaComentarioByNoticia(1L);
        return comentarioByNoticia;
    }

    public Long getContadorComentarioByNoticia(Long id) {
        contadorComentarioByNoticia = comentarioFacade.contaComentarioByProduto(id);
        return contadorComentarioByNoticia;
    }

    public int getContador() {
        return comentarioFacade.count();
    }

    public ProdutoController getProdutoController() {
        return produtoController;
    }

    public void setProdutoController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }

}
