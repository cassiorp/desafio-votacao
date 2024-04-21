package com.softdesign.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "sessao")
public class Sessao {
    @Id
    private String id;
    @Indexed(unique = true)
    private String idPauta;
    private Long duracao;
    private LocalDateTime dataComeco;
    private LocalDateTime dataFim;
}
