/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.PessoaFisicaFacade;
import com.gdados.projeto.model.PessoaFisica;
import com.gdados.projeto.model.Usuario;

/**
 *
 * @author PMBV-164029
 */
public class TesteParticipante {
    
    public static void main(String[] args) {
        PessoaFisicaFacade pf = new PessoaFisicaFacade();
//        List<PessoaFisica> lista = pf.getAll();
//        for (PessoaFisica participante : lista) {
//            System.out.println(participante.getId());
//        }

        Usuario u = new Usuario();
        u.setEmail("teste@gmail.com");
        u.setSenha("frctads");
        
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf("999999999-80");
        pessoaFisica.setNome("teste teste");
        pessoaFisica.setUsuario(u);
        
        pf.save(pessoaFisica);
        System.out.println("cadastrorealizado com sucesso!");
    }
}
