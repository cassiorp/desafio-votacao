package com.softdesign.service;

import com.softdesign.entity.Pauta;
import com.softdesign.exception.BusinessException;
import com.softdesign.exception.EntityNotFoundException;
import com.softdesign.repository.PautaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String ERROR_LOG_MESSAGE = "Erro ao criar pauta";

  private final PautaRepository pautaRepository;

  public PautaService(PautaRepository pautaRepository) {
    this.pautaRepository = pautaRepository;
  }

  public Pauta criar(Pauta pauta) {
    try {
      pauta.setDataDeCriacao(LocalDateTime.now());
      return pautaRepository.save(pauta);
    } catch (Exception e) {
      logger.error(ERROR_LOG_MESSAGE, e);
      throw new BusinessException();
    }
  }

  public List<Pauta> buscaTodas() {
    return pautaRepository.findAll();
  }

  public Pauta buscarPorId(String id) {
    return pautaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada para id: " + id));
  }

}
