package com.softdesign.api.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class SessaoResponseDTO {
    private String id;
    private String idPauta;
    private Long duracao;
    private LocalDateTime dataComeco;
    private LocalDateTime dataFim;
}
