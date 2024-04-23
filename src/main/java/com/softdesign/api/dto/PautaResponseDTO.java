package com.softdesign.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class PautaResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataDeCriacao;
}
