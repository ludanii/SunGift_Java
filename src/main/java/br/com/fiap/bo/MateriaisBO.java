package br.com.fiap.bo;

import br.com.fiap.dto.Materiais;
import br.com.fiap.exceptions.ValidacaoException;

public class MateriaisBO {
    public  boolean validarMateriais(Materiais materiais){
        if (materiais.getTP_MATERIAIS() == null || materiais.getTP_MATERIAIS().isEmpty()){
            throw new ValidacaoException("O tipo de material é obrigatório.");
        }
        return true;
    }
}
