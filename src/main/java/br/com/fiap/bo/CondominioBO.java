package br.com.fiap.bo;
import br.com.fiap.dto.Condominios;
import br.com.fiap.exceptions.ValidacaoException;

public class CondominioBO {

    public boolean validarRegistro(Condominios condominio) {
        if (condominio.getNM_CONDOMINIO() == null || condominio.getNM_CONDOMINIO().isEmpty()) {
            throw new ValidacaoException("O nome do condomínio é obrigatório.");
        } if (condominio.getDS_SENHA() == null || condominio.getDS_SENHA().isEmpty()) {
            throw new ValidacaoException("A senha é obrigatório.");
        } if (condominio.getDS_SENHA().length() < 4 || condominio.getDS_SENHA().length() > 20) {
            throw new ValidacaoException("A senha deve ter mais que 4 caracteres e manos de 20 caracteres.");
        } if ( condominio.getNM_LOGRADOURO() == null ) {
            throw new ValidacaoException("O lagradouro deve ser preenchido.");
        }
        if (condominio.getNM_EMAIL() == null || condominio.getNM_EMAIL().isEmpty() ||
                !condominio.getNM_EMAIL().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidacaoException("Email inválido.");
        }
        return true;
    }
}
