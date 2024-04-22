package com.softdesign.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "pauta")
public class Pauta {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    @Setter
    private LocalDateTime dataDeCriacao;
}
