package br.com.fiap.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Condominios {
    private int ID_CONDOMINIO, DS_COMPLEMENTO;
    private String NM_CONDOMINIO, NR_CEP, NM_BAIRRO, NM_ESTADO, NM_CIDADE,
            DS_SENHA, NM_LOGRADOURO, NM_EMAIL;

    public void cepFormatado() {
        if (NR_CEP != null) {
            if (NR_CEP.contains("-")) {
                if (NR_CEP.length() == 9 && NR_CEP.matches("\\d{5}-\\d{3}")) {
                } else {
                    throw new IllegalArgumentException("CEP inválido. O formato deve ser XXXXX-XXX.");
                }
            } else if (NR_CEP.length() == 8 && NR_CEP.matches("\\d{8}")) {
                NR_CEP = NR_CEP.substring(0, 5) + "-" + NR_CEP.substring(5);
            } else {
                throw new IllegalArgumentException("CEP inválido. O CEP deve conter exatamente 8 dígitos ou estar no formato XXXXX-XXX.");
            }
        } else {
            throw new IllegalArgumentException("CEP inválido. O valor não pode ser nulo.");
        }
    }


    public void logradouroFormatado(){
        try{
            NM_LOGRADOURO = String.format("%s, %s, %s, %s, (%s)", NR_CEP, NM_ESTADO, NM_CIDADE, NM_BAIRRO, DS_COMPLEMENTO);
        } catch (Exception e) {
            throw new IllegalArgumentException("Nao foi possivel preencher o logradouro.", e);
        }
    }

    public void formatandoLetras() {
        try {
            NM_EMAIL = NM_EMAIL.toLowerCase();
            NM_CIDADE = capitalizeWords(NM_CIDADE);
            NM_BAIRRO = capitalizeWords(NM_BAIRRO);
        } catch (Exception e) {
            throw new IllegalArgumentException("Não foi possível formatar os atributos.", e);
        }
    }

    private String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder capitalizedText = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedText.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedText.toString().trim();
    }
}
