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
 * @author fabio
 */
public class TestePromocao {

    public static void main(String[] args) {
//        ProdutoFacade produtoFacade = new ProdutoFacade();
//        List<Produto> produtos = produtoFacade.getAll();
//        
//        for (Produto produto : produtos) {
//            System.out.println(produto.getPrecoTotal());
//        }

        double preco = 27.0;
        double precoTotal;
        double percentual = 0.1;

        if (percentual == 0.0) {
            precoTotal = preco;
        } else {
            precoTotal = (preco - (preco * percentual));
        }

        System.out.println("preço total: " + precoTotal);
    }
}
