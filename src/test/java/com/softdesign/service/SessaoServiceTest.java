package com.softdesign.service;

import com.softdesign.entity.Sessao;
import com.softdesign.exception.EntityNotFoundException;
import com.softdesign.repository.SessaoRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

  @Mock
  private PautaService pautaService;
  @Mock
  private SessaoRepository sessaoRepository;
  @InjectMocks
  private SessaoService sessaoService;

  private static final String ID_TESTE = UUID.randomUUID().toString();
  private static final Long DURACAO_TESTE = 120000L;
  private static final Long DURACAO_PADRAO_TESTE = 60000L;

  @Test
  public void deveCriarSessao() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

    Sessao result = sessaoService.criar(sessao);

    assertNotNull(result);
    assertEquals(sessao.getIdPauta(), result.getIdPauta());
    assertEquals(sessao.getDuracao(), DURACAO_TESTE);
    assertNotNull(result.getDataComeco());
    assertNotNull(result.getDataFim());
  }

  @Test
  public void deveCriarSessaoComTempoDefault1Min() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_TESTE)
        .build();

    when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

    Sessao result = sessaoService.criar(sessao);

    assertEquals(result.getDuracao(), DURACAO_PADRAO_TESTE);
  }

  @Test
  public void deveBuscaPorIdSessaoExistente() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_TESTE)
        .build();

    when(sessaoRepository.findByIdPauta(ID_TESTE)).thenReturn(Optional.of(sessao));

    Sessao result = sessaoService.buscaPorIdPauta(ID_TESTE);

    assertNotNull(result);
    assertEquals(sessao.getIdPauta(), result.getIdPauta());
  }

  @Test
  public void deveBuscaPorIdPautaNaoExistente() {
    Sessao sessao = Sessao.builder()
        .idPauta(ID_TESTE)
        .build();

    when(pautaService.buscarPorId(ID_TESTE)).thenThrow(EntityNotFoundException.class);

    assertThrows(EntityNotFoundException.class, () -> {
      sessaoService.criar(sessao);
    });
  }

  @Test
  public void deveBuscaPorIdSessaoNaoExistente() {
    when(sessaoRepository.findByIdPauta(ID_TESTE)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      sessaoService.buscaPorIdPauta(ID_TESTE);
    });
  }

  @Test
  public void deveEstarAberta() {
    LocalDateTime agora = LocalDateTime.now();
    LocalDateTime fim = agora.plusMinutes(1);
    Sessao sessao = Sessao.builder()
        .dataFim(fim)
        .build();

    boolean result = sessaoService.estaAberta(sessao);

    assertTrue(result);
  }

  @Test
  public void testNaoEstarAberta() {
    LocalDateTime agora = LocalDateTime.now();
    LocalDateTime fim = agora.minusMinutes(1);
    Sessao sessao = Sessao.builder()
        .dataFim(fim)
        .build();

    boolean result = sessaoService.estaAberta(sessao);

    assertFalse(result);
  }

}
