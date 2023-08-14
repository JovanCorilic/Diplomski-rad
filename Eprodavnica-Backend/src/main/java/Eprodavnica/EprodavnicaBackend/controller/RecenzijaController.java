package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.RecenzijaDTO;
import Eprodavnica.EprodavnicaBackend.mapper.ProduktMapper;
import Eprodavnica.EprodavnicaBackend.mapper.RecenzijaMapper;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/recenzija", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecenzijaController {
    @Autowired
    private RecenzijaService recenzijaService;

    private final RecenzijaMapper recenzijaMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createProdukt(@RequestBody RecenzijaDTO recenzijaDTO){
        Recenzija recenzija = recenzijaMapper.toModel(recenzijaDTO);
        recenzijaService.create(recenzija);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProdukt(@RequestBody RecenzijaDTO recenzijaDTO , @PathVariable String id){
        Recenzija recenzija = recenzijaMapper.toModel(recenzijaDTO);
        recenzijaService.update(recenzija,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>get(@PathVariable String id){
        RecenzijaDTO recenzijaDTO = recenzijaMapper.toDto(recenzijaService.findOne(id));
        return new ResponseEntity<>(recenzijaDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<RecenzijaDTO> lista = new ArrayList<>();
        List<Recenzija>temp = recenzijaService.findAll();
        for (Recenzija recenzija : temp){
            lista.add(recenzijaMapper.toDto(recenzija));
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    public RecenzijaController() {
        this.recenzijaMapper = new RecenzijaMapper();
    }
}
