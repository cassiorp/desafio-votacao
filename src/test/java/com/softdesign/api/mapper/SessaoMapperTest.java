package com.softdesign.api.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.SessaoRequestDTO;
import com.softdesign.api.dto.SessaoResponseDTO;
import com.softdesign.entity.Sessao;
import com.softdesign.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class SessaoMapperTest {

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private SessaoMapper sessaoMapper;

  private static final String ID_PAUTA_TESTE = "idPautaTeste";
  private static final Long DURACAO_TESTE = 60L;

  @Test
  public void deveMapearParaEntidade() {
    SessaoRequestDTO sessaoRequestDTO = SessaoRequestDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    when(objectMapper.convertValue(sessaoRequestDTO, Sessao.class)).thenReturn(sessao);

    Sessao result = sessaoMapper.toEntity(sessaoRequestDTO);

    assertEquals(sessao, result);
  }

  @Test
  public void deveMappearParaDTO() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now())
        .dataFim(LocalDateTime.now().plusMinutes(DURACAO_TESTE))
        .build();

    SessaoResponseDTO sessaoResponseDTO = SessaoResponseDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(sessao.getDataComeco())
        .dataFim(sessao.getDataFim())
        .build();

    when(objectMapper.convertValue(sessao, SessaoResponseDTO.class)).thenReturn(sessaoResponseDTO);

    SessaoResponseDTO result = sessaoMapper.toDTO(sessao);

    assertEquals(sessaoResponseDTO, result);
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaEntitdade() {
    SessaoRequestDTO sessaoRequestDTO = SessaoRequestDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    when(objectMapper.convertValue(sessaoRequestDTO, Sessao.class)).thenThrow(
        RuntimeException.class);

    assertThrows(BusinessException.class, () -> sessaoMapper.toEntity(sessaoRequestDTO));
  }

  @Test
  public void deveLancarBusinessExceptionAoMapearParaDTO() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    when(objectMapper.convertValue(sessao, SessaoResponseDTO.class)).thenThrow(
        RuntimeException.class);

    assertThrows(BusinessException.class, () -> sessaoMapper.toDTO(sessao));
  }
}
