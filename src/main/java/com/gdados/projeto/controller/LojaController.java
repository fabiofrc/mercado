/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.GrupoFacade;
import com.gdados.projeto.facade.LojaFacade;
import com.gdados.projeto.facade.UsuarioFacade;
import com.gdados.projeto.model.Loja;
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
public class LojaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Loja pessoaJuridica;
   
    @Inject
    private LojaFacade pessoaJuridicaFacade;
    
    private List<Loja> pessoaJuridicas;

    @Inject
    private GrupoFacade grupoFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    private UsuarioSistema usuario;

    private String confirmaSenha;

    private double tamanhoArquivo;

    public LojaController() {
        if (pessoaJuridica == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            if (verificarUsuarioExistente() && pessoaJuridica.getId() == null) {
                Msg.addMsgWarn("Já existe um usuário com o e-mail informado.");
            } else {
                if (pessoaJuridica.getId() == null) {
                    pessoaJuridica.getUsuario().getGrupos().clear();
                    pessoaJuridica.getUsuario().getGrupos().add(0, grupoFacade.getById(3L));
                    pessoaJuridica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaJuridica.getUsuario().getSenha()));
                    pessoaJuridicaFacade.save(pessoaJuridica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação realizada com sucesso!");
                    return "lista?faces-redirect=true";
                } else {
                    pessoaJuridica.getUsuario().getGrupos().clear();
                    pessoaJuridica.getUsuario().getGrupos().add(0, grupoFacade.getById(3L));
                    pessoaJuridicaFacade.update(pessoaJuridica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação atualizada com sucesso!");
                    return "lista?faces-redirect=true";
                }
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
        }
        return null;
    }

    public String atualizarPerfil() {
        try {
            if (verificarUsuarioExistente() && pessoaJuridica.getId() == null) {
                Msg.addMsgWarn("Já existe um usuário com o e-mail informado.");
            } else {
                if (pessoaJuridica.getId() == null) {
                    pessoaJuridica.getUsuario().getGrupos().clear();
                    pessoaJuridica.getUsuario().getGrupos().add(0, grupoFacade.getById(3L));
                    pessoaJuridica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaJuridica.getUsuario().getSenha()));
                    pessoaJuridicaFacade.save(pessoaJuridica);
                    limpaCampo();
                    Msg.addMsgInfo("Operação realizada com sucesso!");
                    return "cadastro_perfil?faces-redirect=true";
                } else {
                    pessoaJuridica.getUsuario().getGrupos().clear();
                    pessoaJuridica.getUsuario().getGrupos().add(0, grupoFacade.getById(2L));
                    pessoaJuridicaFacade.update(pessoaJuridica);
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
            if (pessoaJuridica.getId() != null) {
                pessoaJuridica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaJuridica.getUsuario().getSenha()));
                pessoaJuridicaFacade.update(pessoaJuridica);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso!");
                return "cadastro_perfil?faces-redirect=true";
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
        }
        return null;
    }

    public Loja guardar(Loja p) {
        if (p.isNovo()) {
            p.getUsuario().getGrupos().clear();
            p.getUsuario().getGrupos().add(0, grupoFacade.getById(3L));
        }
        pessoaJuridica.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(pessoaJuridica.getUsuario().getSenha()));
        return pessoaJuridica;
    }

    public String editar(Long id) {
        try {
            pessoaJuridica = pessoaJuridicaFacade.getById(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String editarPerfil() {
        try {
            usuario = getUsuarioLogado();
            pessoaJuridica = pessoaJuridicaFacade.buscaPessoaJuridicaByIdUsuario(usuario.getUsuario().getId());
            System.out.println("Usuario: " + pessoaJuridica.getId());
            return "/paginas/pf/pessoa_juridica/cadastro?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String meusDados() {
        try {
            return "/paginas/pf/pessoa_juridica/cadastro_perfil?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public void deletar(Loja pessoaJuridica) {
        try {
            pessoaJuridicaFacade.delete(pessoaJuridica);
            getPessoaJuridicas();
        } catch (Exception e) {
        }
    }

    public boolean verificarUsuarioExistente() {
        Usuario usuarioExistente = usuarioFacade.verificaUsuario(pessoaJuridica.getUsuario().getEmail());
        return usuarioExistente != null && usuarioExistente.getEmail().equalsIgnoreCase(pessoaJuridica.getUsuario().getEmail());
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
        return "/paginas/adm/pessoa_juridica/lista?faces-redirect=true";
    }

    public String meusProdutos() {
        return "/paginas/pf/pessoa_juridica/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/plb/pessoa_juridica/cadastro?faces-redirect=true";
    }
    
    public String novoAdm() {
        limpaCampo();
        return "/paginas/adm/pessoa_juridica/cadastro?faces-redirect=true";
    }

    public String novaSenha() {
        pessoaJuridica.getUsuario().setSenha(null);
        return "/paginas/pf/pessoa_juridica/cadastro_senha?faces-redirect=true";
    }

    public boolean isEditando() {
        return this.pessoaJuridica.getId() != null;
    }

    public void limpaCampoPessoaJuridica() {
        pessoaJuridica = new Loja();
    }

    private void limpaCampo() {
        pessoaJuridica = new Loja();
    }

    public void limpaCampoNovo() {
        pessoaJuridica = new Loja();
    }

    public Loja getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(Loja pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public LojaFacade getPessoaJuridicaFacade() {
        return pessoaJuridicaFacade;
    }

    public void setPessoaJuridicaFacade(LojaFacade pessoaJuridicaFacade) {
        this.pessoaJuridicaFacade = pessoaJuridicaFacade;
    }

    public List<Loja> getPessoaJuridicas() {
        pessoaJuridicas = pessoaJuridicaFacade.getAll();
        return pessoaJuridicas;
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
        return pessoaJuridicaFacade.count();
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

    public double getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

}
