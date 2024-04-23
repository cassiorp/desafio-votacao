package com.softdesign.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class VotoRequestDTO {
    @NotBlank
    private String idPauta;
    @NotNull
    private Boolean voto;
    @NotBlank
    @CPF
    private String cpf;
}
