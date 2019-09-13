/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.facade.facadeImplemet.UsuarioImplentQuery;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.model.Usuario;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UsuarioFacade extends DaoGeneric<Usuario> {

    public UsuarioFacade() {
        super(Usuario.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public Usuario verificaUsuario(String email) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        q.setParameter("email", email.toLowerCase());
        try {
            return (Usuario) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Usuario buscaPorEmail(String email) {
        return verificaUsuario(email);
    }

    public boolean findUserByEmail(String email) {
        Usuario user = verificaUsuario(email);
        boolean opt = Optional.ofNullable(user).isPresent();

        return opt;
    }

}
