package com.softdesign.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.softdesign.api.dto.SessaoRequestDTO;
import com.softdesign.api.dto.SessaoResponseDTO;
import com.softdesign.api.mapper.SessaoMapper;
import com.softdesign.entity.Sessao;
import com.softdesign.service.SessaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/sessao")
public class SessaoController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final SessaoService sessaoService;
  private final SessaoMapper sessaoMapper;

  public SessaoController(SessaoService sessaoService, SessaoMapper sessaoMapper) {
    this.sessaoService = sessaoService;
    this.sessaoMapper = sessaoMapper;
  }

  @PostMapping
  public ResponseEntity<SessaoResponseDTO> abreSessaoDeVotacaoEmPauta(
      @RequestBody @Valid SessaoRequestDTO sessaoRequestDTO
  ) {
    logger.info("Abrindo sessao de votacao: {}", sessaoRequestDTO);
    Sessao sessao = sessaoMapper.toEntity(sessaoRequestDTO);
    sessao = sessaoService.criar(sessao);
    SessaoResponseDTO sessaoResponseDTO = sessaoMapper.toDTO(sessao);
    logger.info("Sessao de votacao aberta: {}", sessaoResponseDTO);
    return new ResponseEntity<>(sessaoResponseDTO, CREATED);
  }

}
