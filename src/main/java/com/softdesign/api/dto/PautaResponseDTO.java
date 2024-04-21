package com.softdesign.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PautaResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataDeCriacao;
}
