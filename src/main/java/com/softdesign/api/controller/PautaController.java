package com.softdesign.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.softdesign.api.dto.PautaRequestDTO;
import com.softdesign.api.dto.PautaResponseDTO;
import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.api.mapper.PautaMapper;
import com.softdesign.entity.Pauta;
import com.softdesign.service.PautaService;
import com.softdesign.service.ResultadoVotacaoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ResultadoVotacaoService resultadoVotacaoService;
  private final PautaService pautaService;
  private final PautaMapper pautaMapper;

  public PautaController(ResultadoVotacaoService resultadoVotacaoService, PautaService pautaService,
      PautaMapper pautaMapper) {
    this.resultadoVotacaoService = resultadoVotacaoService;
    this.pautaService = pautaService;
    this.pautaMapper = pautaMapper;
  }

  @PostMapping
  public ResponseEntity<PautaResponseDTO> criarPauta(
      @RequestBody @Valid PautaRequestDTO pautaRequestDTO
  ) {
    logger.info("Criando pauta: {} ", pautaRequestDTO);
    Pauta pauta = pautaMapper.toEntity(pautaRequestDTO);
    pauta = pautaService.criar(pauta);
    PautaResponseDTO pautaResponseDTO = pautaMapper.toDTO(pauta);
    logger.info("Pauta criada: {}", pautaResponseDTO);
    return new ResponseEntity<>(pautaResponseDTO, CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PautaResponseDTO>> buscarPautas() {
    List<PautaResponseDTO> pautaResponseDTOS = pautaService.buscaTodas().stream()
        .map(pautaMapper::toDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(pautaResponseDTOS, OK);
  }

  @GetMapping("/{id}/resultado")
  public ResponseEntity<ResultadoVotacaoDTO> resultadoVotacao(@PathVariable String id) {
    logger.info("Buscando resultado de pauta: {} ", id);
    ResultadoVotacaoDTO resultadoVotacaoDTO = resultadoVotacaoService
        .buscarResultadoVotacaoPorIdPauta(id);
    logger.info("Resultado {} de pauta: {} ", resultadoVotacaoDTO, id);
    return new ResponseEntity<>(resultadoVotacaoDTO, OK);
  }

}
