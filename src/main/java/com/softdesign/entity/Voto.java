package com.softdesign.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "voto")
@CompoundIndex(name = "unique_index", def = "{'idPauta': 1, 'cpf': 1}", unique = true)
public class Voto {
    private String id;
    private String idPauta;
    private String cpf;
    private Boolean voto;
    private LocalDateTime dataDeCriacao;
}
