package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController

public class KoalaController {


    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
        // İsterseniz başlangıç verisi ekleyebilirsiniz:
        koalas.put(1, new Koala(1, "Koby", 12.5, 16, "Male"));
    }

    // [GET]/workintech/koalas => tüm koala listini dönmeli
    @GetMapping("/koalas")
    public ResponseEntity<Collection<Koala>> getAllKoalas() {
        return ResponseEntity.ok(koalas.values());
    }

    // [GET]/workintech/koalas/{id} => İlgili id deki koala objesini dönmeli
    @GetMapping("/koalas/{id}")
    public ResponseEntity<Koala> getKoalaById(@PathVariable int id) {
        Koala koala = koalas.get(id);
        if (koala != null) {
            return ResponseEntity.ok(koala);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // [POST]/workintech/koalas => Bir adet koala objesini koala listesine ekler
    @PostMapping("/koalas")
    public ResponseEntity<Koala> createKoala(@RequestBody Koala newKoala) {
        if (koalas.containsKey(newKoala.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        koalas.put(newKoala.getId(), newKoala);
        return ResponseEntity.status(HttpStatus.CREATED).body(newKoala);
    }
}
// [PUT]/workintech/koalas/{id} => İlgili id deki koala objesini günceller
