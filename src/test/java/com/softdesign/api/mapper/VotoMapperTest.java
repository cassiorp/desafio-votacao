package com.softdesign.api.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.VotoRequestDTO;
import com.softdesign.api.dto.VotoResponseDTO;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class VotoMapperTest {

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private VotoMapper votoMapper;

  private static final String ID_PAUTA_TESTE = "idPautaTeste";
  private static final String CPF_TESTE = "12345678901";

  @Test
  public void deveMapearParaEntidade() {
    VotoRequestDTO votoRequestDTO = VotoRequestDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .build();

    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .build();

    when(objectMapper.convertValue(votoRequestDTO, Voto.class)).thenReturn(voto);

    Voto result = votoMapper.toEntity(votoRequestDTO);

    assertEquals(voto, result);
  }

  @Test
  public void deveMappearParaDTO() {
    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .dataDeCriacao(LocalDateTime.now())
        .build();

    VotoResponseDTO votoResponseDTO = VotoResponseDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .dataDeCriacao(voto.getDataDeCriacao())
        .build();

    when(objectMapper.convertValue(voto, VotoResponseDTO.class)).thenReturn(votoResponseDTO);

    VotoResponseDTO result = votoMapper.toDTO(voto);

    assertEquals(votoResponseDTO, result);
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaEntitdade() {
    VotoRequestDTO votoRequestDTO = VotoRequestDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .build();

    when(objectMapper.convertValue(votoRequestDTO, Voto.class)).thenThrow(RuntimeException.class);

    assertThrows(BusinessException.class, () -> votoMapper.toEntity(votoRequestDTO));
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaDTO() {
    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .voto(true)
        .cpf(CPF_TESTE)
        .build();

    when(objectMapper.convertValue(voto, VotoResponseDTO.class)).thenThrow(RuntimeException.class);

    assertThrows(BusinessException.class, () -> votoMapper.toDTO(voto));
  }
}
