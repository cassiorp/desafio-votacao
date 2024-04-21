package com.softdesign.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.VotoRequestDTO;
import com.softdesign.api.dto.VotoResponseDTO;
import com.softdesign.api.mapper.VotoMapper;
import com.softdesign.entity.Voto;
import com.softdesign.service.VotoService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private VotoService votoService;
  @MockBean
  private VotoMapper votoMapper;

  private static final String CPF_TESTE = "70842605010";
  private static final String ID_PAUTA_TESTE = UUID.randomUUID().toString();
  private static final String ID_VOTO_TESTE = UUID.randomUUID().toString();

  @Test
  public void testVotar() throws Exception {

    VotoRequestDTO requestDTO = VotoRequestDTO.builder()
        .cpf(CPF_TESTE)
        .idPauta(ID_PAUTA_TESTE)
        .voto(Boolean.TRUE)
        .build();

    Voto voto = Voto.builder()
        .cpf(CPF_TESTE)
        .idPauta(ID_PAUTA_TESTE)
        .voto(Boolean.TRUE)
        .build();

    VotoResponseDTO responseDTO = VotoResponseDTO.builder()
        .id(ID_VOTO_TESTE)
        .cpf(CPF_TESTE)
        .idPauta(ID_PAUTA_TESTE)
        .voto(Boolean.TRUE)
        .dataDeCriacao(LocalDateTime.now())
        .build();

    when(votoMapper.toEntity(requestDTO)).thenReturn(voto);
    when(votoService.votar(any(Voto.class))).thenReturn(voto);
    when(votoMapper.toDTO(voto)).thenReturn(responseDTO);

    String json = new ObjectMapper().writeValueAsString(requestDTO);

    mockMvc.perform(post("/api/v1/voto")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID_VOTO_TESTE));
  }

}
