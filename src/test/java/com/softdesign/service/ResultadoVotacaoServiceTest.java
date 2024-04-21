package com.softdesign.service;

import com.softdesign.api.dto.ResultadoVotacaoDTO;
import com.softdesign.entity.Pauta;
import com.softdesign.entity.Sessao;
import com.softdesign.entity.Voto;
import com.softdesign.service.PautaService;
import com.softdesign.service.ResultadoVotacaoService;
import com.softdesign.service.SessaoService;
import com.softdesign.service.VotoService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultadoVotacaoServiceTest {

    @Mock
    private PautaService pautaService;
    @Mock
    private SessaoService sessaoService;
    @Mock
    private VotoService votoService;
    @InjectMocks
    private ResultadoVotacaoService resultadoVotacaoService;

    private static final String ID_PAUTA_TESTE = UUID.randomUUID().toString();
    private static final String ID_SESSAO_TESTE = UUID.randomUUID().toString();
    private static final String TITULO_TESTE = "titulo teste";
    private static final String DESCRICAO_TESTE = "descricao teste";
    private static final Long DURACAO_TESTE = 60000l;

    @Test
    void deveBuscarResultadoVotacaoPorIdPauta() {
        Pauta pauta = Pauta.builder()
            .id(ID_PAUTA_TESTE)
            .titulo(TITULO_TESTE)
            .descricao(DESCRICAO_TESTE)
            .build();

        Sessao sessao = Sessao.builder()
            .id(ID_SESSAO_TESTE)
            .duracao(DURACAO_TESTE)
            .dataComeco(LocalDateTime.now().minusMinutes(1))
            .dataFim(LocalDateTime.now())
            .build();

        List<Voto> votos = Arrays.asList(
            Voto.builder().voto(true).build(),
            Voto.builder().voto(true).build(),
            Voto.builder().voto(false).build(),
            Voto.builder().voto(false).build()
        );

        when(pautaService.buscarPorId(ID_PAUTA_TESTE)).thenReturn(pauta);
        when(sessaoService.buscaPorIdPauta(ID_PAUTA_TESTE)).thenReturn(sessao);
        when(votoService.findAllByIdPauta(ID_PAUTA_TESTE)).thenReturn(votos);

        ResultadoVotacaoDTO resultado = resultadoVotacaoService.buscarResultadoVotacaoPorIdPauta(ID_PAUTA_TESTE);

        assertEquals(ID_PAUTA_TESTE, resultado.getIdPauta());
    }
}
