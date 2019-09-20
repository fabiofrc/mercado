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
 * @author PMBV-164029
 */
public class ListaUsuario {

    public static boolean verificarUsuarioExistente() {
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuarioExistente = usuarioFacade.verificaUsuario("admin@admin.com");
        return usuarioExistente != null;
    }

    public static void main(String[] args) {

//         List<Usuario> lista = usuarioFacade.getAll();
//        lista.forEach((usuario) -> {
//            System.out.println("Lista:" + usuario.getEmail());
//        });
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario resultado = usuarioFacade.verificaUsuario("fabio@gmail.com");
        if (resultado != null) {
            System.out.println("resultado: " + resultado.getId());
        }
    }
}
