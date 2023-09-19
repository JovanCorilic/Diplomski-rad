package Eprodavnica.EprodavnicaBackend.controller;


import Eprodavnica.EprodavnicaBackend.dto.ArtikalDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;

import Eprodavnica.EprodavnicaBackend.mapper.ArtikalMapper;
import Eprodavnica.EprodavnicaBackend.mapper.RacunMapper;

import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.service.RacunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static Eprodavnica.EprodavnicaBackend.controller.KorisnikController.TrenutnoUlogovanKorisnik;

@RestController
@RequestMapping(value = "/racun", produces = MediaType.APPLICATION_JSON_VALUE)
public class RacunController {
    @Autowired
    private RacunService racunService;
    private final RacunMapper racunMapper;
    private final ArtikalMapper artikalMapper;

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/create")
    public ResponseEntity<?> createProdukt(@RequestBody RacunDTO racunDTO){
        Racun racun = racunMapper.toModel(racunDTO);
        racunService.create(racun);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/dodajArtikal")
    public ResponseEntity<?>dodajArtikal(@RequestBody ArtikalDTO artikalDTO){
        Artikal artikal = artikalMapper.toModel(artikalDTO);
        racunService.dodajArtikal(artikal,TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProdukt(@RequestBody RacunDTO racunDTO , @PathVariable String id){
        Racun racun = racunMapper.toModel(racunDTO);
        racunService.update(racun,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/get/{brojRacuna}")
    public ResponseEntity<?>get(@PathVariable String brojRacuna){
        RacunDTO racunDTO = racunMapper.toDto(racunService.findOne(brojRacuna));
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

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/by-pageMusterija")
    public ResponseEntity<Page<RacunDTO>>getPageableMusterija(Pageable pageable){
        Page<Racun>page = racunService.getAllMusterija(pageable,TrenutnoUlogovanKorisnik());
        List<RacunDTO>lista = racunMapper.toDtoLista(page.toList());
        Page<RacunDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @GetMapping("/by-pageAdmin")
    public ResponseEntity<Page<RacunDTO>>getPageableAdmin(Pageable pageable){
        Page<Racun>page = racunService.getAllAdmin(pageable);
        List<RacunDTO>lista = racunMapper.toDtoLista(page.toList());
        Page<RacunDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/filter-by-pageMusterija")
    public ResponseEntity<Page<RacunDTO>>getFilterMusterijaPageable(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Racun>page = racunService.filterMusterija(filterDTO,pageable,TrenutnoUlogovanKorisnik());
        List<RacunDTO>lista = racunMapper.toDtoLista(page.toList());
        Page<RacunDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @PostMapping("/filter-by-pageAdmin")
    public ResponseEntity<Page<RacunDTO>>getFilterAdminPageable(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Racun>page = racunService.filterAdmin(filterDTO,pageable);
        List<RacunDTO>lista = racunMapper.toDtoLista(page.toList());
        Page<RacunDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/by-pageArtikal/{brojRacuna}")
    public ResponseEntity<Page<ArtikalDTO>>getPageableAdmin(Pageable pageable,@PathVariable String brojRacuna){
        Page<Artikal>page = racunService.getAllArtikalPageable(brojRacuna,pageable);
        List<ArtikalDTO>lista = artikalMapper.toDtoArtikal(page.toList());
        Page<ArtikalDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/dajAktivanRacun")
    public ResponseEntity<RacunDTO>dajAktivanRacun(){
        Racun racun = racunService.dajAktivanRacun(TrenutnoUlogovanKorisnik());
        if (racun == null){
            racun = new Racun();
            racun.setBrojRacuna("nema");
            racun.setMusterija(new Korisnik());
        }

        RacunDTO racunDTO = racunMapper.toDto(racun);

        return new ResponseEntity<>(racunDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @DeleteMapping("/ukloniArtikal/{id}")
    public ResponseEntity<?>ukloniArtikal(@PathVariable Integer id){
        racunService.deleteArtikal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PutMapping("/plati")
    public ResponseEntity<?>plati(@RequestBody String brojRacuna){
        racunService.plati(brojRacuna,TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public RacunController() {
        this.racunMapper = new RacunMapper();
        this.artikalMapper = new ArtikalMapper();
    }
}
