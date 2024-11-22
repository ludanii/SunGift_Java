package br.com.fiap.exceptions;

// Exceção para erros de validação
public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
        super(message);
    }
}
