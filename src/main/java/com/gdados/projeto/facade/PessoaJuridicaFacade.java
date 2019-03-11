/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.PessoaJuridica;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PessoaJuridicaFacade extends DaoGeneric<PessoaJuridica> {

    public PessoaJuridicaFacade() {
        super(PessoaJuridica.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public PessoaJuridica buscaPessoaJuridicaByIdUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT p FROM PessoaJuridica p JOIN p.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return (PessoaJuridica) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
    
    
}
