/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
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

    public void inicializar() {
        System.out.println("iniciando.....");
        if (produto == null) {
            limpaCampo();
        }
    }

    @PostConstruct
    public void init() {
        carregaFilter();
        if (produto == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            if (produto.getId() == null) {
                produtoFacade.save(produto);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso");
            } else {
                produtoFacade.update(produto);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso");
            }
        } catch (Exception e) {
            System.out.println("com.gdados.projeto.controller.UsuarioController.salvar()");
        }
        return null;
    }

    public String view(Long id) {
        try {
            produto = produtoFacade.getAllByCodigo(id);
            produto.getSubCategoria();
            return "/paginas/plb/produto/detalhes?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public String editar(Long id) {
        try {
            produto = produtoFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public String editar_produto(Long id) {
        try {
            produto = produtoFacade.getAllByCodigo(id);
            return "cadastro_produto?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public void deletar(Produto noticia) {
        try {
            produtoFacade.delete(noticia);
            getProdutos();
        } catch (Exception e) {
        }
    }

    private List<Produto> carregaFilter() {
        try {
            produtosDestaque = new ArrayList<>();
            produtosDestaque = produtoFacade.getAllDestaque();

            if (produtoFilter.getTitulo() == null && produtoFilter.getCategoria() == null) {
                produtosDisponivel = produtoFacade.getAll();
            } else {
                produtosDisponivel = produtoFacade.buscaNoticiaByFiltro1(produtoFilter);
            }
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return produtosDisponivel;
    }

    public String buscaNoticiaByFilter() {
        try {
            produtoFilter.setTitulo(paramentroTitulo);
            produtoFilter.setCategoria("");
//            System.out.println("linha 05");

            if (produtoFilter.getTitulo() != null || produtoFilter.getCategoria() != null) {
                produtosDisponivel = produtoFacade.buscaNoticiaByFiltro1(produtoFilter);
                limpaFilter();
                getProdutosDisponivel();
                System.err.println("linha 01");
                if (produtosDisponivel.isEmpty()) {
                    System.err.println("linha 02");
                    carregaFilter();
                    return "/paginas/plb/produto/produto?faces-redirect=true";
                } else {
                    System.err.println("linha 03");
                    getProdutosDisponivel();
                    return "/paginas/plb/produto/produto?faces-redirect=true";
                }
            } else {
                System.err.println("linha 04");
                produtosDisponivel = produtoFacade.getAll();
                getProdutosDisponivel();
                limpaFilter();
                return "/paginas/plb/produto/produto?faces-redirect=true";
            }

        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        return null;
    }

    public void buscaNoticiaByCategoria(Long id) {
        try {
            produtosDisponivel = produtoFacade.listaNoticiaBySubCategoria(id);
            System.out.println("certo: " + produtosDisponivel);
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
    }

    public String buscaProdutoByCategoria(Long id) {
        try {
            produtosDisponivel = produtoFacade.listaProdutoByCategoria(id);
            System.out.println("certo: " + produtosDisponivel);
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
            produto = produtoFacade.getAllByCodigo(Long.valueOf(studentId));
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

    public String visualisarNoticias() {
        try {
            produtosDestaque = produtoFacade.getAllDestaque();
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
        usuario = getUsuarioLogado();
        if (usuario.getUsuario().getPessoa().getId() != null) {
            produtosByPessoaJuridica = produtoFacade.listaNoticiaByPessoaJuridica(usuario.getUsuario().getPessoa().getId());
            return produtosByPessoaJuridica;
        }
        return null;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

}
