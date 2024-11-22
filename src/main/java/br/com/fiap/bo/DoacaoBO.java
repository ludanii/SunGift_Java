package br.com.fiap.bo;

import br.com.fiap.dto.Doacoes;
import java.sql.SQLException;

public class DoacaoBO {

    public int calcularDesconto(Doacoes doacoes, int descontoAtual) {

        int novoDesconto = 0;

        if (doacoes.getNR_QUANTIDADE() >= 1 && doacoes.getNR_QUANTIDADE() <= 10) {
            if (descontoAtual < 16) {
                novoDesconto = 5;
            }
            if (descontoAtual > 16) {
                novoDesconto = 20 - descontoAtual;
            }
        } else if (doacoes.getNR_QUANTIDADE() >= 11 && doacoes.getNR_QUANTIDADE() <= 20) {

            if (descontoAtual < 11) {
                novoDesconto = 10;
            }
            if (descontoAtual > 11) {
                novoDesconto =  20 - descontoAtual;
            }

        } else if (doacoes.getNR_QUANTIDADE() >= 21 && doacoes.getNR_QUANTIDADE() <= 30) {
            if (descontoAtual < 6) {
                novoDesconto =  15;
            }
            if (descontoAtual > 6) {
                novoDesconto =  20 - descontoAtual;
            }
        } else if (doacoes.getNR_QUANTIDADE() >= 31) {
            novoDesconto = 20 - descontoAtual;
        }
        return novoDesconto;
    }

}