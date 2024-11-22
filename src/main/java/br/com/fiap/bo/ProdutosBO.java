package br.com.fiap.bo;

import br.com.fiap.dto.Produtos;
import br.com.fiap.exceptions.ValidacaoException;

public class ProdutosBO {
    public boolean validarProdutos(Produtos produtos) {
        if (produtos.getDS_PRODUTO() == null || produtos.getDS_PRODUTO().isEmpty()) {
            throw new ValidacaoException("A descricao dos produtos é obrigatória.");
        }
        if (produtos.getNM_PRODUTO() == null || produtos.getNM_PRODUTO().isEmpty()) {
            throw new ValidacaoException("O nome dos produtos é obrigatório.");
        }
        return true;
    }
}
