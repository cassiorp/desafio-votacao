package com.softdesign.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class SessaoResponseDTO {
    private String id;
    private String idPauta;
    private Long duracao;
    private LocalDateTime dataComeco;
    private LocalDateTime dataFim;
}
