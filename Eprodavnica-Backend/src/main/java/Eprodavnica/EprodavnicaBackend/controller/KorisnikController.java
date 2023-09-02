package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProdavacDTO;
import Eprodavnica.EprodavnicaBackend.mapper.MusterijaMapper;
import Eprodavnica.EprodavnicaBackend.mapper.ProdavacMapper;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/korisnik", produces = MediaType.APPLICATION_JSON_VALUE)
public class KorisnikController {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final MusterijaMapper musterijaMapper;
    private final ProdavacMapper prodavacMapper;

    @GetMapping("/dajMusteriju")
    public ResponseEntity<MusterijaDTO>dajMusteriju(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        Korisnik korisnik = userDetailsService.dajKorisnika(u.getEmail());
        MusterijaDTO musterijaDTO = musterijaMapper.toDto(korisnik);
        return new ResponseEntity<>(musterijaDTO, HttpStatus.OK);
    }

    @GetMapping("/dajProdavac")
    public ResponseEntity<ProdavacDTO>dajProdavac(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        Korisnik korisnik = userDetailsService.dajKorisnika(u.getEmail());
        ProdavacDTO prodavacDTO = prodavacMapper.toDto(korisnik);
        return new ResponseEntity<>(prodavacDTO,HttpStatus.OK);
    }

    public KorisnikController() {
        this.musterijaMapper = new MusterijaMapper();
        this.prodavacMapper = new ProdavacMapper();
    }

    public static String TrenutnoUlogovanKorisnik(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Korisnik u=(Korisnik)auth.getPrincipal();
        return u.getEmail();
    }
}
