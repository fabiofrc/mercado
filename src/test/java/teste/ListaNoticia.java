/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.ProdutoFacade;
import com.gdados.projeto.model.Produto;
import java.util.List;

/**
 *
 * @author PMBV-164029
 */
public class ListaNoticia {
    public static void main(String[] args) {
        ProdutoFacade nf = new ProdutoFacade();
        List<Produto> lista = nf.getAll();
        for (Produto noticia : lista) {
            System.out.println(noticia.getId());
        }
    }
}
