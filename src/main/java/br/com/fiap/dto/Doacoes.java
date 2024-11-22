package br.com.fiap.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class Doacoes {
    private int ID_DOACOES, ID_CONDOMINIO, ID_MATERIAIS, NR_QUANTIDADE, NR_DESCONTOS;
    private LocalDate DT_DOACAO;
}
