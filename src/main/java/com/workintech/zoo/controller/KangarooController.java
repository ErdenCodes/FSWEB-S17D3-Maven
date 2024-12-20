package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController

public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
        kangaroos.put(1, new Kangaroo(1, "Jack", 1.5, 70.0, "Male", false));
    }

    // [GET]/workintech/kangaroos => Tüm kanguroo listesi
    @GetMapping("/kangaroos")
    public ResponseEntity<Collection<Kangaroo>> getAllKangaroos() {
        return ResponseEntity.ok(kangaroos.values());
    }

    // [GET]/workintech/kangaroos/{id} => Belirli id'deki kangaroo
    @GetMapping("/kangaroos/{id}")
    public ResponseEntity<Kangaroo> getKangarooById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo != null) {
            return ResponseEntity.ok(kangaroo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // [POST]/workintech/kangaroos => Yeni bir kangaroo ekleme
    @PostMapping("/kangaroos")
    public ResponseEntity<Kangaroo> createKangaroo(@RequestBody Kangaroo newKangaroo) {
        if (kangaroos.containsKey(newKangaroo.getId())) {
            // Aynı id'ye sahip bir kangaroo zaten varsa conflict döneriz
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        kangaroos.put(newKangaroo.getId(), newKangaroo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newKangaroo);
    }

    // [PUT]/workintech/kangaroos/{id} => Var olan kangaroo'yu güncelleme
    @PutMapping("/kangaroos/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable int id, @RequestBody Kangaroo updatedKangaroo) {
        if (kangaroos.containsKey(id)) {
            // Mevcut kaydı güncelliyoruz
            updatedKangaroo.setId(id);
            kangaroos.put(id, updatedKangaroo);
            return ResponseEntity.ok(updatedKangaroo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // [DELETE]/workintech/kangaroos/{id} => Belirli id'deki kangaroo'yu silme
    @DeleteMapping("/kangaroos/{id}")
    public ResponseEntity<Void> deleteKangaroo(@PathVariable int id) {
        if (kangaroos.containsKey(id)) {
            kangaroos.remove(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
