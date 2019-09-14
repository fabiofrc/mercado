/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.LojaFacade;
import com.gdados.projeto.model.Loja;
import com.gdados.projeto.model.Usuario;

/**
 *
 * @author PMBV-164029
 */
public class TestePessoaJuridica {

    public static void main(String[] args) {
        LojaFacade pj = new LojaFacade();
//        List<PessoaFisica> lista = pf.getAll();
//        for (PessoaFisica participante : lista) {
//            System.out.println(participante.getId());
//        }

        Usuario u = new Usuario();
        u.setEmail("teste1@gmail.com");
        u.setSenha("frctads");

        Loja pessoaJuridica = new Loja();
        pessoaJuridica.setCnpj("999999999999/080");
        pessoaJuridica.setNome("teste1 teste1");
        
        pessoaJuridica.setUsuario(u);

        pj.save(pessoaJuridica);
        System.out.println("cadastrorealizado com sucesso!");
    }
}
