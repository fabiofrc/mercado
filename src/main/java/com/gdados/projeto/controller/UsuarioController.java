/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.controller;

import com.gdados.projeto.facade.UsuarioFacade;
import com.gdados.projeto.model.Usuario;
import com.gdados.projeto.security.MyPasswordEncoder;
import com.gdados.projeto.util.msg.Msg;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class UsuarioController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    @Inject
    private UsuarioFacade usuarioFacade;
    private List<Usuario> usuarios;

    public UsuarioController() {
        if (usuarios == null) {
            limpaCampo();
        }
    }

    public String salvar() {
        try {
            if (usuario.getId() == null) {
                usuario.setSenha(MyPasswordEncoder.getPasswordEncoder(usuario.getSenha()));
                usuarioFacade.save(usuario);
                limpaCampo();
                return "lista?faces-redirect=true";
            } else {
                usuarioFacade.update(usuario);
                limpaCampo();
                return "lista?faces-redirect=true";
            }
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public String editar(Long id) {
        try {
            usuario = usuarioFacade.getAllByCodigo(id);
            return "cadastro?faces-redirect=true";
        } catch (Exception e) {
        }
        return null;
    }

    public void deletar(Usuario usuario) {
        try {
            usuarioFacade.delete(usuario);
            getUsuarios();
        } catch (Exception e) {
        }
    }

    public String verificarEmail() {
        try {
            usuario = usuarioFacade.verificaUsuario(usuario.getEmail());
            if (usuario.getId() == null) {
                Msg.addMsgWarn("Login inválido");
            }

            return "/paginas/pf/usuario/cadastro_senha?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
            Msg.addMsgWarn("Login inválido");
        }
        return null;
    }

    public String salvarNovaSenha() {
        try {
            if (usuario.getId() != null) {
                usuario.setSenha(MyPasswordEncoder.getPasswordEncoder(usuario.getSenha()));
                usuarioFacade.update(usuario);
                limpaCampo();
                Msg.addMsgInfo("Operação realizada com sucesso!");
                return "sucesso?faces-redirect=true";
            }
        } catch (Exception e) {
            Msg.addMsgError("Operação não realizada! " + e.getMessage());
        }
        return null;
    }

    public String lista() {
        return "/paginas/adm/usuario/lista?faces-redirect=true";
    }

    public String novo() {
        limpaCampo();
        return "/paginas/adm/usuario/cadastro?faces-redirect=true";
    }

    public String home() {
        return "/Home?faces-redirect=true";
    }

    public String login() {
        return "/login?faces-redirect=true";
    }

    private void limpaCampo() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public List<Usuario> getUsuarios() {
        usuarios = usuarioFacade.getAll();
        return usuarios;
    }

    public int getContador() {
        return usuarioFacade.count();
    }

}
