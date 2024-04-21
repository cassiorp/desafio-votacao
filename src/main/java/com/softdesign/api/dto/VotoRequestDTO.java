package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequestDTO {
    @NotBlank
    private String idPauta;
    @NotNull
    private Boolean voto;
    @NotBlank
    private String cpf;
}
