package com.softdesign.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessaoRequestDTO {
    @NotNull
    private String idPauta;
    private Long duracao;
}
