package com.softdesign.api.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
public class ResultadoVotacaoDTO {
    private String idPauta;
    private String titulo;
    private String descricao;
    private StatusVotacaoDTO statusVotacaoDTO;
    private Long duracao;
    private LocalDateTime dataComeco;
    private LocalDateTime dataFim;
    private Integer totalDeVotos;
    private Integer votosAFavor;
    private Integer votosContra;
}
