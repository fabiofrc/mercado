package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Promocao;
import java.io.Serializable;
import javax.persistence.EntityManager;

public class PromocaoFacade extends DaoGeneric<Promocao> implements Serializable {

    public PromocaoFacade() {
        super(Promocao.class);
    }
   
    EntityManager entityManager = new JpaUtil().createEntityManager();

}
