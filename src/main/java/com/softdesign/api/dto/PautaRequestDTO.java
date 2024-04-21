package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaRequestDTO {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
}
