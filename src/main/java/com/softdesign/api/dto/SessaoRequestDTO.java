package com.softdesign.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class SessaoRequestDTO {
    @NotNull
    private String idPauta;
    private Long duracao;
}
