package com.softdesign.repository;

import com.softdesign.entity.Sessao;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessaoRepository extends MongoRepository<Sessao, String> {
  Optional<Sessao> findByIdPauta(String idPauta);
}
