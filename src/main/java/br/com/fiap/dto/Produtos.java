package br.com.fiap.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Produtos {
    private int ID_PRODUTOS, NR_QUANTIDADE;
    private double NR_PRECO;
    private String DS_PRODUTO, NM_PRODUTO;
}
