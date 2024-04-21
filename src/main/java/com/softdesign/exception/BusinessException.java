package com.softdesign.exception;

public class BusinessException extends RuntimeException {
    public BusinessException() {
        super("Desculpe, ocorreu um erro ao processar sua solicitação. Tente novamente mais tarde ou entre em contato com o suporte se o problema persistir");
    }
}
