package br.com.fiap.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class Workshops {
    private int ID_WORKSHOPS;
    private LocalDate DT_WORKSHOP;
    private String TP_WORKSHOPS, ST_WORKSHOP, DS_WORKSHOP, NM_WORKSHOP, NM_LOGRADOURO;

    public void definirStatus(){
        if(DT_WORKSHOP.isAfter(LocalDate.now())){
            ST_WORKSHOP = "Em breve";
        }
        if (DT_WORKSHOP.isBefore(LocalDate.now())){
            ST_WORKSHOP = "Realizado";
        }
    }
}
