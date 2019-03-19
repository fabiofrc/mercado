/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.PessoaFisica;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PessoaFisicaFacade extends DaoGeneric<PessoaFisica> {

    public PessoaFisicaFacade() {
        super(PessoaFisica.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public PessoaFisica buscaParticipanteByIdUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT p FROM PessoaFisica p JOIN p.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return (PessoaFisica) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<PessoaFisica> porNome(String nome) {
        try {
            Query q = em.createQuery("SELECT p FROM PessoaFisica p WHERE p.nome = :nome");
            q.setParameter("nome", nome.toUpperCase() + "%");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
}
