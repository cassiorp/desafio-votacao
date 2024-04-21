package com.softdesign.api.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.api.dto.VotoRequestDTO;
import com.softdesign.api.dto.VotoResponseDTO;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VotoMapper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String ERROR_LOG_MESSAGE = "Erro ao mapear a voto";

    private final ObjectMapper objectMapper;

    public VotoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Voto toEntity(VotoRequestDTO votoRequestDTO) {
        try {
            return objectMapper.convertValue(votoRequestDTO, Voto.class);
        } catch (RuntimeException e) {
            logger.error(ERROR_LOG_MESSAGE, e);
            throw new BusinessException();
        }
    }

    public VotoResponseDTO toDTO(Voto voto) {
        try {
            return objectMapper.convertValue(voto, VotoResponseDTO.class);
        } catch (RuntimeException e) {
            logger.error(ERROR_LOG_MESSAGE, e);
            throw new BusinessException();
        }
    }

}
