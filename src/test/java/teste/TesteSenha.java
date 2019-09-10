/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.gdados.projeto.security.MyPasswordEncoder;

/**
 *
 * @author fabio
 */
public class TesteSenha {

    public static void main(String[] args) {

        String senha = MyPasswordEncoder.encodePasswordWithBCrypt("frctads");
        String confirmaSenha = MyPasswordEncoder.encodePasswordWithBCrypt("frctads");

        System.out.println("senha: " + senha);
        System.out.println("confirmSenha: " + confirmaSenha);
    }
}
//$2a$10$E/e4591GgVTGokyJmFGvVOFxL.Z5edPnPDvTdL5MMEx.L4Lcd/TiG
//$2a$10$MmNSGZ/YZNeGX6IZSSQsa.2m7TTjbNJ.0sh6405Olk0tYpXK2lGoS
//$2a$10$NTU7vrZ0tm2IWSziKSwnm.wMBfOsmaa0OpoSp4dBmNTPFruLlWH5O