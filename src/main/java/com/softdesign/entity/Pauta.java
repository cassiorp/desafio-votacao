package com.softdesign.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "pauta")
@EqualsAndHashCode
public class Pauta {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataDeCriacao;
}
