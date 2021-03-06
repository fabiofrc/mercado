/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.ComentarioFacade;
import com.gdados.projeto.facade.GrupoFacade;
import com.gdados.projeto.facade.ClienteFacade;
import com.gdados.projeto.facade.UsuarioFacade;
import com.gdados.projeto.model.Comentario;
import com.gdados.projeto.model.Endereco;
import com.gdados.projeto.model.Cliente;
import com.gdados.projeto.model.Usuario;
import com.gdados.projeto.security.MyPasswordEncoder;
import com.gdados.projeto.security.UsuarioLogado;
import com.gdados.projeto.security.UsuarioSistema;
import com.gdados.projeto.util.endereco.BuscadorCep;
import com.gdados.projeto.util.endereco.WebServiceCep;
import com.gdados.projeto.util.msg.Msg;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@SessionScoped
public class ClienteController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cliente pessoaFisica;
    @Inject
    private ClienteFacade pessoaFisicaFacade;
    private List<Cliente> pessoaFisicas;

    @Inject
    private GrupoFacade grupoFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    private UsuarioSistema usuario;

    private Endereco endereco;

    private BuscadorCep buscadorCep;

    private String senha;
    private String confirmaSenha;

    @Inject
    private ComentarioFacade comentarioFacade;
    private List<Comentario> comentarioByParticpantes;

    private double tamanhoArquivo;

    public ClienteController() {
        if (pessoaFisica == null) {
            limpaCampo();
        }
        if (endereco == null) {
            endereco = new Endereco();
        }
        if (buscadorCep == null) {
            buscadorCep = new BuscadorCep();
        }
    }

    public String salvar() {
        try {

            if (pessoaFisica.getId() == null) {
                if (senha.equalsIgnoreCase(confirmaSenha)) {
                    pessoaFisica.getUsuario().getGrupos().clear();
                    pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getById(2L));
                    pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(senha));
                    pessoaFisicaFacade.save(pessoaFisica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação realizada com sucesso!");
                    return "cadastro_sucesso?faces-redirect=true";
                } else {
                    Msg.addMsgWarn("Senhas diferente!");
                }
            } else {
                pessoaFisica.getUsuario().getGrupos().clear();
                pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getById(2L));
                pessoaFisicaFacade.update(pessoaFisica);
                limpaCampo();
                Msg.addMsgInfo("Operação atualizada com sucesso!");
                return "cadastro_sucesso?faces-redirect=true";
            }

        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getLocalizedMessage());
        }
        return null;
    }

    public String salvarNovaSenha() {
        try {
            if (pessoaFisica.getId() != null) {
                pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
                pessoaFisicaFacade.update(pessoaFisica);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso!");
                return "cadastro_perfil?faces-redirect=true";
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
        }
        return null;
    }

    public Cliente guardar(Cliente p) {
        if (p.isNovo()) {
            p.getUsuario().getGrupos().clear();
            p.getUsuario().getGrupos().add(0, grupoFacade.getById(3L));
        }
        pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
        return pessoaFisica;
    }

    public String editar(Long id) {
        try {
            pessoaFisica = pessoaFisicaFacade.getById(id);
            setSenha(pessoaFisica.getUsuario().getSenha());
            setConfirmaSenha(pessoaFisica.getUsuario().getSenha());
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String editarPerfil() {
        try {
            usuario = getUsuarioLogado();
            pessoaFisica = pessoaFisicaFacade.buscaParticipanteByIdUsuario(usuario.getUsuario().getId());
            return "/paginas/pf/pessoa_fisica/cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Cliente participante) {
        try {
            pessoaFisicaFacade.delete(participante);
            getPessoaFisicas();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
    }

    public void buscarEnderecoByCep() {
        try {
            WebServiceCep webServiceCep = WebServiceCep.searchCep(endereco.getCep());
            //A ferramenta de busca ignora qualquer caracter que não seja número.

            //caso a busca ocorra bem, imprime os resultados.
            if (webServiceCep.wasSuccessful()) {

                endereco.setCep(webServiceCep.getCep());
                endereco.setLogradouro(webServiceCep.getLogradouroFull());
                endereco.setBairro(webServiceCep.getBairro());
                endereco.setCidade(webServiceCep.getCidade());
                endereco.setUf(webServiceCep.getUf());

                System.out.println("Logradouro: " + endereco.getLogradouro());
                //caso haja problemas imprime as exce??es.
            } else {
                System.out.println("Erro numero: " + webServiceCep.getResulCode());

                System.out.println("Descrição do erro: " + webServiceCep.getResultText());
            }
        } catch (Exception e) {
            Msg.addMsgError("Erro: " + e.getLocalizedMessage());
        }
    }

    public boolean verificarUsuarioExistente() {
        Usuario usuarioExistente = usuarioFacade.verificaUsuario(pessoaFisica.getUsuario().getEmail());
        return usuarioExistente != null && usuarioExistente.getEmail().equalsIgnoreCase(pessoaFisica.getUsuario().getEmail());
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

    public void existe() {
        try {
            if (verificarUsuarioExistente()) {
                Msg.addMsgWarn("já exite um registro com esse email");
            }
        } catch (Exception e) {
            Msg.addMsgError("Erro: " + e.getLocalizedMessage());
        }
    }

    public void limpaCampoParticipante() {
        limpaCampo();
    }

    private void limpaCampo() {
        pessoaFisica = new Cliente();
        pessoaFisica.setEndereco(new Endereco());
        setConfirmaSenha(null);
        setSenha(null);
    }

    public void limpaCampoNovo() {
        pessoaFisica = new Cliente();
    }

    public String lista() {
        return "/paginas/adm/pessoa_fisica/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/plb/pessoa_fisica/cadastro?faces-redirect=true";
    }

    public String novaSenha() {
        pessoaFisica.getUsuario().setSenha(null);
        return "/paginas/pf/pessoa_fisica/cadastro_senha?faces-redirect=true";
    }

    public String visualisarComentario() {
        return "/paginas/pf/pessoa_fisica/meus_comentarios?faces-redirect=true";
    }

    public boolean isEditando() {
        return this.pessoaFisica.getId() != null;
    }

    public Cliente getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(Cliente pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public ClienteFacade getPessoaFisicaFacade() {
        return pessoaFisicaFacade;
    }

    public void setPessoaFisicaFacade(ClienteFacade pessoaFisicaFacade) {
        this.pessoaFisicaFacade = pessoaFisicaFacade;
    }

    public List<Cliente> getPessoaFisicas() {
        pessoaFisicas = pessoaFisicaFacade.getAll();
        return pessoaFisicas;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public GrupoFacade getGrupoFacade() {
        return grupoFacade;
    }

    public void setGrupoFacade(GrupoFacade grupoFacade) {
        this.grupoFacade = grupoFacade;
    }

    public int getContador() {
        return pessoaFisicaFacade.count();
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> comentarioByParticpantes() {
        return comentarioByParticpantes;
    }

    public List<Comentario> buscaComentarioByParticipante(Long id) {
        comentarioByParticpantes = comentarioFacade.listaComentarioByUsuario(id);
        return comentarioByParticpantes;
    }

    public double getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public BuscadorCep getBuscadorCep() {
        return buscadorCep;
    }

    public void setBuscadorCep(BuscadorCep buscadorCep) {
        this.buscadorCep = buscadorCep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

}
