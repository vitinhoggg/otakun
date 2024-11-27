/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.otakun.principal;

import com.otakun.dados.DadosAtributo;
import com.otakun.dados.DadosData;
import com.otakun.dao.AnimeDao;
import com.otakun.model.Anime;
import com.otakun.service.ConsumoApi;
import com.otakun.service.ConverteDados;
import java.util.Scanner;

/**
 *
 * @author Pichau
 */
public class Principal {

    public static AnimeDao REPO;
    private final String ENDERECO = "https://kitsu.io/api/edge/anime?filter[text]=";
    private Scanner leitura = new Scanner(System.in);
    private AnimeDao repositorio;

    public Principal(AnimeDao repositorio) {
        this.repositorio = repositorio;
        Principal.REPO = repositorio;
    }

    public String getAnimes() {
        ConsumoApi consumo = new ConsumoApi();
        var json = consumo.obterDados(ENDERECO + "konosuba");
        return json;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar animes
                2 - Listar animes buscados
                3 - Buscar anime por titulo           
                0 - Sair
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscarSalvarAnimeWeb();
                    break;

                case 2:
                    listarAnimesBuscados();
                    break;

                case 3:
                    buscarAnimePorTitulo();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }
    }

    private void buscarSalvarAnimeWeb() {
        DadosAtributo dados = getDadosAnime();
        Anime anime = new Anime(dados);
        this.repositorio.save(anime);
    }

    private void listarAnimesBuscados() {
        var animes = repositorio.findAll();
        animes.stream()
        .forEach(System.out::println);
    }
private void buscarAnimePorTitulo() {
    System.out.println("Escolha um anime pelo nome: ");
    var nomeAnime = leitura.nextLine();
    var AnimeBusca = repositorio.findByTituloContainingIgnoreCase(nomeAnime);
    
    if (AnimeBusca .isPresent()) {
        System.out.println("Dados da Anime: " + AnimeBusca.get());
    } else {      
        System.out.println("Anime nao encontrado!");
    }
}

    private DadosAtributo getDadosAnime() {
        System.out.println("Digite o nome do anime para busca");
        var nomeAnime = leitura.nextLine();

        ConsumoApi consumo = new ConsumoApi();
        String json = consumo.obterDados(ENDERECO + nomeAnime.replace(" ", "+"));

        ConverteDados conversor = new ConverteDados();
        DadosData dados = conversor.obterDados(json, DadosData.class);

        DadosAtributo dadostributo = dados.data().get(0).atributos();
        return dadostributo;

    }
}
