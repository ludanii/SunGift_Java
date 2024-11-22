package br.com.fiap.bo;

import br.com.fiap.dto.Workshops;
import br.com.fiap.exceptions.ValidacaoException;

public class WorkshopsBO {

    public boolean validarWorkshops(Workshops workshops) {

        if(workshops.getDT_WORKSHOP() == null) {
            throw new ValidacaoException("A data do workshop é obrigatória.");
        } if(workshops.getNM_WORKSHOP() == null || workshops.getNM_WORKSHOP().isEmpty()) {
            throw new ValidacaoException("o nome do workshop é obrigatório.");
        } if(workshops.getDS_WORKSHOP() == null || workshops.getDS_WORKSHOP().isEmpty()) {
            throw new ValidacaoException("a descricao do workshop é obrigatória.");
        } if(workshops.getST_WORKSHOP() == null || workshops.getST_WORKSHOP().isEmpty()) {
            throw new ValidacaoException("o status do workshop é obrigatório.");
        } if(workshops.getTP_WORKSHOPS() == null || workshops.getTP_WORKSHOPS().isEmpty()) {
            throw new ValidacaoException("o tipo do workshop é obrigatório.");
        } if(workshops.getNM_LOGRADOURO() == null || workshops.getNM_LOGRADOURO().isEmpty()) {
            throw new ValidacaoException("o logradouro do workshop é obrigatório.");
        }
        return true;
    }
}
