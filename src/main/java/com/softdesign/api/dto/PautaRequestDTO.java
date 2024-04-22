package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class PautaRequestDTO {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
}
