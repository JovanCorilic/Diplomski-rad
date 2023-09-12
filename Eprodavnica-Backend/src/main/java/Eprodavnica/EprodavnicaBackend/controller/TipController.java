package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.mapper.RacunMapper;
import Eprodavnica.EprodavnicaBackend.mapper.TipMapper;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        try {
            tipService.create(tip);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{naziv}")
    public ResponseEntity<?>updateProdukt(@RequestBody TipDTO tipDTO , @PathVariable String naziv){
        Tip tip = tipMapper.toModel(tipDTO);
        try{
            tipService.update(tip,naziv);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/by-page")
    public ResponseEntity<Page<TipDTO>>getTipPageable(Pageable pageable){
        Page<Tip>page = tipService.findAllPageable(pageable);
        List<TipDTO>lista = tipMapper.toDTOLista(page.toList());
        Page<TipDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PostMapping("/filter-by-page")
    public ResponseEntity<Page<TipDTO>>getTipFilter(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Tip>page = tipService.filterAllPageable(pageable,filterDTO);
        List<TipDTO>lista = tipMapper.toDTOLista(page.toList());
        Page<TipDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    public TipController() {
        this.tipMapper = new TipMapper();
    }
}
