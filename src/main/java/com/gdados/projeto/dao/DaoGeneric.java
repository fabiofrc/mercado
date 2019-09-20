///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.gdados.projeto.dao;
//
//import com.gdados.projeto.util.jpa.EntityManagerProducer;
//import java.io.Serializable;
//import java.util.List;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import com.gdados.projeto.util.jpa.Transational;
//
///**
// *
// * @author PMBV-164029
// * @param <T>
// */
//public class DaoGeneric<T> implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Inject
//    private EntityManager entityManager;
//
//    private final Class<T> persistentClass;
//
//    public DaoGeneric(Class<T> classe) {
//        persistentClass = classe;
//        entityManager = new EntityManagerProducer().createEntityManager();
//    }
//
//    public Class<T> getPersintentClass() {
//        return this.persistentClass;
//    }
//
//    public void setEntityManager(EntityManager em) {
//        this.entityManager = em;
//    }
//
//    protected EntityManager getEntityManager() {
//        if (this.entityManager == null) {
//            throw new IllegalStateException("Erro onde:");
//        }
//        return entityManager;
//    }
//
//    @Transational
//    public T save(T entity) {
//        try {
//            getEntityManager().getTransaction().begin();
//            getEntityManager().persist(entity);
//            getEntityManager().flush();
//            getEntityManager().getTransaction().commit();
//            getEntityManager().close();
//        } catch (Exception ex) {
//            getEntityManager().getTransaction().rollback();
//        }
//        return entity;
//    }
//
//    @Transational
//    public T update(T entity) {
//        try {
//            getEntityManager().getTransaction().begin();
//            getEntityManager().merge(entity);
//            getEntityManager().flush();
//            getEntityManager().getTransaction().commit();
//            getEntityManager().close();
//        } catch (Exception ex) {
//            getEntityManager().getTransaction().rollback();
//        }
//        return entity;
//    }
//
//    @Transational
//    public void delete(T entity) {
//        try {
//            getEntityManager().getTransaction().begin();
//            getEntityManager().remove(entity);
//            getEntityManager().flush();
//            getEntityManager().getTransaction().commit();
//            getEntityManager().close();
//        } catch (Exception ex) {
//            getEntityManager().getTransaction().rollback();
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAll() {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllDisponivel() {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE (obj.status) = ('" + true + "') AND (obj.destaque) = ('" + false + "') ORDER BY obj.id desc";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllDestaque() {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE (obj.status) = ('" + true + "') AND (obj.destaque) = ('" + true + "') ORDER BY obj.id desc";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllByStatus(Integer field, Integer codigo) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE (" + field + ") =  ('" + codigo + "')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public T getAllByCodigo(Long codigo) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE obj.id = ('" + codigo + "')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return (T) query.getSingleResult();
//    }
//
//    @SuppressWarnings("unchecked")
//    public T getAllByEmail(String email) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE obj.email = ('" + email + "')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return (T) query.getSingleResult();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllByCodigoOrdem(String field, Integer codigo) {
//        String querySelect = "SELECT obj AS data FROM " + persistentClass.getSimpleName() + " obj WHERE (" + field + ") = ('" + codigo + "')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllByCodigox(String field, Integer codigo) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE (" + field + ") = ('" + codigo + "')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllByName(String field, String name) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj WHERE upper(" + field + ") like upper('%" + name + "%')";
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> getAllOrder(String field) {
//        String querySelect = "SELECT obj FROM " + persistentClass.getSimpleName() + " obj ORDER BY " + field;
//        Query query = getEntityManager().createQuery(querySelect);
//        return query.getResultList();
//    }
//
//    public int count() {
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<T> rt = cq.from(persistentClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }
//}
