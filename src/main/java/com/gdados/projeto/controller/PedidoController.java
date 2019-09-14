package com.gdados.projeto.controller;

import com.gdados.projeto.facade.PedidoFacade;
import com.gdados.projeto.facade.ClienteFacade;
import com.gdados.projeto.facade.LojaFacade;
import com.gdados.projeto.model.FormaPagamento;
import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.PedidoItem;
import com.gdados.projeto.model.Cliente;
import com.gdados.projeto.model.Loja;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.model.Status;
import com.gdados.projeto.model.Usuario;
import com.gdados.projeto.security.CurrentUser;
import com.gdados.projeto.security.UsuarioLogado;
import com.gdados.projeto.security.UsuarioSistema;
import com.gdados.projeto.service.EstoqueService;
import com.gdados.projeto.service.NegocioException;
import com.gdados.projeto.util.boleto.EmissorBoleto;
import com.gdados.projeto.util.msg.Msg;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@SessionScoped
public class PedidoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @CurrentUser
    private UsuarioSistema usuario;

    private Pedido carrinho;

    private List<Pedido> pedidos;

    private List<Pedido> pedidosByPessoaFisica;

    @Inject
    private ClienteFacade pessoaFisicaFacade;

    @Inject
    private PedidoFacade pedidoFacade;

    private PedidoItem item;

    @Inject
    private EmissorBoleto emissorBoleto;

    private Integer indexMenu;

    @Inject
    private EstoqueService estoqueService;

    private LineChartModel dateModeloPedido;

    public PedidoController() {
        limpar();
        createDateModel();
    }

    public void inicializar() {
        if (this.carrinho == null) {
            limpar();
        }
        this.recalcularCarrinho();
    }

    private void limpar() {
        item = new PedidoItem();
        carrinho = new Pedido();
    }

    public String getNomeCliente() {
        return carrinho.getUsuario().getPessoa().getNome() == null ? null : carrinho.getUsuario().getPessoa().getNome();
    }

    public void setNomeCliente(String nome) {
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public List<Cliente> completarCliente(String nome) {
        return this.pessoaFisicaFacade.porNome(nome);
    }

    public void adicionarItem(Produto produto) {
        try {
            LocalDate dataRegistro = LocalDate.now();
            if (existeItemComProduto(produto)) {
                Msg.addMsgWarn("O produto já está no carrinho!");
            } else {
                item = new PedidoItem();
                item.setDataRegistro(dataRegistro);
                item.setProduto(produto);
                item.setQuantidade(1);
                item.setValorUnitario(produto.getPrecoVenda());
                item.setPedido(carrinho);
                carrinho.setStatus(Status.PENDENTE);
                carrinho.getPedidoItems().add(item);
                Msg.addMsgInfo("Produto adicionado!");
            }
            recalcularCarrinho();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    public String adicionar(Produto produto) {
        try {
            LocalDate dataRegistro = LocalDate.now();
            if (existeItemComProduto(produto)) {
                Msg.addMsgWarn("O produto já está no carrinho!");
            } else {
                item = new PedidoItem();
                item.setDataRegistro(dataRegistro);
                item.setProduto(produto);
                item.setQuantidade(1);
                item.setValorUnitario(produto.getPrecoVenda());
                item.setPedido(carrinho);
                carrinho.setStatus(Status.PENDENTE);
                carrinho.getPedidoItems().add(item);
                Msg.addMsgInfo("Produto: " + item.getProduto().getNome() + " adicionado na cesta");
            }
            recalcularCarrinho();
            return "/paginas/plb/pedido/pedido?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;

    }

    public String removerItem(PedidoItem index) {
        try {
            carrinho.getPedidoItems().remove(index);
            recalcularCarrinho();
            Msg.addMsgWarn("Produto " + index + " removido da cesta!");
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void produtoSelecionado(SelectEvent event) {
        adicionarItem((Produto) event.getObject());
    }

    public void clienteSelecionado(SelectEvent event) {
        this.carrinho.setUsuario((Usuario) event.getObject());
    }

    public String salvar() {
        try {
            LocalDate dataRegistro = LocalDate.now();
            usuario = getUsuarioLogado();
            if (carrinho.getUsuario() == null) {
                carrinho.setUsuario(usuario.getUsuario());
            }

            carrinho.setDataRegistro(dataRegistro);
            carrinho = this.pedidoFacade.save(this.carrinho);
            estoqueService.baixarItensEstoque(carrinho);
            carrinho.getPedidoItems().clear();

            Msg.addInfoMessage("Carrinho atualizado com sucesso!");
            return "/paginas/plb/pedido/cadastro_sucesso?faces-redirect=true";
        } catch (NegocioException e) {
            System.out.println("erro: " + e.getLocalizedMessage());
            return "/login?faces-redirect=true";
        }
    }

    public String editar(Long id) {
        try {
            carrinho = pedidoFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Pedido pedido) {
        try {
            pedidoFacade.delete(pedido);
            getPedidos();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public String finalizar() {
        try {
            if (usuario != null && !carrinho.isVazio()) {
                salvar();
                return "pagamento";
            } else if (usuario == null && !carrinho.isVazio()) {
                Msg.addInfoMessage("Faça login para continuar.");
                return "/login";
            } else {
                Msg.addInfoMessage("O carrinho deve possuir pelo menos 1 item.");
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public void recalcularCarrinho() {
        if (this.carrinho != null) {
            this.carrinho.recalcularValorTotal();
        }
    }

    private boolean existeItemComProduto(Produto produto) {
        boolean existeItem = false;
        for (PedidoItem items : this.getCarrinho().getPedidoItems()) {
            if (produto.equals(items.getProduto())) {
                existeItem = true;
                break;
            }
        }
        return existeItem;
    }

    public void atualizarQuantidade(PedidoItem item, int linha) {
        if (item.getQuantidade() < 1) {
            if (linha == 0) {
                item.setQuantidade(1);
            } else {
                this.getCarrinho().getPedidoItems().remove(linha);
            }
        }
        this.carrinho.recalcularValorTotal();
    }

    public void sucesso() {
        limpar();
    }

    public void emitir() {
        LojaFacade pessoaJuridicaFacade = new LojaFacade();
        Loja cedente = pessoaJuridicaFacade.getAllByCodigo(2L);

        usuario = getUsuarioLogado();

        byte[] pdf = this.emissorBoleto.gerarBoleto(cedente, carrinho, (Cliente) usuario.getUsuario().getPessoa());
        enviarBoleto(pdf);
        limpar();
    }

    private void enviarBoleto(byte[] bPDF) {
        Date dataRegistro = new Date(System.currentTimeMillis());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Boleto_" + dataRegistro + "_mercado.pdf");
        try {
            response.setContentLength(bPDF.length);
            ServletOutputStream output = response.getOutputStream();
            output.write(bPDF, 0, bPDF.length);
            response.flushBuffer();
            System.out.println("Numero do pedido: " + carrinho.getId() + "\nBoleto emitido com sucesso! ");
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Boleto emitido com sucesso", null));

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar boleto", e);
        }
        FacesContext.getCurrentInstance().responseComplete();
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

    public String novoPedido() {
        try {
            usuario = getUsuarioLogado();
            if (usuario != null && !carrinho.isVazio()) {
                setIndexMenu(2);
                return "/paginas/plb/pedido/cadastro?faces-redirect=true";
            } else if (usuario == null) {
                Msg.addMsgWarn("Faça login para continuar.");
                return "/login";
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String buscaPedidoByUsuario() {
        try {
            usuario = getUsuarioLogado();
            pedidosByPessoaFisica = pedidoFacade.listaPedidoByUsuario(usuario.getUsuario().getId());
            return "/paginas/pf/pessoa_fisica/meus_pedidos?faces-redirect=true";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String lista() {
        return "/paginas/adm/pedido/lista?faces-redirect=true";
    }

    public String confirmarCompra() {
        setIndexMenu(3);
        return "/paginas/plb/pedido/confirmacao?faces-redirect=true";
    }

    public Date getDataHoje() {
        Calendar cal = Calendar.getInstance();
        Date dataHoje = cal.getTime();
        return dataHoje;
    }

    public boolean isEditando() {
        return this.carrinho.getId() != null;
    }

    public ClienteFacade getPessoaFisicaFacade() {
        return pessoaFisicaFacade;
    }

    public void setPessoaFisicaFacade(ClienteFacade pessoaFisicaFacade) {
        this.pessoaFisicaFacade = pessoaFisicaFacade;
    }

    public PedidoFacade getPedidoFacade() {
        return pedidoFacade;
    }

    public void setPedidoFacade(PedidoFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    public PedidoItem getItem() {
        return item;
    }

    public void setItem(PedidoItem item) {
        this.item = item;
    }

    public Pedido getCarrinho() {
        return carrinho;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public List<Pedido> getPedidos() {
        pedidos = pedidoFacade.getAll();
        return pedidos;
    }

    public List<Pedido> getPedidosByPessoaFisica() {
        return pedidosByPessoaFisica;
    }

    public Integer getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(Integer indexMenu) {
        this.indexMenu = indexMenu;
    }

    public EstoqueService getEstoqueService() {
        return estoqueService;
    }

    public void setEstoqueService(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    public LineChartModel getDateModeloPedido() {
        return dateModeloPedido;
    }

    public void setDateModeloPedido(LineChartModel dateModeloPedido) {
        this.dateModeloPedido = dateModeloPedido;
    }

    public int getContador() {
        return pedidoFacade.count();
    }

    private void createDateModel() {
        dateModeloPedido = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        pedidos = new ArrayList<>();
        for (Pedido p : pedidos) {
            dateModeloPedido = new LineChartModel();
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

        dateModeloPedido.addSeries(series1);

        dateModeloPedido.setTitle("Zoom for Details");
        dateModeloPedido.setZoom(true);
        dateModeloPedido.getAxis(AxisType.Y).setLabel("Values");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2015-02-01");
        axis.setTickFormat("%b %#d, %y");

        dateModeloPedido.getAxes().put(AxisType.X, axis);
    }

}
