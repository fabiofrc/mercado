/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.UsuarioFacade;
import com.gdados.projeto.model.Usuario;
import java.util.Optional;

/**
 *
 * @author fabio
 */
public class TesteOptional {
    
    public static void main(String[] args) {
        Optional<String> linguagem = Optional.of("JAVA");
        String respostaPreenchida = "Sim";
        
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario respostaNula = usuarioFacade.buscaPorEmail("etetete");

//        System.out.println("Optional Não Vazio:" + linguagem);
//        System.out.println("Optional Não Vazio: Obtem o valor: " + linguagem.get());
//        System.out.println("Optional Vazio: " + Optional.empty());
        System.out.println("Chamada do método ofNullable() em Optional Não Vazio: " + Optional.ofNullable(respostaPreenchida));
//        System.out.println("Chamada do método ofNullable() em Optional Vazio: " + Optional.ofNullable(respostaNula));
//
//        // Ocorre uma java.lang.NullPointerException na linha abaixo
//        System.out.println("Chamada do método of() Optional Não Vazio: " + Optional.of(respostaNula));
    }
}
