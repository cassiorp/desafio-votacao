package com.softdesign.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.PautaRequestDTO;
import com.softdesign.api.dto.PautaResponseDTO;
import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.api.dto.StatusVotacaoDTO;
import com.softdesign.api.mapper.PautaMapper;
import com.softdesign.entity.Pauta;
import com.softdesign.service.PautaService;
import com.softdesign.service.ResultadoVotacaoService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PautaService pautaService;
  @MockBean
  private PautaMapper pautaMapper;
  @MockBean
  private ResultadoVotacaoService resultadoVotacaoService;

  private final static String PATH = "/api/v1/pauta";
  private final static String ID_TESTE = "507f1f77bcf86cd799439011";
  private final static String TITULO_TESTE = "Pauta de Teste";
  private final static String DESCRICAO_TESTE = "Descricao de Teste";
  private final static LocalDateTime DATA_CRIACAO_TESTE = LocalDateTime.now();


  @Test
  void deveCriarPautaEStatus201() throws Exception {
    PautaRequestDTO requestDTO = PautaRequestDTO.builder()
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .build();

    Pauta pauta = Pauta.builder()
        .id(ID_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .dataDeCriacao(DATA_CRIACAO_TESTE)
        .build();

    PautaResponseDTO responseDTO = PautaResponseDTO.builder()
        .id(ID_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .dataDeCriacao(DATA_CRIACAO_TESTE)
        .build();

    when(pautaMapper.toEntity(requestDTO)).thenReturn(pauta);
    when(pautaService.criar(any(Pauta.class))).thenReturn(pauta);
    when(pautaMapper.toDTO(pauta)).thenReturn(responseDTO);

    String json = new ObjectMapper().writeValueAsString(requestDTO);

    mockMvc.perform(post(PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID_TESTE))
        .andExpect(jsonPath("$.titulo").value(TITULO_TESTE))
        .andExpect(jsonPath("$.dataDeCriacao").isNotEmpty())
        .andExpect(jsonPath("$.descricao").value(DESCRICAO_TESTE));
  }

  @Test
  void deveLancarBadRequestComTituloInvalidoEStatus400() throws Exception {
    PautaRequestDTO requestDTO = PautaRequestDTO.builder()
        .titulo(null)
        .descricao(DESCRICAO_TESTE)
        .build();

    String json = new ObjectMapper().writeValueAsString(requestDTO);

    mockMvc.perform(post(PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deveLancarBadRequestComDescricaoInvalidaEStatus400() throws Exception {
    PautaRequestDTO requestDTO = PautaRequestDTO.builder()
        .titulo(TITULO_TESTE)
        .descricao("")
        .build();

    String json = new ObjectMapper().writeValueAsString(requestDTO);

    mockMvc.perform(post(PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deveRetornarListaDePautasEStatus200() throws Exception {
    Pauta pauta1 = Pauta.builder()
        .id("1")
        .titulo("Pauta 1")
        .descricao("Descrição da Pauta 1")
        .dataDeCriacao(LocalDateTime.now())
        .build();

    Pauta pauta2 = Pauta.builder()
        .id("2")
        .titulo("Pauta 2")
        .descricao("Descrição da Pauta 2")
        .dataDeCriacao(LocalDateTime.now())
        .build();

    List<Pauta> pautas = Arrays.asList(pauta1, pauta2);

    PautaResponseDTO responseDTO1 = PautaResponseDTO.builder()
        .id("1")
        .titulo("Pauta 1")
        .descricao("Descrição da Pauta 1")
        .dataDeCriacao(pauta1.getDataDeCriacao())
        .build();

    PautaResponseDTO responseDTO2 = PautaResponseDTO.builder()
        .id("2")
        .titulo("Pauta 2")
        .descricao("Descrição da Pauta 2")
        .dataDeCriacao(pauta2.getDataDeCriacao())
        .build();

    when(pautaService.buscaTodas()).thenReturn(pautas);
    when(pautaMapper.toDTO(pauta1)).thenReturn(responseDTO1);
    when(pautaMapper.toDTO(pauta2)).thenReturn(responseDTO2);

    mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value("1"))
        .andExpect(jsonPath("$[0].titulo").value("Pauta 1"))
        .andExpect(jsonPath("$[0].descricao").value("Descrição da Pauta 1"))
        .andExpect(jsonPath("$[1].id").value("2"))
        .andExpect(jsonPath("$[1].titulo").value("Pauta 2"))
        .andExpect(jsonPath("$[1].descricao").value("Descrição da Pauta 2"));
  }

  @Test
  void testResultadoVotacao() throws Exception {
    ResultadoVotacaoDTO resultadoVotacaoDTO = ResultadoVotacaoDTO.builder()
        .idPauta(ID_TESTE)
        .titulo(TITULO_TESTE)
        .descricao(DESCRICAO_TESTE)
        .statusVotacaoDTO(StatusVotacaoDTO.ABERTA)
        .duracao(60000L)
        .dataComeco(LocalDateTime.now())
        .dataFim(LocalDateTime.now().plusMinutes(1))
        .totalDeVotos(10)
        .votosAFavor(7)
        .votosContra(3)
        .build();

    when(resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(ID_TESTE))
        .thenReturn(resultadoVotacaoDTO);

    mockMvc.perform(get("/api/v1/pauta/{id}/resultado", ID_TESTE)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idPauta").value(ID_TESTE))
    ;
  }
}
