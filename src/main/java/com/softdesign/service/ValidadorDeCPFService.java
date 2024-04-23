package com.softdesign.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.softdesign.client.ValidadorDeCpfClient;
import com.softdesign.client.ValidationDTO;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.UnableToVoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDeCPFService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ValidadorDeCpfClient validadorDeCpfClient;

  public void valida(String cpf) {
    ResponseEntity<ValidationDTO> validate = validaCpf(cpf);
    if (validate.getStatusCode().equals(NOT_FOUND)) {
      throw new UnableToVoteException();
    }
  }

  private ResponseEntity<ValidationDTO> validaCpf(String cpf) {
    try {
      return this.validadorDeCpfClient.validate(cpf);
    } catch (RuntimeException e) {
      logger.error("Erro ao validar CPF: ", e);
      throw new BusinessException();
    }
  }

}
