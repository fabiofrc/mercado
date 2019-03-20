package com.gdados.projeto.util.boleto;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import com.gdados.projeto.model.Pedido;
import com.gdados.projeto.model.PessoaFisica;
import com.gdados.projeto.model.PessoaJuridica;

public class EmissorBoletoStella implements EmissorBoleto {

    private static final long serialVersionUID = 1L;

    @Inject
    public EmissorBoletoStella() {

    }

    @Override
    public byte[] gerarBoleto(PessoaJuridica beneficiarioSistema, Pedido carrinho, PessoaFisica cliente) {

        Calendar dataAtual = Calendar.getInstance();
        Datas datas = Datas.novasDatas()
                .comDocumento(dataAtual.get(Calendar.DAY_OF_MONTH), dataAtual.get(Calendar.MONTH) + 1, dataAtual.get(Calendar.YEAR))
                .comProcessamento(dataAtual.get(Calendar.DAY_OF_MONTH), dataAtual.get(Calendar.MONTH) + 1, dataAtual.get(Calendar.YEAR))
                .comVencimento(dataAtual.get(Calendar.DAY_OF_MONTH) + 3, dataAtual.get(Calendar.MONTH) + 1, dataAtual.get(Calendar.YEAR));

        Endereco enderecoBeneficiario = Endereco.novoEndereco().comLogradouro("Rua Vergueiro, 3185 Cj 57")
                .comBairro("Vila Mariana").comCep("04101-300").comCidade("Sao Paulo").comUf("SP");

        // Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("AOVS SISTEMAS DE INFORMATICA LTDA CAELUM").comDocumento("05.555.382/0001-33")
                .comAgencia("1904").comDigitoAgencia("6").comCodigoBeneficiario("12283")
                .comDigitoCodigoBeneficiario("1").comNumeroConvenio("1207113").comCarteira("18")
                .comEndereco(enderecoBeneficiario).comNossoNumero("9000206");

        Endereco enderecoPagador = Endereco.novoEndereco().comLogradouro("Av. Via das Flores, 1106")
                .comBairro("Pricuma").comCep("69309-393").comCidade("Boa Vista").comUf("RR");

        // Quem paga o boleto
        Pagador pagador = Pagador.novoPagador().comNome(cliente.getNome())
                .comDocumento(cliente.getCpf()).comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();

        Boleto boleto = Boleto.novoBoleto().comBanco(banco).comDatas(datas).comDescricoes()
                .comBeneficiario(beneficiario).comPagador(pagador).comValorBoleto(carrinho.getValorTotal())
                .comNumeroDoDocumento("1234")
                .comInstrucoes("Sr Caixa, nao receber após vencimento", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
                .comLocaisDePagamento("Em qualquer banco até o vencimento", "Pagar preferencialmente no Banco do Brasil");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        // Para gerar um array de bytes a partir de um PDF
        byte[] bPDF = gerador.geraPDF();

        // Para gerar um array de bytes a partir de um PNG
        // byte[] bPNG = gerador.geraPNG();
        return bPDF;
    }

    @Override
    public File gerarBoletoEmArquivo(String arquivo, PessoaJuridica beneficiarioSistema, Pedido cobrancaSistema) {
        return null;
    }

}
