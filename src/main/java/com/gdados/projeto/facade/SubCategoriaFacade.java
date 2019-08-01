package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.SubCategoria;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SubCategoriaFacade extends DaoGeneric<SubCategoria> implements Serializable {

    public SubCategoriaFacade() {
        super(SubCategoria.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public long contaProdutoBySubCategoria(Long id) {
        Query q = em.createQuery("select count(p) from Produto p JOIN p.subCategoria s WHERE s.id = :id");
        q.setParameter("id", id);
        long contador = (long) q.getSingleResult();
        return contador;
    }

}
