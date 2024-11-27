/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.otakun.service;

import com.otakun.dto.AnimeDto;
import com.otakun.model.Anime;
import com.otakun.principal.Principal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pichau
 */

@Service
public class AnimeService {
    
    public List<AnimeDto> obterTodosOsAnimes(){
        List<Anime> animes  = Principal.REPO.findAll();
        System.out.println(animes);
        return converteDados(animes);
    }
    
    private List<AnimeDto> converteDados(List<Anime> anime) {
        return anime.stream()
            .map(a -> new AnimeDto(a.getId(), a.getTitulo(), a.getDescricao(), a.getPoster(), a.getTotalEpisodios()))
            .collect(Collectors.toList());
    }
}
