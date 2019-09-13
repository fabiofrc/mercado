package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.facade.facadeImplemet.SubCategoriaImplentQuery;
import com.gdados.projeto.model.SubCategoria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SubCategoriaFacade extends DaoGeneric<SubCategoria> implements Serializable, SubCategoriaImplentQuery {

    public SubCategoriaFacade() {
        super(SubCategoria.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

   

    @Override
    public List<SubCategoria> listaSubCategoriaByCategoria(Long id) {
        try {
            Query q = em.createQuery("SELECT s FROM SubCategoria s JOIN s.categoria c WHERE c.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public List<SubCategoria> listarSubCategoriaById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long contarSubCategoria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long contarSubCategoriaById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubCategoria buscarSubCategoriaByCategoriaById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubCategoria buscarSubCategoriaById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
