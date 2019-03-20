/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.ComentarioFacade;
import com.gdados.projeto.facade.GrupoFacade;
import com.gdados.projeto.facade.PessoaFisicaFacade;
import com.gdados.projeto.facade.UsuarioFacade;
import com.gdados.projeto.model.Comentario;
import com.gdados.projeto.model.PessoaFisica;
import com.gdados.projeto.model.Usuario;
import com.gdados.projeto.security.MyPasswordEncoder;
import com.gdados.projeto.security.UsuarioLogado;
import com.gdados.projeto.security.UsuarioSistema;
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
public class PessoaFisicaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private PessoaFisica pessoaFisica;
    @Inject
    private PessoaFisicaFacade pessoaFisicaFacade;
    private List<PessoaFisica> pessoaFisicas;

    @Inject
    private GrupoFacade grupoFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    private UsuarioSistema usuario;

    private String confirmaSenha;

    @Inject
    private ComentarioFacade comentarioFacade;
    private List<Comentario> comentarioByParticpantes;

    private double tamanhoArquivo;

    public PessoaFisicaController() {
        if (pessoaFisica == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
//            pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
//            setConfirmaSenha(MyPasswordEncoder.getPasswordEncoder(confirmaSenha));
            if (verificarUsuarioExistente() && pessoaFisica.getId() == null) {
                Msg.addMsgWarn("Já existe um usuário com o e-mail informado.");
            } else {
                if (pessoaFisica.getId() == null) {
                    pessoaFisica.getUsuario().getGrupos().clear();
                    pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getAllByCodigo(2L));
                    pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
                    pessoaFisicaFacade.save(pessoaFisica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação realizada com sucesso!");
                    return "cadastro_sucesso?faces-redirect=true";
                } else {
                    pessoaFisica.getUsuario().getGrupos().clear();
                    pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getAllByCodigo(2L));
                    pessoaFisicaFacade.update(pessoaFisica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação atualizada com sucesso!");
                    return "cadastro_sucesso?faces-redirect=true";
                }
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
        }
        return null;
    }

    public String atualizarPerfil() {
        try {
            if (verificarUsuarioExistente() && pessoaFisica.getId() == null) {
                Msg.addMsgWarn("Já existe um usuário com o e-mail informado.");
            } else {
                if (pessoaFisica.getId() == null) {
                    pessoaFisica.getUsuario().getGrupos().clear();
                    pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getAllByCodigo(2L));
                    pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
                    pessoaFisicaFacade.save(pessoaFisica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação realizada com sucesso!");
                    return "cadastro_perfil?faces-redirect=true";
                } else {
                    pessoaFisica.getUsuario().getGrupos().clear();
                    pessoaFisica.getUsuario().getGrupos().add(0, grupoFacade.getAllByCodigo(2L));
                    pessoaFisicaFacade.update(pessoaFisica);
                    Msg.addMsgInfo("Operação atualizada com sucesso!");
                    return "cadastro_perfil?faces-redirect=true";
                }
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
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

    public PessoaFisica guardar(PessoaFisica p) {
        if (p.isNovo()) {
            p.getUsuario().getGrupos().clear();
            p.getUsuario().getGrupos().add(0, grupoFacade.getAllByCodigo(3L));
        }
        pessoaFisica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaFisica.getUsuario().getSenha()));
        return pessoaFisica;
    }

    public String editar(Long id) {
        try {
            pessoaFisica = pessoaFisicaFacade.getAllByCodigo(id);
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
            System.out.println("Usuario: " + usuario.getUsuario().getId());
            return "/paginas/pf/pessoa_fisica/cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(PessoaFisica participante) {
        try {
            pessoaFisicaFacade.delete(participante);
            getPessoaFisicas();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
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

    public String visualisarPedido() {
        return "/paginas/pf/pessoa_fisica/meus_pedidos?faces-redirect=true";
    }

    public boolean isEditando() {
        return this.pessoaFisica.getId() != null;
    }

    public void limpaCampoParticipante() {
        pessoaFisica = new PessoaFisica();
    }

    private void limpaCampo() {
        pessoaFisica = new PessoaFisica();
    }

    public void limpaCampoNovo() {
        pessoaFisica = new PessoaFisica();
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public PessoaFisicaFacade getPessoaFisicaFacade() {
        return pessoaFisicaFacade;
    }

    public void setPessoaFisicaFacade(PessoaFisicaFacade pessoaFisicaFacade) {
        this.pessoaFisicaFacade = pessoaFisicaFacade;
    }

    public List<PessoaFisica> getPessoaFisicas() {
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

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public List<Comentario> comentarioByParticpantes() {
        return comentarioByParticpantes;
    }

    public List<Comentario> buscaComentarioByParticipante(Long id) {
        comentarioByParticpantes = comentarioFacade.listaComentarioByParticipante(id);
        return comentarioByParticpantes;
    }

    public double getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

}
