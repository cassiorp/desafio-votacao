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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String VOTACAO_ENCERRADA_MESSAGE = "Sessão de votação encerrada";
  private static final String CPF_JA_VOTOU_MESSAGE = "CPF já votou: ";

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
    voto.setDataDeCriacao(LocalDateTime.now());
    return this.save(voto);
  }

  private Voto save(Voto vote) {
    try {
      return votoRepository.save(vote);
    } catch (DuplicateKeyException e) {
      logger.error(CPF_JA_VOTOU_MESSAGE, e);
      throw new ConflictException(CPF_JA_VOTOU_MESSAGE);
    } catch (RuntimeException e) {
      logger.error("Erro ao salvar voto", e);
      throw new BusinessException();
    }
  }

  public List<Voto> buscaVotoPorIdPauta(String idPauta) {
    return votoRepository.findAllByIdPauta(idPauta);
  }

}
