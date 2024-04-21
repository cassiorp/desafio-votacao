package com.softdesign.service;

import com.softdesign.entity.Sessao;
import com.softdesign.exception.EntityNotFoundException;
import com.softdesign.repository.SessaoRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

  private final SessaoRepository sessaoRepository;
  private final PautaService pautaService;

  public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
    this.sessaoRepository = sessaoRepository;
    this.pautaService = pautaService;
  }

  public Sessao criar(Sessao sessao) {
    pautaService.buscarPorId(sessao.getIdPauta());
    if (sessao.getDuracao() == null) {
      sessao.setDuracao(60000L);
    }
    sessao.setDataComeco(LocalDateTime.now());
    sessao.setDataFim(LocalDateTime.now().plus(sessao.getDuracao(), ChronoUnit.MILLIS));
    return sessaoRepository.save(sessao);
  }

  public Sessao buscaPorIdPauta(String idPauta) {
    return sessaoRepository.findByIdPauta(idPauta)
        .orElseThrow(() -> {
          throw new EntityNotFoundException("Sessao não encontrada para pauta: " + idPauta);
        });
  }

  public boolean estaAberta(Sessao sessao) {
    LocalDateTime fim = sessao.getDataFim();
    LocalDateTime agora = LocalDateTime.now();
    return agora.isBefore(fim);
  }

}
