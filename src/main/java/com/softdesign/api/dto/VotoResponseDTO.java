package com.softdesign.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoResponseDTO {
    private String id;
    private String idPauta;
    private String cpf;
    private Boolean voto;
    private LocalDateTime dataDeCriacao;
}