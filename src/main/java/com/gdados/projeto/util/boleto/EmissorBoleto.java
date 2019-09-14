package com.gdados.projeto.util.boleto;

import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.Cliente;
import com.gdados.projeto.model.Loja;
import java.io.File;
import java.io.Serializable;

public interface EmissorBoleto extends Serializable {

    public byte[] gerarBoleto(Loja beneficiarioSistema, Pedido cobrancaSistema, Cliente cliente);

    public File gerarBoletoEmArquivo(String arquivo, Loja beneficiarioSistema, Pedido cobrancaSistema);

}
