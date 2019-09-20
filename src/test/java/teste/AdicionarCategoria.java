/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.CategoriaFacade;
import com.gdados.projeto.facade.TesteFacade;
import com.gdados.projeto.model.Categoria;
import javax.inject.Inject;

/**
 *
 * @author fabio
 */
public class AdicionarCategoria {

    @Inject
    private TesteFacade tf;

    public void cadastrar(Categoria c) {
        tf.save(c);
    }

    public static void main(String[] args) {
        CategoriaFacade cf = new CategoriaFacade();

        Categoria c = new Categoria();
        c.setNome("teste 4");
        Categoria retorno = cf.save(c);
//        c.setDataRegistro(LocalDate.MIN);
//        AdicionarCategoria ac = new AdicionarCategoria();
//        ac.cadastrar(c);

        System.out.println("Cadastro realizado com sucesso!" + retorno.getId());
    }
}
