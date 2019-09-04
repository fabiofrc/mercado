package com.gdados.projeto.facade;

import com.gdados.projeto.dao.DaoGeneric;
import com.gdados.projeto.dao.JpaUtil;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.util.filter.ProdutoFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProdutoFacade extends DaoGeneric<Produto> implements Serializable {

    public ProdutoFacade() {
        super(Produto.class);
    }

    EntityManager em = new JpaUtil().createEntityManager();

    public long contaTotal() {
        Query q = em.createQuery("select count(c) from Produto c");
        long contador = (long) q.getSingleResult();
        return contador;
    }

    public long contaProdutoByPessoaJuridica(Long id) {
        Query q = em.createQuery("select count(p) from Produto p JOIN p.pessoaJuridica s WHERE s.id = :id");
        q.setParameter("id", id);
        long contador = (long) q.getSingleResult();
        return contador;
    }

    public long contaProdutoByPromocao(Long id) {
        Query q = em.createQuery("select count(p) from Produto p JOIN p.promocao s WHERE s.id = :id");
        q.setParameter("id", id);
        long contador = (long) q.getSingleResult();
        return contador;
    }

    public List<Produto> listaNoticiaBySubCategoria(Long id) {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n JOIN N.subCategoria s WHERE s.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Produto> listaNoticiaBySubCategoria(String nome) {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n JOIN N.subCategoria s WHERE s.nome = :nome");
            q.setParameter("nome", nome);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Produto> listaProdutoByCategoria(Long id) {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n JOIN N.subCategoria.categoria s WHERE s.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Produto> listaProdutoByPromocao(Long id) {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n JOIN N.promocao s WHERE s.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Produto> listaNoticiaByPessoaJuridica(Long id) {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n JOIN N.pessoaJuridica s WHERE s.id = :id");
            q.setParameter("id", id);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<Produto> listaNoticiaByDestaque() {
        try {
            Query q = em.createQuery("SELECT n FROM Produto n WHERE n.destaque = :destaque ORDER BY n.dataatuAlizacao DESC");
            q.setParameter("destaque", Boolean.TRUE);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("erro: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<String> nomeQueContem(String titulo) {
        TypedQuery<String> query = em.createQuery("SELECT p.nome FROM Produto p WHERE upper(p.nome) LIKE upper(:nome)", String.class);
        query.setParameter("titulo", "%" + titulo + "%");
        return query.getResultList();
    }

    public List<Produto> buscaNoticiaByFiltro1(ProdutoFilter filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> n = query.from(Produto.class);

        Path<String> tituloPath = n.<String>get("nome");
        Path<String> categoriaPath = n.join("subCategoria").<String>get("nome");
        Path<Double> precoPath = n.<Double>get("preco");

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTitulo() != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(tituloPath), "%" + filter.getTitulo().toLowerCase() + "%");
            predicates.add(paramentro);
        }

        if (filter.getCategoria() != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(categoriaPath), "%" + filter.getCategoria().toLowerCase() + "%");
            predicates.add(paramentro);
        }

        if (filter.getPrecoMinimo() >= 1 && filter.getPrecoMaximo() <= 1000) {
            Predicate paramentro = criteriaBuilder.between(precoPath, filter.getPrecoMinimo(), filter.getPrecoMaximo());
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("dataatuAlizacao")));
        TypedQuery<Produto> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    public List<Produto> buscaProdutosByPreco(ProdutoFilter filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> criteria = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> n = criteria.from(Produto.class);

        Path<Double> precoPath = n.<Double>get("preco");
        Path<String> tituloPath = n.<String>get("nome");
        Path<String> categoriaPath = n.join("subCategoria").<String>get("nome");

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTitulo() != null) {
            Predicate paramentro = criteriaBuilder.like(tituloPath, "%" + filter.getTitulo() + "%");
            predicates.add(paramentro);
        }

        if (filter.getCategoria() != null) {
            Predicate paramentro = criteriaBuilder.like(categoriaPath, "%" + filter.getCategoria() + "%");
            predicates.add(paramentro);
        }

        if (filter.getTitulo() != null) {
            Predicate paramentro = criteriaBuilder.between(precoPath, filter.getPrecoMinimo(), filter.getPrecoMaximo());
            predicates.add(paramentro);
        }

        criteria.where((Predicate[]) predicates.toArray(new Predicate[0]));
        criteria.orderBy(criteriaBuilder.desc(n.get("dataatuAlizacao")));
        TypedQuery<Produto> typedQuery = em.createQuery(criteria);

        return typedQuery.getResultList();
    }
}
