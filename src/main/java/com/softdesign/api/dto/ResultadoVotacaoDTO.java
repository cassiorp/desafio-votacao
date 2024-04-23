package com.softdesign.api.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ResultadoVotacaoDTO {
    private String idPauta;
    private String titulo;
    private String descricao;
    private StatusVotacaoDTO status;
    private Long duracao;
    private LocalDateTime dataComeco;
    private LocalDateTime dataFim;
    private Integer totalDeVotos;
    private Integer votosAFavor;
    private Integer votosContra;
}
