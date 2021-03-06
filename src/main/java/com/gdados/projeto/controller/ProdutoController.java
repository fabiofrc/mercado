/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.ComentarioFacade;
import com.gdados.projeto.facade.ProdutoFacade;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.model.SubCategoria;
import com.gdados.projeto.security.UsuarioLogado;
import com.gdados.projeto.security.UsuarioSistema;
import com.gdados.projeto.util.filter.ProdutoFilter;
import com.gdados.projeto.util.msg.Msg;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@ApplicationScoped
public class ProdutoController implements Serializable {

    private Produto produto = new Produto();
    @Inject
    private ProdutoFacade produtoFacade;
    private List<Produto> produtos;

    private List<Produto> produtosDisponivel;
    private List<Produto> produtosDestaque;
    private List<Produto> produtosByPessoaJuridica;

    @Inject
    private ProdutoFilter produtoFilter;

    @Inject
    private SubCategoria categoria;

    private String file;
    private byte[] arquivo;
    private String paramentroCatagoria;
    private String paramentroTitulo;

    private UsuarioSistema usuario;

    @Inject
    private ComentarioFacade comentarioFacade;

    public void inicializar() {
        System.out.println("iniciando.....");
        if (produto == null) {
            limpaCampo();
        }
    }

    public ProdutoController() {
        if (produtoFilter == null) {
            produtoFilter = new ProdutoFilter();
        }
        if (produtosDisponivel == null) {
            produtosDisponivel = new ArrayList<>();
        }

        if (produtosDestaque == null) {
            produtosDestaque = new ArrayList<>();
        }

        if (produtoFacade == null) {
            produtoFacade = new ProdutoFacade();
        }
        if (produto == null) {
            limpaCampo();
        }
        carregaFilter();
    }

    public void onSlideEnd(SlideEndEvent event) {
        FacesMessage message = new FacesMessage("Slide Ended", "Value: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String pesquisarProdutoFilter() {
        try {
            produtosDestaque = produtoFacade.buscaNoticiaByFiltro1(produtoFilter);
            Msg.addMsgInfo("Atualizando pesquisa...");
            limpaFilter();
            return "/paginas/plb/produto/produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String atualizaHome() {
        try {
            produtosDestaque = produtoFacade.getAll();
            Msg.addMsgInfo("Atualizando pesquisa...");
            limpaFilter();
            return "../Home?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String salvar() {
        try {
            LocalDate dataRegistro = LocalDate.now();
            if (produto.getId() == null) {
                produto.setDataRegistro(dataRegistro);
                produtoFacade.save(produto);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso");
                return "lista?faces-redirect=true";
            } else {
                produto.setDataatuAlizacao(dataRegistro);
                produtoFacade.update(produto);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso");
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String view(Long id) {
        try {
            produto = produtoFacade.getById(id);
            produto.getSubCategoria();
            return "/paginas/plb/produto/detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String detalhes(Long id) {
        try {
            produto = produtoFacade.getById(id);
            produto.getSubCategoria();
            return "/paginas/adm/produto/detalhes?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            produto = produtoFacade.getById(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String editar_produto(Long id) {
        try {
            produto = produtoFacade.getById(id);
            return "cadastro_produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Produto noticia) {
        try {
            produtoFacade.delete(noticia);
            getProdutos();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
    }

    private List<Produto> carregaFilter() {
        try {
            produtosDestaque = new ArrayList<>();
            produtosDestaque = produtoFacade.getAll();

            if (produtoFilter.getTitulo() == null && produtoFilter.getCategoria() == null) {
                produtosDestaque = produtoFacade.getAll();
            } else {
                produtosDestaque = produtoFacade.buscaNoticiaByFiltro1(produtoFilter);
            }
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return produtosDisponivel;
    }

    public void buscaProdutoBySubCategoria(Long id) {
        try {
            produtosDestaque = produtoFacade.listaNoticiaBySubCategoria(id);
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
    }

    public String buscaProdutoByCategoria(Long id) {
        try {
            produtosDestaque = produtoFacade.listaProdutoByCategoria(id);
            return "/paginas/plb/produto/produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return null;
    }

    public String buscaProdutoByPessoaJuridica(Long id) {
        try {
            produtosDestaque = produtoFacade.listaNoticiaByPessoaJuridica(id);
            return "/paginas/plb/produto/produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return null;
    }

    public String buscaProdutoByPessoaJuridica() {
        try {
            usuario = getUsuarioLogado();
            produtosByPessoaJuridica = produtoFacade.listaNoticiaByPessoaJuridica(usuario.getUsuario().getPessoa().getId());
            return "/paginas/pf/pessoa_juridica/meus_produtos?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return null;
    }

    public String buscaProdutoByPromocao(Long id) {
        try {
            produtosDestaque = produtoFacade.listaProdutoByPromocao(id);
            return "/paginas/plb/produto/produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return null;
    }

    public void limpaFilter() {
        produtoFilter = new ProdutoFilter();
        categoria = new SubCategoria();
        setParamentroTitulo(null);
        setParamentroCatagoria(null);
    }

    public void limparFiltro() {
        try {
            if (produtoFilter != null) {
                limpaFilter();
                produtosDestaque = produtoFacade.getAll();
            }
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }

    }

    public List<String> pesquisarPorTitulo(String titulo) {
        return this.produtoFacade.nomeQueContem(titulo);
    }

    public void handleFileUpload(FileUploadEvent event) {
        byte[] content = event.getFile().getContents();
        System.out.println(content.length);
        produto.setArquivo(content);
    }

    public StreamedContent getImage1() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String studentId = context.getExternalContext().getRequestParameterMap().get("id");
            produto = produtoFacade.getById(Long.valueOf(studentId));
            return new DefaultStreamedContent(new ByteArrayInputStream(produto.getArquivo()));
        }
    }

    public void addMessageDisponivel() {
        String summary = produto.isStatus() ? "Disponivel" : "Não disponivel";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void addMessageDestaque() {
        String summary = produto.isDestaque() ? "Em destaque" : "Sem destaque";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public String lista() {
        return "/paginas/adm/produto/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/produto/cadastro?faces-redirect=true";
    }

    public String novoProduto() {
        limpaCampo();
        return "/paginas/pf/pessoa_juridica/cadastro_produto?faces-redirect=true";
    }

    public String meusProdutos() {
        getProdutosByPessoaJuridica();
        return "/paginas/pf/pessoa_juridica/lista?faces-redirect=true";
    }

    public String visualisarProdutos() {
        try {
            produtosDestaque = produtoFacade.getAll();
            produtosDisponivel = produtoFacade.getAll();
            return "/paginas/plb/produto/produto?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro" + e.getLocalizedMessage());
        }
        return null;
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

    private void limpaCampo() {
        produto = new Produto();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ProdutoFacade getProdutoFacade() {
        return produtoFacade;
    }

    public void setProdutoFacade(ProdutoFacade produtoFacade) {
        this.produtoFacade = produtoFacade;
    }

    public List<Produto> getProdutos() {
        produtos = produtoFacade.getAll();
        return produtos;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public int getContador() {
        return produtoFacade.count();
    }

    public boolean isEditando() {
        return this.produto.getId() != null;
    }

    public List<Produto> getProdutosDisponivel() {
        return produtosDisponivel;
    }

    public ProdutoFilter getProdutoFilter() {
        return produtoFilter;
    }

    public void setProdutoFilter(ProdutoFilter produtoFilter) {
        this.produtoFilter = produtoFilter;
    }

    public String getParamentroCatagoria() {
        return paramentroCatagoria;
    }

    public void setParamentroCatagoria(String paramentroCatagoria) {
        this.paramentroCatagoria = paramentroCatagoria;
    }

    public SubCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(SubCategoria categoria) {
        this.categoria = categoria;
    }

    public String getParamentroTitulo() {
        return paramentroTitulo;
    }

    public void setParamentroTitulo(String paramentroTitulo) {
        this.paramentroTitulo = paramentroTitulo;
    }

    public List<Produto> getProdutosDestaque() {
        return produtosDestaque;
    }

    public List<Produto> getProdutosByPessoaJuridica() {
        return produtosByPessoaJuridica;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public int getNotaByProduto(Long id) {
        return (int) comentarioFacade.mediaComentarioByProduto(id);
    }

    public int getNotaByUsuario(Long id) {
        return (int) comentarioFacade.mediaComentarioByProduto(id);
    }

    public ComentarioFacade getComentarioFacade() {
        return comentarioFacade;
    }

    public void setComentarioFacade(ComentarioFacade comentarioFacade) {
        this.comentarioFacade = comentarioFacade;
    }

    public long getContadorProdutoByPessoaJuridica(Long id) {
        return produtoFacade.contaProdutoByPessoaJuridica(id);
    }

    public long getContadorProdutoByPromocao(Long id) {
        return produtoFacade.contaProdutoByPromocao(id);
    }

    public long getContadorProdutoByCategoria(Long id) {
        return produtoFacade.contaProdutoBySubCategoria(id);
    }
}
