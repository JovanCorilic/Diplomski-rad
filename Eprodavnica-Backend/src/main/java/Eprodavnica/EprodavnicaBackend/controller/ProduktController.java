package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.ImageModel;
import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
import Eprodavnica.EprodavnicaBackend.mapper.ProduktMapper;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.service.ProduktService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Eprodavnica.EprodavnicaBackend.controller.KorisnikController.TrenutnoUlogovanKorisnik;

@RestController
@RequestMapping(value = "/produkt", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProduktController {
    @Autowired
    private ProduktService produktService;

    private final ProduktMapper produktMapper;

    //moze i @RequestParam

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduktDTO> createProdukt(@RequestPart("File") MultipartFile file, @RequestPart("produkt")ProduktDTO produktDTO) throws IOException {
        if (file.getOriginalFilename().equals("nema")){
            produktDTO.setSlika(new ImageModel());
            produktDTO.getSlika().setName("nema");
        }else{
            produktDTO.getSlika().setPicByte(file.getBytes());
        }
        Produkt produkt = produktMapper.toModel(produktDTO);
        Produkt produkt1 = produktService.create(produkt, produktDTO.getSlika(),TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(produktMapper.toDto(produkt1),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/dodajuWishlist")
    public ResponseEntity<?> dodajUWishlist(@RequestBody String serijskiBroj){
        produktService.dodajUWishlist(TrenutnoUlogovanKorisnik(),serijskiBroj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/daLiJeUWishlist/{serijskiBroj}")
    public ResponseEntity<Boolean>daLiJeUWishlist(@PathVariable String serijskiBroj){
        Boolean temp=produktService.daLiJeUWishlist(TrenutnoUlogovanKorisnik(),serijskiBroj);
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @PutMapping(value = "/update/{serijskiBroj}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduktDTO>updateProdukt(@RequestPart("File") MultipartFile file,
                                                   @RequestPart("produkt")ProduktDTO produktDTO ,
                                                   @PathVariable String serijskiBroj) throws IOException {
        if (file.getOriginalFilename().equals("nema")){
            produktDTO.setSlika(new ImageModel());
            produktDTO.getSlika().setName("nema");
        }else{
            produktDTO.getSlika().setPicByte(file.getBytes());
        }
        Produkt produkt = produktMapper.toModel(produktDTO);
        produkt = produktService.update(produkt,serijskiBroj,produktDTO.getSlika());
        return new ResponseEntity<>(produktMapper.toDto(produkt),HttpStatus.OK);
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

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/by-pageIstorijaProdukata")
    public ResponseEntity<Page<ProduktDTO>>getIstorijaProdukataPageable(Pageable pageable){
        Page<Produkt>page = produktService.findByIstorijaProdukataPageable(pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/by-pageWishlist")
    public ResponseEntity<Page<ProduktDTO>>getWishlistPageable(Pageable pageable){
        Page<Produkt>page = produktService.findByWishlist(pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @GetMapping("/by-pageProdavac")
    public ResponseEntity<Page<ProduktDTO>>getProdavacPageable(Pageable pageable){
        Page<Produkt>page = produktService.findByProdavac(pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PostMapping("/filter-by-page")
    public ResponseEntity<Page<ProduktMiniDTO>>getProduktFilter(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Produkt>page = produktService.filterPageable(filterDTO,pageable);
        List<ProduktMiniDTO>lista = produktMapper.toDTOListaMiniProdukt(page.toList());
        Page<ProduktMiniDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/filter-by-pageIstorijaProdukata")
    public ResponseEntity<Page<ProduktDTO>>getProduktFilterPageIstorijaProdukata(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Produkt>page = produktService.filterPageableIstorijaProdukata(filterDTO,pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/filter-by-pageWishlist")
    public ResponseEntity<Page<ProduktDTO>>getProduktFilterPageWishlist(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Produkt>page = produktService.filterPageableWishlist(filterDTO,pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @GetMapping("/filter-by-pageProdavac")
    public ResponseEntity<Page<ProduktDTO>>getProduktFilterPageProdavac(@RequestBody FilterDTO filterDTO, Pageable pageable){
        Page<Produkt>page = produktService.filterPageableProdavac(filterDTO,pageable,TrenutnoUlogovanKorisnik());
        List<ProduktDTO>lista = produktMapper.toDTOListProdukt(page.toList());
        Page<ProduktDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
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

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @PostMapping("/izbaciIzWishlista")
    public ResponseEntity<?>izbaciIzWishlista(@RequestBody String serijskiBroj){
        produktService.izbaciIzWishlista(serijskiBroj,TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/daLiJeUIstorijiProdukata/{serijskiBroj}")
    public ResponseEntity<Boolean>daLiJeUIstorijiProdukata(@PathVariable String serijskiBroj){
        Boolean odgovor = produktService.daLiJeUIstorijiProdukata(serijskiBroj,TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(odgovor,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_PRODAVCEM')")
    @PutMapping("/povuciProdukt")
    public ResponseEntity<?>povuciProdukt(@RequestBody String serijskiBroj){
        produktService.povuciProizvod(serijskiBroj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_PRODAVCEM')")
    @PutMapping("/vratiProdukt")
    public ResponseEntity<?>vratiProdukt(@RequestBody String serijskiBroj){
        produktService.vratiProizvod(serijskiBroj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @PutMapping("/dodajAkciju/{broj}")
    public ResponseEntity<?>dodajAkciju(@RequestBody String serijskiBroj, @PathVariable Integer broj){
        produktService.dodajAkciju(serijskiBroj,broj);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ProduktController() {
        this.produktMapper = new ProduktMapper();
    }
}
