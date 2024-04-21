package com.softdesign.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.SessaoRequestDTO;
import com.softdesign.api.dto.SessaoResponseDTO;
import com.softdesign.api.mapper.SessaoMapper;
import com.softdesign.entity.Sessao;
import com.softdesign.service.SessaoService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SessaoController.class)
public class SessaoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private SessaoService sessaoService;
  @MockBean
  private SessaoMapper sessaoMapper;

  private static final String ID_PAUTA_TESTE = UUID.randomUUID().toString();
  private static final String ID_SESSAO_TESTE = UUID.randomUUID().toString();
  private static final Long DURACAO_TESTE = 12000l;

  @Test
  public void testAbreSessaoDeVotacaoEmPauta() throws Exception {
    SessaoRequestDTO requestDTO = SessaoRequestDTO.builder()
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    Sessao sessao = Sessao.builder()
        .id(ID_SESSAO_TESTE)
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    SessaoResponseDTO responseDTO = SessaoResponseDTO.builder()
        .id(ID_SESSAO_TESTE)
        .idPauta(ID_PAUTA_TESTE)
        .duracao(DURACAO_TESTE)
        .build();

    when(sessaoMapper.toEntity(requestDTO)).thenReturn(sessao);
    when(sessaoService.criar(any(Sessao.class))).thenReturn(sessao);
    when(sessaoMapper.toDTO(sessao)).thenReturn(responseDTO);

    String json = new ObjectMapper().writeValueAsString(requestDTO);

    mockMvc.perform(post("/api/v1/sessao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID_SESSAO_TESTE));
  }

}
