package com.example.crowdzero.models;

import java.util.Arrays;
import java.util.List;

public class ListaConquistas {
    public static List<Conquista> LConquistas(){
        return Arrays.asList(
                Conquista.ConquistaBuilder.builder()
                        .setNome("3")
                        .setNPontos("10")
                        .setFeito(false)
                        .build(),

                Conquista.ConquistaBuilder.builder()
                        .setNome("4")
                        .setNPontos("30")
                        .setFeito(false)
                        .build(),

                Conquista.ConquistaBuilder.builder()
                        .setNome("5")
                        .setNPontos("40")
                        .setFeito(false)
                        .build(),
                Conquista.ConquistaBuilder.builder()
                        .setNome("6")
                        .setNPontos("50")
                        .setFeito(false)
                        .build(),
                Conquista.ConquistaBuilder.builder()
                        .setNome("7")
                        .setNPontos("60")
                        .setFeito(false)
                        .build(),
                Conquista.ConquistaBuilder.builder()
                        .setNome("8")
                        .setNPontos("70")
                        .setFeito(false)
                        .build()
        );
    }
}
