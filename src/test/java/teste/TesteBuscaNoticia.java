/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.ProdutoFacade;
import com.gdados.projeto.model.Produto;
import com.gdados.projeto.util.filter.ProdutoFilter;
import java.util.List;

/**
 *
 * @author PMBV-164029
 */
public class TesteBuscaNoticia {
    
    @SuppressWarnings("null")
    public static void main(String[] args) {
        ProdutoFacade nf = new ProdutoFacade();
        ProdutoFilter noticiaFilter = new ProdutoFilter();
        noticiaFilter.setTitulo("");
        noticiaFilter.setCategoria("");
        noticiaFilter.setPrecoMinimo(10);
        noticiaFilter.setPrecoMaximo(100);
        
        if (noticiaFilter != null) {
            List<Produto> noticias = nf.buscaNoticiaByFiltro1(noticiaFilter);
            for (Produto noticia : noticias) {
                System.out.println("Noticia: " + noticia.getTitulo() + " Categoria: " + noticia.getSubCategoria().getNome());
//                System.out.println("Categoria: " + noticia.getCategoria().getNome());
            }
        }
        
    }
}
