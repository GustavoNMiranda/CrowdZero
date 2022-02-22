package com.example.crowdzero.models;

import java.util.Arrays;
import java.util.List;

public class FAKE_INSTITUICOES {
    public static List<Instituicao> LInstituicoes(){
        return Arrays.asList(
                Instituicao.InstituicaoBuilder.builder()
                        .setID(1)
                        .setNome("Fórum Viseu")
                        .setEndereco("R. Dom José da Cruz Moreira Pinto 32")
                        .setEstado(0)
                        .setLatitude(40.66227845781562)
                        .setLongitude(-7.914157332762673)
                        .setU_Reporte("2021-06-19 11:30:08.597+00")
                        .build(),
                Instituicao.InstituicaoBuilder.builder()
                        .setID(3)
                        .setNome("Pingo Doce Viseu")
                        .setEndereco("R. Mendonça, 3510-156 Viseu")
                        .setEstado(1)
                        .setLatitude(40.653001370705255)
                        .setLongitude(-7.913500948176402)
                        .setU_Reporte("2021-06-20 11:30:08.597+00")
                        .build(),
                Instituicao.InstituicaoBuilder.builder()
                        .setID(4)
                        .setNome("Palácio do Gelo Shopping")
                        .setEndereco("Rua Palácio do Gelo nº 3, Viseu")
                        .setEstado(2)
                        .setLatitude(40.643830957712034)
                        .setLongitude(-7.911176715568483)
                        .setU_Reporte("2021-06-30 11:30:08.597+00")
                        .build(),
                Instituicao.InstituicaoBuilder.builder()
                        .setID(8)
                        .setNome("Hospital São Mateus SA")
                        .setEndereco("R. 5 de Outubro")
                        .setEstado(3)
                        .setLatitude(40.65639407092335)
                        .setLongitude(-7.905100877143841)
                        .setU_Reporte("2021-06-30 11:30:08.597+00")
                        .build()
        );
    }
}
