/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.ClienteFacade;
import com.gdados.projeto.model.Cliente;
import com.gdados.projeto.model.Usuario;

/**
 *
 * @author PMBV-164029
 */
public class TesteParticipante {
    
    public static void main(String[] args) {
        ClienteFacade pf = new ClienteFacade();
//        List<PessoaFisica> lista = pf.getAll();
//        for (Cliente participante : lista) {
//            System.out.println(participante.getId());
//        }

        Usuario u = new Usuario();
        u.setEmail("teste@gmail.com");
        u.setSenha("frctads");
        
        Cliente pessoaFisica = new Cliente();
        pessoaFisica.setCpf("999999999-80");
        pessoaFisica.setNome("teste teste");
        pessoaFisica.setUsuario(u);
        
        pf.save(pessoaFisica);
        System.out.println("cadastrorealizado com sucesso!");
    }
}
