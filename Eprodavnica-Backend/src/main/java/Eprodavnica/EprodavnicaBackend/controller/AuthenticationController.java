package Eprodavnica.EprodavnicaBackend.controller;

import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.RegistracijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.UserLoginDTO;
import Eprodavnica.EprodavnicaBackend.dto.UserTokenStateDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.security.TokenUtils;
import Eprodavnica.EprodavnicaBackend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/log-in")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO authenticationRequest,
                                                       HttpServletResponse response) throws InterruptedException {
        if (!userDetailsService.daLiJeOdobrenOdAdmina(authenticationRequest.getUsername())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        Korisnik user = (Korisnik) authentication.getPrincipal();
        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(user.getEmail(),user.getUloge().get(0).getAuthority(),fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        // Kreiraj cookie
        // String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";  // kasnije mozete probati da postavite i ostale atribute, ali tek nakon sto podesite https
        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok().headers(headers).body(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PreAuthorize("hasAuthority('LOGOUT')")
    @GetMapping( "/log-out")
    public ResponseEntity<?> logoutUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?>registracijaMusterije(@RequestBody RegistracijaDTO registracijaDTO){
        if (userDetailsService.daLiSeVecKoristiEmail(registracijaDTO.getEmail()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userDetailsService.register(registracijaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verifikacijaRegistracijaMusterija/{token}")
    public ResponseEntity<?>VerifikacijaRegistracije(@PathVariable String token) throws Exception {
        userDetailsService.potvrdaNaloga(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verifikacijaAdminNalog/{token}")
    public ResponseEntity<?>VerifikacijaAdminNaloga(@PathVariable String token) throws Exception {
        userDetailsService.potvrdaNaloga(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public AuthenticationController() {
    }
}
