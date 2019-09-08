/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.ComentarioFacade;
import com.gdados.projeto.facade.ProdutoFacade;

/**
 *
 * @author PMBV-164029
 */
public class ContadorComentariosByNoticia {

    public static String format(double x) {
        return String.format("%.1f", x);
    }

    public static void main(String[] args) {
        ComentarioFacade cf = new ComentarioFacade();

        long cont_comentarioByProduto = cf.contaComentarioByProduto(4L);
        System.out.println("Quantidade de comentário: " + cont_comentarioByProduto);

        long cont_comentarioTotal = cf.contaTotal();
        System.out.println("Total de comentarios: " + cont_comentarioTotal);

        long soma_comentarioByProduto = cf.somaComentarioByProduto(4L);
        System.out.println("Soma de comentarios: " + soma_comentarioByProduto);

        double media_comentarioByProduto = cf.mediaComentarioByProduto(4L);
        System.out.println("Média de comentarios: " + format(media_comentarioByProduto));

//        ProdutoFacade nf = new ProdutoFacade();
//        long cont_noticia = nf.contaTotal();
//        System.out.println("Total de noticias: " + cont_noticia);
//
//        double porcetagemComentrioByNotica = (cont_comentarioByProduto * 100) / cont_comentarioTotal;
//        System.out.println("porcentagem: " + porcetagemComentrioByNotica);
//
////        double total = (cont_comentarioByNoticia * 100) / cont_comentarioTotal;
//        System.out.println("diferença: " + (100.0 - porcetagemComentrioByNotica));
    }
}
