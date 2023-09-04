package Eprodavnica.EprodavnicaBackend.controller;
import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.RecenzijaDTO;
import Eprodavnica.EprodavnicaBackend.mapper.RecenzijaMapper;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.service.RecenzijaService;
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

import static Eprodavnica.EprodavnicaBackend.controller.KorisnikController.TrenutnoUlogovanKorisnik;

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

    @GetMapping("/by-page/{serijskiBroj}")
    public ResponseEntity<Page<RecenzijaDTO>>getRecenzijaPageable(@PathVariable String serijskiBroj, Pageable pageable){
        Page<Recenzija>page = recenzijaService.findAllPageable(pageable,serijskiBroj);
        List<RecenzijaDTO>lista = recenzijaMapper.uListuDTO(page.toList());
        Page<RecenzijaDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping("/by-pageMusterija")
    public ResponseEntity<Page<RecenzijaDTO>>getRecenzijaPageableMusterija(Pageable pageable){
        Page<Recenzija>page = recenzijaService.findAllMusterijaPageable(pageable,TrenutnoUlogovanKorisnik());
        List<RecenzijaDTO>lista = recenzijaMapper.uListuDTO(page.toList());
        Page<RecenzijaDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PostMapping("/filter-by-page/{serijskiBroj}")
    public ResponseEntity<Page<RecenzijaDTO>>getProduktFilter(@RequestBody FilterDTO filterDTO,
                                                                @PathVariable String  serijskiBroj,Pageable pageable){
        Page<Recenzija>page = recenzijaService.filterPageable(pageable,serijskiBroj,filterDTO);
        List<RecenzijaDTO>lista = recenzijaMapper.uListuDTO(page.toList());
        Page<RecenzijaDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PostMapping("/filter-by-pageMusterija")
    public ResponseEntity<Page<RecenzijaDTO>>getProduktFilterMusterija(@RequestBody FilterDTO filterDTO,Pageable pageable){
        Page<Recenzija>page = recenzijaService.filterPageable(pageable,TrenutnoUlogovanKorisnik(),filterDTO);
        List<RecenzijaDTO>lista = recenzijaMapper.uListuDTO(page.toList());
        Page<RecenzijaDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    public RecenzijaController() {
        this.recenzijaMapper = new RecenzijaMapper();
    }
}
