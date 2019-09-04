package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Promocao;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PromocaoFacade extends DaoGeneric<Promocao> implements Serializable {

    public PromocaoFacade() {
        super(Promocao.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public List<Promocao> listaPromocaoAtivaByData() {
        try {
            Query q = em.createQuery("SELECT p FROM Promocao p WHERE p.dataEncerramento >= :dataAtual ORDER BY p.dataEncerramento DESC");
            q.setParameter("dataAtual", LocalDate.now());
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }
}
