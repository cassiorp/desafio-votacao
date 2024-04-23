package com.softdesign.service;

import com.softdesign.entity.Sessao;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.ConflictException;
import com.softdesign.exception.EntityNotFoundException;
import com.softdesign.repository.SessaoRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String SESSAO_JA_ABERTA_ERROR_MESSAGE = "Sessão já aberta";

  private final SessaoRepository sessaoRepository;
  private final PautaService pautaService;

  public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
    this.sessaoRepository = sessaoRepository;
    this.pautaService = pautaService;
  }

  public Sessao criar(Sessao sessao) {
    pautaService.buscarPorId(sessao.getIdPauta());
    if (sessao.getDuracao() == null || sessao.getDuracao() <= 0) {
      sessao.setDuracao(60000L);
    }
    sessao.setDataComeco(LocalDateTime.now());
    sessao.setDataFim(LocalDateTime.now().plus(sessao.getDuracao(), ChronoUnit.MILLIS));
    return this.save(sessao);
  }

  private Sessao save(Sessao sessao) {
    try {
      return sessaoRepository.save(sessao);
    } catch (DuplicateKeyException e) {
      logger.error(SESSAO_JA_ABERTA_ERROR_MESSAGE, e);
      throw new ConflictException(SESSAO_JA_ABERTA_ERROR_MESSAGE);
    } catch (RuntimeException e) {
      logger.error("Erro ao salvar sessão", e);
      throw new BusinessException();
    }
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
