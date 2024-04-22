package com.softdesign.service;

import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.ConflictException;
import com.softdesign.repository.VotoRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

  @Mock
  private VotoRepository votoRepository;
  @Mock
  private SessaoService sessaoService;
  @InjectMocks
  private VotoService votoService;

  private static final String ID_PAUTA_TESTE = UUID.randomUUID().toString();
  private static final String CPF_TESTE = "18002242068";

  @Test
  void votarSessaoAberta() {
    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .cpf(CPF_TESTE)
        .voto(Boolean.TRUE)
        .build();

    Sessao sessao = Sessao.builder()
        .duracao(60000l)
        .dataComeco(LocalDateTime.now().minusMinutes(10))
        .dataFim(LocalDateTime.now().plusMinutes(10))
        .build();

    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(sessaoService.estaAberta(sessao)).thenReturn(true);
    when(votoRepository.save(any(Voto.class))).thenReturn(voto);

    Voto result = votoService.votar(voto);

    assertNotNull(result);
    assertEquals(ID_PAUTA_TESTE, result.getIdPauta());
    verify(votoRepository, times(1)).save(voto);
  }

  @Test
  void deveBuscarVotosPorIdPauta() {
    when(votoRepository.findAllByIdPauta(ID_PAUTA_TESTE)).thenReturn(
        Arrays.asList(
            Voto.builder().idPauta(ID_PAUTA_TESTE).id(UUID.randomUUID().toString()).build(),
            Voto.builder().idPauta(ID_PAUTA_TESTE).id(UUID.randomUUID().toString()).build(),
            Voto.builder().idPauta(ID_PAUTA_TESTE).id(UUID.randomUUID().toString()).build(),
            Voto.builder().idPauta(ID_PAUTA_TESTE).id(UUID.randomUUID().toString()).build()
        )
    );
    List<Voto> votos = votoService.buscaVotoPorIdPauta(ID_PAUTA_TESTE);
    assertEquals(votos.size(), 4);
  }

  @Test
  void votarSessaoFechada() {
    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .cpf(CPF_TESTE)
        .voto(Boolean.TRUE)
        .build();

    Sessao sessao = Sessao.builder()
        .duracao(60000l)
        .dataComeco(LocalDateTime.now().minusMinutes(20))
        .dataFim(LocalDateTime.now().plusMinutes(10))
        .build();

    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(sessaoService.estaAberta(sessao)).thenReturn(false);

    assertThrows(ConflictException.class, () -> votoService.votar(voto));
  }


  @Test
  void deveLancarConflictException() {
    Sessao sessao = Sessao.builder().build();
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(sessaoService.estaAberta(any())).thenReturn(true);
    when(votoRepository.save(any())).thenThrow(DuplicateKeyException.class);
    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .build();
    assertThrows(ConflictException.class, () -> {
      votoService.votar(voto);
    });
  }

  @Test
  void deveLancarBusinessException() {
    Sessao sessao = Sessao.builder().build();
    when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
    when(sessaoService.estaAberta(sessao)).thenReturn(true);

    when(votoRepository.save(any())).thenThrow(RuntimeException.class);

    Voto voto = Voto.builder()
        .idPauta(ID_PAUTA_TESTE)
        .build();

    assertThrows(BusinessException.class, () -> {
      votoService.votar(voto);
    });
  }

}
