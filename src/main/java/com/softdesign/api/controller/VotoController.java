package com.softdesign.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.softdesign.api.dto.VotoRequestDTO;
import com.softdesign.api.dto.VotoResponseDTO;
import com.softdesign.api.mapper.VotoMapper;
import com.softdesign.entity.Voto;
import com.softdesign.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {

  private final VotoService votoService;
  private final VotoMapper votoMapper;

  public VotoController(VotoService votoService, VotoMapper votoMapper) {
    this.votoService = votoService;
    this.votoMapper = votoMapper;
  }

  @PostMapping
  public ResponseEntity<VotoResponseDTO> votar(
      @RequestBody @Valid VotoRequestDTO votoRequestDTO
  ) {
    Voto voto = votoMapper.toEntity(votoRequestDTO);
    voto = votoService.votar(voto);
    VotoResponseDTO votoResponseDTO = votoMapper.toDTO(voto);
    return new ResponseEntity<>(votoResponseDTO, CREATED);
  }

}
