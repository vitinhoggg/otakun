package com.otakun;

import com.otakun.dao.AnimeDao;
import com.otakun.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.otakun.dao")
@EntityScan (basePackages = "com.otakun.model")
public class BasicApplication implements CommandLineRunner {
    @Autowired
    private AnimeDao repositorio;

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorio);
        System.out.println( principal.getAnimes() );
        
        principal.exibeMenu();
    }

}
