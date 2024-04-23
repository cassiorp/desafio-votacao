package com.softdesign.service;

import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.api.dto.StatusVotacaoDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResultadoVotacaoService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    try {

      Integer votosAFavor = contaVotosAFavor(votos);
      Integer votosContra = contaVotosContra(votos);

      return ResultadoVotacaoDTO.builder()
          .idPauta(pauta.getId())
          .titulo(pauta.getTitulo())
          .descricao(pauta.getDescricao())
          .duracao(sessao.getDuracao())
          .dataComeco(sessao.getDataComeco())
          .status(getStatusVotacaoDTO(sessao, votosAFavor, votosContra))
          .dataFim(sessao.getDataFim())
          .totalDeVotos(votos.size())
          .votosAFavor(votosAFavor)
          .votosContra(votosContra)
          .build();

    } catch (RuntimeException e) {
      logger.error("Erro ao montar resultado de votação: ", e);
      throw new BusinessException();
    }

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
