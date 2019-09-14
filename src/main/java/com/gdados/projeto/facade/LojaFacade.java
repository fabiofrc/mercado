
package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Loja;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LojaFacade extends DaoGeneric<Loja> {

    public LojaFacade() {
        super(Loja.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public Loja buscaPessoaJuridicaByIdUsuario(Long id) {
        try {
            Query q = em.createQuery("SELECT p FROM PessoaJuridica p JOIN p.usuario u WHERE u.id = :id");
            q.setParameter("id", id);
            return (Loja) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
    
    
}
