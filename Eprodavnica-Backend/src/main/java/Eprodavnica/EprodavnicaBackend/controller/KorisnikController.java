package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.KorisnikDTO;
import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProdavacDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.mapper.KorisnikMapper;
import Eprodavnica.EprodavnicaBackend.mapper.MusterijaMapper;
import Eprodavnica.EprodavnicaBackend.mapper.ProdavacMapper;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.service.CustomUserDetailsService;
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

import java.util.List;

@RestController
@RequestMapping(value = "/korisnik", produces = MediaType.APPLICATION_JSON_VALUE)
public class KorisnikController {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final MusterijaMapper musterijaMapper;
    private final ProdavacMapper prodavacMapper;
    private final KorisnikMapper korisnikMapper;

    @PreAuthorize("hasAuthority('OPERACIJE_SA_MUSTERIJOM')")
    @GetMapping("/dajMusteriju")
    public ResponseEntity<MusterijaDTO>dajMusteriju(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        Korisnik korisnik = userDetailsService.dajKorisnika(u.getEmail());
        MusterijaDTO musterijaDTO = musterijaMapper.toDto(korisnik);
        return new ResponseEntity<>(musterijaDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_PRODAVCEM')")
    @GetMapping("/dajProdavac")
    public ResponseEntity<ProdavacDTO>dajProdavac(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        Korisnik korisnik = userDetailsService.dajKorisnika(u.getEmail());
        ProdavacDTO prodavacDTO = prodavacMapper.toDto(korisnik);
        return new ResponseEntity<>(prodavacDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @GetMapping("/dajAdmin")
    public ResponseEntity<KorisnikDTO>dajAdmin(){
        Korisnik korisnik = userDetailsService.dajKorisnika(TrenutnoUlogovanKorisnik());
        KorisnikDTO korisnikDTO = korisnikMapper.toDto(korisnik);
        return new ResponseEntity<>(korisnikDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_PRODAVCEM','OPERACIJE_SA_MUSTERIJOM')")
    @PutMapping("/update")
    public ResponseEntity<?>update(@RequestBody KorisnikDTO korisnikDTO){
        userDetailsService.update(korisnikMapper.toModel(korisnikDTO),TrenutnoUlogovanKorisnik());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @GetMapping("/by-pageMusterija")
    public ResponseEntity<Page<KorisnikDTO>>byPageMusterija(Pageable pageable){
        Page<Korisnik>page = userDetailsService.findAllMusterija(pageable);
        List<KorisnikDTO> lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @GetMapping("/by-pageProdavac")
    public ResponseEntity<Page<KorisnikDTO>>byPageProdavac(Pageable pageable){
        Page<Korisnik>page = userDetailsService.findAllProdavac(pageable);
        List<KorisnikDTO> lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_SUPERADMINOM')")
    @GetMapping("/by-pageAdmin")
    public ResponseEntity<Page<KorisnikDTO>>byPageAdmin(Pageable pageable){
        Page<Korisnik>page = userDetailsService.findAllAdmin(pageable);
        List<KorisnikDTO> lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @PostMapping("/filter-by-pageMusterija")
    public ResponseEntity<Page<KorisnikDTO>>filterMusterija(@RequestBody KorisnikDTO korisnikDTO,Pageable pageable){
        Page<Korisnik>page = userDetailsService.filterAllMusterija(korisnikDTO,pageable);
        List<KorisnikDTO>lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_ADMINOM')")
    @PostMapping("/filter-by-pageProdavac")
    public ResponseEntity<Page<KorisnikDTO>>filterProdavac(@RequestBody KorisnikDTO korisnikDTO,Pageable pageable){
        Page<Korisnik>page = userDetailsService.filterAllProdavac(korisnikDTO,pageable);
        List<KorisnikDTO>lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_SUPERADMINOM')")
    @PostMapping("/filter-by-pageAdmin")
    public ResponseEntity<Page<KorisnikDTO>>filterAdmin(@RequestBody KorisnikDTO korisnikDTO,Pageable pageable){
        Page<Korisnik>page = userDetailsService.filterAllAdmin(korisnikDTO,pageable);
        List<KorisnikDTO>lista = korisnikMapper.toDtoKorisnikLista(page.toList());
        Page<KorisnikDTO> dtos = new PageImpl<>(lista,page.getPageable(),page.getTotalElements());
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_SUPERADMINOM')")
    @PutMapping("/povuciKorisnika")
    public ResponseEntity<?>povuciKorisnika(@RequestBody String email){
        userDetailsService.povuciKorisnika(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OPERACIJE_SA_ADMINOM','OPERACIJE_SA_SUPERADMINOM')")
    @PutMapping("/vratiKorisnika")
    public ResponseEntity<?>vratiKorisnika(@RequestBody String email){
        userDetailsService.vratiKorisnika(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OPERACIJE_SA_SUPERADMINOM')")
    @PostMapping("/pravljenjeAdmina")
    public ResponseEntity<?>pravljenjeAdmina(@RequestBody MusterijaDTO admin){
        userDetailsService.pravljenjeAdminNaloga(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public KorisnikController() {
        this.musterijaMapper = new MusterijaMapper();
        this.prodavacMapper = new ProdavacMapper();
        this.korisnikMapper = new KorisnikMapper();
    }

    public static String TrenutnoUlogovanKorisnik(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        return u.getEmail();
    }
}
