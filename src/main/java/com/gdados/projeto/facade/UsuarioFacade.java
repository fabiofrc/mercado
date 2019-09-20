/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.model.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UsuarioFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Usuario save(Usuario entity) {
        em.persist(entity);
        return entity;
    }

    public Usuario update(Usuario entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Usuario entity) {
        em.remove(entity);
    }

    public List<Usuario> getAll() {
        Query q = em.createQuery("SELECT p FROM Usuario p");
        return q.getResultList();
    }

    public Usuario getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Usuario c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Usuario) q.getSingleResult();
    }

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

    public int count() {
        Query q = em.createQuery("select count(p) from Usuario p");
        int contador = (int) q.getSingleResult();
        return contador;
    }

}
