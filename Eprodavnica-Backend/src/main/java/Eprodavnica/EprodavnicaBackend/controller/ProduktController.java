package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.mapper.ProduktMapper;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.service.ProduktService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/produkt", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProduktController {
    @Autowired
    private ProduktService produktService;

    private final ProduktMapper produktMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createProdukt(@RequestBody ProduktDTO produktDTO){
        Produkt produkt = produktMapper.toModel(produktDTO);
        produktService.create(produkt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProdukt(@RequestBody ProduktDTO produktDTO , @PathVariable String id){
        Produkt produkt = produktMapper.toModel(produktDTO);
        produktService.update(produkt,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>get(@PathVariable String id){
        ProduktDTO produktDTO = produktMapper.toDto(produktService.findOne(id));
        return new ResponseEntity<>(produktDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<ProduktDTO>lista = new ArrayList<>();
        List<Produkt>temp = produktService.findAll();
        for (Produkt produkt : temp){
            lista.add(produktMapper.toDto(produkt));
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    public ProduktController() {
        this.produktMapper = new ProduktMapper();
    }
}
