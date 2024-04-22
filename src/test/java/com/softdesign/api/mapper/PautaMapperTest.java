package com.softdesign.api.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.PautaRequestDTO;
import com.softdesign.api.dto.PautaResponseDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PautaMapperTest {

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private PautaMapper pautaMapper;

  private static final String TITULO_TESTE = "Titulo Teste";
  private static final String DESCRICAO_TESTE = "Descricao";

  @Test
  public void deveMapearParaEntidade() {
    PautaRequestDTO pautaRequestDTO = PautaRequestDTO.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Pauta pauta = Pauta.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    when(objectMapper.convertValue(pautaRequestDTO, Pauta.class)).thenReturn(pauta);

    Pauta result = pautaMapper.toEntity(pautaRequestDTO);

    assertEquals(pauta, result);
  }

  @Test
  public void deveMappearParaDTO() {
    PautaResponseDTO pautaResponseDTO = PautaResponseDTO.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Pauta pauta = Pauta.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    when(objectMapper.convertValue(pauta, PautaResponseDTO.class)).thenReturn(pautaResponseDTO);

    PautaResponseDTO result = pautaMapper.toDTO(pauta);

    assertEquals(pautaResponseDTO, result);
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaEntitdade() {
    PautaRequestDTO pautaRequestDTO = PautaRequestDTO.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    when(objectMapper.convertValue(pautaRequestDTO, Pauta.class)).thenThrow(RuntimeException.class);
    assertThrows(BusinessException.class, () -> pautaMapper.toEntity(pautaRequestDTO));
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaDTO() {
    Pauta pauta = Pauta.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();
    when(objectMapper.convertValue(pauta, PautaResponseDTO.class)).thenThrow(
        RuntimeException.class);
    assertThrows(BusinessException.class, () -> pautaMapper.toDTO(pauta));
  }
}
