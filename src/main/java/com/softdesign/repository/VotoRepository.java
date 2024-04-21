package com.softdesign.repository;

import com.softdesign.entity.Voto;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotoRepository extends MongoRepository<Voto, String> {
  List<Voto> findAllByIdPauta(String idPauta);
}
