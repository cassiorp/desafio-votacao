package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class PautaRequestDTO {
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
}
