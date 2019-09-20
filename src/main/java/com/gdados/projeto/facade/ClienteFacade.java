/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.model.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ClienteFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Cliente save(Cliente entity) {
        em.persist(entity);
        return entity;
    }

    public Cliente update(Cliente entity) {
        em.merge(entity);
        return entity;
    }

    public void delete(Cliente entity) {
        em.remove(entity);
    }

    public List<Cliente> getAll() {
        Query q = em.createQuery("SELECT p FROM Cliente p");
        return q.getResultList();
    }

    public Cliente getById(Long id) {
        Query q = em.createQuery("SELECT c FROM Cliente c WHERE c.id = :id");
        q.setParameter("id", id);
        return (Cliente) q.getSingleResult();
    }

    public Cliente buscaParticipanteByIdUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT p FROM Cliente p JOIN p.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return (Cliente) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Cliente> porNome(String nome) {
        try {
            Query q = em.createQuery("SELECT p FROM PessoaFisica p WHERE p.nome = :nome");
            q.setParameter("nome", nome.toUpperCase() + "%");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Cliente p");
        int contador = (int) q.getSingleResult();
        return contador;
    }
}
