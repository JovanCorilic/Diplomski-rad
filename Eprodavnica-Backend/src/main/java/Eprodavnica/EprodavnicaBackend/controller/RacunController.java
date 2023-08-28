package Eprodavnica.EprodavnicaBackend.controller;


import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;

import Eprodavnica.EprodavnicaBackend.mapper.RacunMapper;

import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.service.RacunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/racun", produces = MediaType.APPLICATION_JSON_VALUE)
public class RacunController {
    @Autowired
    private RacunService racunService;

    private final RacunMapper racunMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createProdukt(@RequestBody RacunDTO racunDTO){
        Racun racun = racunMapper.toModel(racunDTO);
        racunService.create(racun);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dodajArtikal")
    public ResponseEntity<?>dodajArtikal(@RequestBody Artikal artikal){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        racunService.dodajArtikal(artikal,u.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProdukt(@RequestBody RacunDTO racunDTO , @PathVariable String id){
        Racun racun = racunMapper.toModel(racunDTO);
        racunService.update(racun,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>get(@PathVariable String id){
        RacunDTO racunDTO = racunMapper.toDto(racunService.findOne(id));
        return new ResponseEntity<>(racunDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<RacunDTO> lista = new ArrayList<>();
        List<Racun>temp = racunService.findAll();
        for (Racun racun : temp){
            lista.add(racunMapper.toDto(racun));
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    public RacunController() {
        this.racunMapper = new RacunMapper();
    }
}
