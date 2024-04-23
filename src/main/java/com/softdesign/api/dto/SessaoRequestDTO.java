package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class SessaoRequestDTO {
    @NotBlank
    private String idPauta;
    private Long duracao;
}
