package com.softdesign.service;

import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.api.dto.StatusVotacaoDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultadoVotacaoServiceTest {

  @Mock
  private PautaService pautaService;
  @Mock
  private SessaoService sessaoService;
  @Mock
  private VotoService votoService;
  @InjectMocks
  private ResultadoVotacaoService resultadoVotacaoService;

  private static final String ID_PAUTA_TESTE = UUID.randomUUID().toString();
  private static final String ID_SESSAO_TESTE = UUID.randomUUID().toString();
  private static final String TITULO_TESTE = "titulo teste";
  private static final String DESCRICAO_TESTE = "descricao teste";
  private static final Long DURACAO_TESTE = 60000l;

  @Test
  void deveBuscarResultadoVotacaoPorIdPautaComSessaoAberta() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now().minusMinutes(1))
        .dataFim(LocalDateTime.now())
        .build();

    List<Voto> votos = Arrays.asList(
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build()
    );

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);

    ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(
        ID_PAUTA_TESTE);

    assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
  }

  @Test
  void deveBuscarResultadoVotacaoPorIdPautaComVotacaoEmpatada() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now().minusMinutes(1))
        .dataFim(LocalDateTime.now())
        .build();

    List<Voto> votos = Arrays.asList(
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build()
    );

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);

    ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(
        ID_PAUTA_TESTE);

    assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
    assertEquals(resultado.getStatus(), StatusVotacaoDTO.EMPATADA);
  }

  @Test
  void deveBuscarResultadoVotacaoPorIdPautaComVotacaoAberta() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now().minusMinutes(5))
        .dataFim(LocalDateTime.now())
        .build();

    List<Voto> votos = Arrays.asList(
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build()
    );

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);
    when(sessaoService.estaAberta(any())).thenReturn(true);

    ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(
        ID_PAUTA_TESTE);

    assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
    assertEquals(resultado.getStatus(), StatusVotacaoDTO.ABERTA);
  }

  @Test
  void deveBuscarResultadoVotacaoPorIdPautaComVotacaoAprovada() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now().minusMinutes(5))
        .dataFim(LocalDateTime.now())
        .build();

    List<Voto> votos = Arrays.asList(
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build()
    );

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);
    when(sessaoService.estaAberta(any())).thenReturn(false);

    ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(
        ID_PAUTA_TESTE);

    assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
    assertEquals(resultado.getStatus(), StatusVotacaoDTO.APROVADA);
  }

  @Test
  void deveBuscarResultadoVotacaoPorIdPautaComVotacaoReprovada() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .duracao(DURACAO_TESTE)
        .dataComeco(LocalDateTime.now().minusMinutes(5))
        .dataFim(LocalDateTime.now())
        .build();

    List<Voto> votos = Arrays.asList(
        Voto.builder().voto(true).build(),
        Voto.builder().voto(true).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build(),
        Voto.builder().voto(false).build()
    );

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);
    when(sessaoService.estaAberta(any())).thenReturn(false);

    ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(
        ID_PAUTA_TESTE);

    assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
    assertEquals(resultado.getStatus(), StatusVotacaoDTO.REPROVADA);
  }

  @Test
  void deveLancarBusinessException() {
    Pauta pauta = Pauta.builder()
        .id(ID_PAUTA_TESTE)
        .build();

    when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);

    assertThrows(BusinessException.class, () -> {
      resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(ID_PAUTA_TESTE);
    });
  }
}
