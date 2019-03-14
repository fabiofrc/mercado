/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.facade.PessoaFisicaFacade;
import com.gdados.projeto.model.PessoaFisica;
import java.util.List;

/**
 *
 * @author PMBV-164029
 */
public class TesteParticipante {

    public static void main(String[] args) {
        PessoaFisicaFacade pf = new PessoaFisicaFacade();
        List<PessoaFisica> lista = pf.getAll();
        for (PessoaFisica participante : lista) {
            System.out.println(participante.getId());
        }
    }
}
