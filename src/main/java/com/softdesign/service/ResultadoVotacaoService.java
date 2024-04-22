package com.softdesign.service;

import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.api.dto.StatusVotacaoDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResultadoVotacaoService {

  private final PautaService pautaService;
  private final SessaoService sessaoService;
  private final VotoService votoService;

  public ResultadoVotacaoService(PautaService pautaService, SessaoService sessaoService,
      VotoService votoService) {
    this.pautaService = pautaService;
    this.sessaoService = sessaoService;
    this.votoService = votoService;
  }

  public ResultadoVotacaoDTO buscarResultadoVotacaoPorIdPauta(String idPauta) {

    Pauta pauta = pautaService.buscarPorId(idPauta);
    Sessao sessao = sessaoService.buscaPorIdPauta(pauta.getId());
    List<Voto> votos = votoService.buscaVotoPorIdPauta(pauta.getId());

    Integer votosAFavor = contaVotosAFavor(votos);
    Integer votosContra = contaVotosContra(votos);

    return ResultadoVotacaoDTO.builder()
        .idPauta(pauta.getId())
        .titulo(pauta.getTitulo())
        .descricao(pauta.getDescricao())
        .duracao(sessao.getDuracao())
        .dataComeco(sessao.getDataComeco())
        .statusVotacaoDTO(getStatusVotacaoDTO(sessao, votosAFavor, votosContra))
        .dataFim(sessao.getDataFim())
        .totalDeVotos(votos.size())
        .votosAFavor(votosAFavor)
        .votosContra(votosContra)
        .build();
  }

  private StatusVotacaoDTO getStatusVotacaoDTO(Sessao sessao, Integer votosAFavor,
      Integer votosContra) {
    if (sessaoService.estaAberta(sessao)) {
      return StatusVotacaoDTO.ABERTA;
    } else if (votosContra > votosAFavor) {
      return StatusVotacaoDTO.REPROVADA;
    } else if (votosAFavor > votosContra) {
      return StatusVotacaoDTO.APROVADA;
    } else {
      return StatusVotacaoDTO.EMPATADA;
    }
  }

  private Integer contaVotosAFavor(List<Voto> votos) {
    return Math.toIntExact(votos.stream().filter(Voto::getVoto).count());
  }

  private Integer contaVotosContra(List<Voto> votos) {
    return Math.toIntExact(votos.stream().filter(v -> !v.getVoto()).count());
  }

}
