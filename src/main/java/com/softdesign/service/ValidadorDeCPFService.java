package com.softdesign.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.softdesign.client.ValidadorDeCpfClient;
import com.softdesign.client.ValidationDTO;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.UnableToVoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidadorDeCPFService {

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
      throw new BusinessException();
    }
  }

}
