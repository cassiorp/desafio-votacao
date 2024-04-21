package com.softdesign.service;

import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.ConflictException;
import com.softdesign.repository.VotoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String ERROR_LOG_MESSAGE = "Erro ao votar";
  private static final String VOTACAO_ENCERRADA_MESSAGE = "Sessão de votação encerrada";

  private final VotoRepository votoRepository;
  private final SessaoService sessaoService;

  public VotoService(VotoRepository votoRepository, SessaoService sessaoService) {
    this.votoRepository = votoRepository;
    this.sessaoService = sessaoService;
  }

  public Voto votar(Voto voto) {
    Sessao sessao = sessaoService.buscaPorIdPauta(voto.getIdPauta());
    if (sessaoService.estaAberta(sessao)) {
      return criar(voto);
    } else {
      logger.error(VOTACAO_ENCERRADA_MESSAGE);
      throw new ConflictException(VOTACAO_ENCERRADA_MESSAGE);
    }
  }

  private Voto criar(Voto voto) {
    try {
      voto.setDataDeCriacao(LocalDateTime.now());
      return votoRepository.save(voto);
    } catch (Exception e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

  public List<Voto> findAllByIdPauta(String idPauta) {
    return votoRepository.findAllByIdPauta(idPauta);
  }

}
