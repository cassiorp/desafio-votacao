package com.softdesign.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.softdesign.api.dto.PautaRequestDTO;
import com.softdesign.api.dto.PautaResponseDTO;
import com.softdesign.api.mapper.PautaMapper;
import com.softdesign.entity.Pauta;
import com.softdesign.service.PautaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

  private final PautaService pautaService;
  private final PautaMapper pautaMapper;

  public PautaController(PautaService pautaService, PautaMapper pautaMapper) {
    this.pautaService = pautaService;
    this.pautaMapper = pautaMapper;
  }

  @PostMapping
  public ResponseEntity<PautaResponseDTO> criarPauta(
      @RequestBody @Valid PautaRequestDTO pautaRequestDTO
  ) {
    Pauta pauta = pautaMapper.toEntity(pautaRequestDTO);
    pauta = pautaService.criar(pauta);
    PautaResponseDTO pautaResponseDTO = pautaMapper.toDTO(pauta);
    return new ResponseEntity<>(pautaResponseDTO, CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PautaResponseDTO>> buscarPautas() {
    List<PautaResponseDTO> pautaResponseDTOS = pautaService.buscaTodas().stream()
        .map(pautaMapper::toDTO)
        .collect(Collectors.toList());
    return new ResponseEntity<>(pautaResponseDTOS, OK);
  }

}
