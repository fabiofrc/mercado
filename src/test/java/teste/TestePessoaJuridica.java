/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.PessoaJuridicaFacade;
import com.gdados.projeto.model.PessoaJuridica;
import com.gdados.projeto.model.Usuario;

/**
 *
 * @author PMBV-164029
 */
public class TestePessoaJuridica {

    public static void main(String[] args) {
        PessoaJuridicaFacade pj = new PessoaJuridicaFacade();
//        List<PessoaFisica> lista = pf.getAll();
//        for (PessoaFisica participante : lista) {
//            System.out.println(participante.getId());
//        }

        Usuario u = new Usuario();
        u.setEmail("teste1@gmail.com");
        u.setSenha("frctads");

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("999999999999/080");
        pessoaJuridica.setNome("teste1 teste1");
        
        pessoaJuridica.setUsuario(u);

        pj.save(pessoaJuridica);
        System.out.println("cadastrorealizado com sucesso!");
    }
}
