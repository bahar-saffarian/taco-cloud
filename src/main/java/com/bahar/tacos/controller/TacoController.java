package com.bahar.tacos.controller;

import com.bahar.tacos.model.Taco;
import com.bahar.tacos.repository.TacoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://taco-cloud:8080")
public class TacoController {

    private final TacoRepository repository;

    public TacoController(TacoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = repository.findById(id);

        if (optTaco.isPresent())
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("#{hasRole('ADMIN')}")
    public Taco postTaco(@RequestBody Taco taco) {
        return repository.save(taco);
    }

    @PutMapping(path = "/tacoId", consumes = "application/json")
    public Taco putTaco(@PathVariable("tacoId") Long id, @RequestBody Taco taco) {
        taco.setId(id);
        return repository.save(taco);
    }

    @PatchMapping(path = "/tacoId", consumes = "application/json")
    public ResponseEntity<Taco> patchTaco(@PathVariable("tacoId") Long id, @RequestBody Taco patch) {
        Optional<Taco> optTaco = repository.findById(id);
        if (optTaco.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Taco taco = optTaco.get();
        if (StringUtils.hasText(patch.getName()))
            taco.setName(patch.getName());
        return new ResponseEntity<>(repository.save(taco), HttpStatus.OK);
    }

    @DeleteMapping("/{tacoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("#{hasAnyAuthority('ROLE_ADMIN')}")
    public void deleteTaco(@PathVariable("tacoId") Long tacoId) {
        try {
            repository.deleteById(tacoId);
        } catch (EmptyResultDataAccessException e) {}
    }
}
