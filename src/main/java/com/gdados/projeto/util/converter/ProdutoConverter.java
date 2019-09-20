package com.gdados.projeto.util.converter;

import com.gdados.projeto.facade.ProdutoFacade;
import com.gdados.projeto.model.Produto;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

    ProdutoFacade produtoFacade = new ProdutoFacade();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Produto retorno = null;
        if (value != null && !value.equals("")) {
            retorno = produtoFacade.getById(Long.valueOf(value));
            if (retorno == null) {
                String descricaoErro = "Produto não existe.";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, descricaoErro, descricaoErro);
                throw new ConverterException(message);
            }
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Produto) value).getId();
            return codigo == null ? "" : codigo.toString();
        }
        return null;
    }

}
