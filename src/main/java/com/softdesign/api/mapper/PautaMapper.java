package com.softdesign.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.PautaRequestDTO;
import com.softdesign.api.dto.PautaResponseDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PautaMapper {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String ERROR_LOG_MESSAGE = "Erro ao mapear pauta";

  private final ObjectMapper objectMapper;

  public PautaMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Pauta toEntity(PautaRequestDTO pautaRequestDTO) {
    try {
      return objectMapper.convertValue(pautaRequestDTO, Pauta.class);
    } catch (RuntimeException e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

  public PautaResponseDTO toDTO(Pauta pauta) {
    try {
      return objectMapper.convertValue(pauta, PautaResponseDTO.class);
    } catch (RuntimeException e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

}
