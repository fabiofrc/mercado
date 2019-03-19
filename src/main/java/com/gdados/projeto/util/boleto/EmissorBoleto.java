package com.gdados.projeto.util.boleto;

import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.PessoaJuridica;
import java.io.File;
import java.io.Serializable;

public interface EmissorBoleto extends Serializable {

    public byte[] gerarBoleto(PessoaJuridica beneficiarioSistema, Pedido cobrancaSistema);

    public File gerarBoletoEmArquivo(String arquivo, PessoaJuridica beneficiarioSistema, Pedido cobrancaSistema);

}
