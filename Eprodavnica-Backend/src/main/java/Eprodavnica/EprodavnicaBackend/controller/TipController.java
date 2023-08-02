package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.mapper.RacunMapper;
import Eprodavnica.EprodavnicaBackend.mapper.TipMapper;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tip", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipController {
    @Autowired
    private TipService tipService;

    private final TipMapper tipMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createProdukt(@RequestBody TipDTO tipDTO){
        Tip tip = tipMapper.toModel(tipDTO);
        tipService.create(tip);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProdukt(@RequestBody TipDTO tipDTO , @PathVariable String id){
        Tip tip = tipMapper.toModel(tipDTO);
        tipService.update(tip,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>get(@PathVariable String id){
        TipDTO tipDTO = tipMapper.toDto(tipService.findOne(id));
        return new ResponseEntity<>(tipDTO, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        List<TipDTO> lista = new ArrayList<>();
        List<Tip>temp = tipService.findAll();
        for (Tip tip : temp){
            lista.add(tipMapper.toDto(tip));
        }
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    public TipController() {
        this.tipMapper = new TipMapper();
    }
}
