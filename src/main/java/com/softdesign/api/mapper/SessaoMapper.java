package com.softdesign.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.SessaoRequestDTO;
import com.softdesign.api.dto.SessaoResponseDTO;
import com.softdesign.entity.Sessao;
import com.softdesign.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessaoMapper {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final static String ERROR_LOG_MESSAGE = "Erro ao mapear a sessão de votação";

  private final ObjectMapper objectMapper;

  public SessaoMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Sessao toEntity(SessaoRequestDTO sessaoRequestDTO) {
    try {
      return objectMapper.convertValue(sessaoRequestDTO, Sessao.class);
    } catch (RuntimeException e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

  public SessaoResponseDTO toDTO(Sessao sessao) {
    try {
      return objectMapper.convertValue(sessao, SessaoResponseDTO.class);
    } catch (RuntimeException e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

}
