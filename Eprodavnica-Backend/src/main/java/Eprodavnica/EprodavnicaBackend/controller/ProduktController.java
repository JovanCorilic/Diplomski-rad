package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
import Eprodavnica.EprodavnicaBackend.mapper.ProduktMapper;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.service.ProduktService;
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

    @GetMapping("/get/{serijskiBroj}")
    public ResponseEntity<?>get(@PathVariable String serijskiBroj){
        ProduktDTO produktDTO = produktMapper.toDto(produktService.findOne(serijskiBroj));
        return new ResponseEntity<>(produktDTO, HttpStatus.OK);
    }

    @GetMapping("/by-page")
    public ResponseEntity<Page<ProduktMiniDTO>>getProduktPageable(Pageable pageable){
        Page<Produkt>page = produktService.findAllPageable(pageable);
        List<ProduktMiniDTO>lista = produktMapper.toDTOListaMiniProdukt(page.toList());
        Page<ProduktMiniDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PostMapping("/filter-by-page")
    public ResponseEntity<Page<ProduktMiniDTO>>getProduktFilter(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Produkt>page = produktService.filterPageable(filterDTO,pageable);
        List<ProduktMiniDTO>lista = produktMapper.toDTOListaMiniProdukt(page.toList());
        Page<ProduktMiniDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
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
