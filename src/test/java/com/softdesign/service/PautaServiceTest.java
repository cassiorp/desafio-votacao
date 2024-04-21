package com.softdesign.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.softdesign.entity.Pauta;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.EntityNotFoundException;
import com.softdesign.repository.PautaRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

  @Mock
  private PautaRepository pautaRepository;
  @InjectMocks
  private PautaService pautaService;

  private static final String ID_TESTE = UUID.randomUUID().toString();

  @Test
  void deveCriarPauta() {
    Pauta pauta = Pauta.builder()
        .titulo("Pauta Bacana")
        .descricao("Devin é uma farsa?")
        .build();

    LocalDateTime antesDaCriacao = LocalDateTime.now().minusSeconds(1);

    when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

    Pauta result = pautaService.criar(pauta);

    assertNotNull(result);
    assertEquals("Pauta Bacana", result.getTitulo());
    assertEquals("Devin é uma farsa?", result.getDescricao());
    assertTrue(result.getDataDeCriacao().isAfter(antesDaCriacao));
    verify(pautaRepository, times(1)).save(pauta);
  }

  @Test
  void deveLancarBusinessException() {
    Pauta pauta = Pauta.builder().build();

    when(pautaRepository.save(any(Pauta.class))).thenThrow(new RuntimeException("Erro ao salvar"));

    assertThrows(BusinessException.class, () -> pautaService.criar(pauta));
    verify(pautaRepository, times(1)).save(pauta);
  }

  @Test
  void testBuscarPorId() {
    Pauta pauta = Pauta.builder()
        .id(ID_TESTE)
        .titulo("Pauta Bacana")
        .descricao("Devin é uma farsa?")
        .build();
    when(pautaRepository.findById(ID_TESTE)).thenReturn(Optional.of(pauta));

    Pauta result = pautaService.buscarPorId(ID_TESTE);

    assertNotNull(result);
    assertEquals(ID_TESTE, result.getId());
    verify(pautaRepository, times(1)).findById(ID_TESTE);
  }

  @Test
  void testBuscarPorIdNaoEncontrada() {
    when(pautaRepository.findById(ID_TESTE)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> pautaService.buscarPorId(ID_TESTE));
    verify(pautaRepository, times(1)).findById(ID_TESTE);
  }

  @Test
  void testBuscaTodas() {
    Pauta pauta1 = Pauta.builder()
        .id("1")
        .titulo("1Pauta Bacana1")
        .descricao("1Devin é uma farsa?1")
        .build();

    Pauta pauta2 = Pauta.builder()
        .id("2")
        .titulo("2Pauta Bacana2")
        .descricao("2Devin é uma farsa?2")
        .build();

    List<Pauta> pautas = Arrays.asList(pauta1, pauta2);

    when(pautaRepository.findAll()).thenReturn(pautas);

    List<Pauta> result = pautaService.buscaTodas();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("1", result.get(0).getId());
    assertEquals("2", result.get(1).getId());
    verify(pautaRepository, times(1)).findAll();
  }
}
