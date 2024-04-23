package com.softdesign.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.softdesign.client.ValidadorDeCpfClient;
import com.softdesign.client.ValidationDTO;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.UnableToVoteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ValidadorDeCPFServiceTest {

  @Mock
  private ValidadorDeCpfClient validadorDeCpfClient;
  @InjectMocks
  private ValidadorDeCPFService validadorDeCPFService;

  private static final String CPF_TEST = "00523156065";

  @Test
  void deveValidarCpf() {
    ValidationDTO ableToVote = ValidationDTO.builder()
        .status("ABLE_TO_VOTE")
        .build();
    ResponseEntity<ValidationDTO> validationDTOResponseEntity = new ResponseEntity<ValidationDTO>(
        ableToVote,
        OK
    );
    when(validadorDeCpfClient.validate(CPF_TEST)).thenReturn(validationDTOResponseEntity);
    validadorDeCPFService.valida(CPF_TEST);
    verify(validadorDeCpfClient, times(1)).validate(CPF_TEST);
  }

  @Test
  void deveNaoValidaCPF() {
    ValidationDTO ableToVote = ValidationDTO.builder()
        .status("UNABLE_TO_VOTE")
        .build();
    ResponseEntity<ValidationDTO> validationDTOResponseEntity = new ResponseEntity<ValidationDTO>(
        ableToVote,
        NOT_FOUND
    );
    when(validadorDeCpfClient.validate(CPF_TEST)).thenReturn(validationDTOResponseEntity);
    assertThrows(UnableToVoteException.class, () -> validadorDeCPFService.valida(CPF_TEST));
  }

  @Test
  void deveLancarBusinessException() {
    when(validadorDeCpfClient.validate(CPF_TEST)).thenThrow(RuntimeException.class);
    assertThrows(BusinessException.class, () -> validadorDeCPFService.valida(CPF_TEST));
  }

}