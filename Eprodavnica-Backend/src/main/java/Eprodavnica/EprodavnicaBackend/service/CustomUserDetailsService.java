package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.KorisnikDTO;
import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.RegistracijaDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Uloga;
import Eprodavnica.EprodavnicaBackend.model.VerificationToken;
import Eprodavnica.EprodavnicaBackend.other.KonverterDatum;
import Eprodavnica.EprodavnicaBackend.other.ObavestenjeEmail;
import Eprodavnica.EprodavnicaBackend.other.VerifikacioniTokenSlanjeEmail;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.UlogaRepository;
import Eprodavnica.EprodavnicaBackend.repository.VerificationTokenRepository;
import Eprodavnica.EprodavnicaBackend.security.password.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private KorisnikRepository userRepository;
    @Autowired
    private UlogaRepository ulogaRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private ProduktRepository produktRepository;

    private final CustomPasswordEncoder customPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            if (!user.isOdobrenOdAdmina() || !user.isPotvrdjen())
                throw new UsernameNotFoundException(String.format("Korisnik nije odobren od strane admina '%s'.", email));
            return user;
        }
    }

    public void update(Korisnik korisnik,String email){
        Korisnik user = userRepository.findByEmail(email);
        user.setIme(korisnik.getIme());
        user.setPrezime(korisnik.getPrezime());
        userRepository.save(user);
    }

    public Page<Korisnik>findAllMusterija(Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_MUSTERIJA");
        return userRepository.findByUlogeContainingAndPotvrdjenIsTrueOrderByEmailAsc(uloga,pageable);
    }

    public Page<Korisnik>findAllProdavac(Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_PRODAVAC");
        return userRepository.findByUlogeContainingAndPotvrdjenIsTrueOrderByEmailAsc(uloga,pageable);
    }

    public Page<Korisnik>findAllAdmin(Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_ADMIN");
        return userRepository.findByUlogeContainingAndPotvrdjenIsTrueOrderByEmailAsc(uloga,pageable);
    }

    public Page<Korisnik>filterAllMusterija(KorisnikDTO korisnikDTO,Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_MUSTERIJA");
        return userRepository.findByCustomCriteria(korisnikDTO,uloga,pageable);
    }

    public Page<Korisnik>filterAllProdavac(KorisnikDTO korisnikDTO,Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_PRODAVAC");
        return userRepository.findByCustomCriteria(korisnikDTO,uloga,pageable);
    }

    public void povuciKorisnika(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        korisnik.setOdobrenOdAdmina(false);
        userRepository.save(korisnik);

        String text = "Vaš akaunt je blokiran zbog kršenja pravila ! \n"+
                "Blokiran je : " + KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>();
        lista.add(korisnik);
        slanjeObavestenja(lista,text,"Blokiranje akaunta");
    }

    public void vratiKorisnika(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        korisnik.setOdobrenOdAdmina(true);
        userRepository.save(korisnik);

        String text = "Vaš akaunt je vraćen. \n"+
                "Vraćen je : " + KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>();
        lista.add(korisnik);
        slanjeObavestenja(lista,text,"Vraćanje akaunta");
    }

    @Async
    public void slanjeObavestenja(List<Korisnik> korisniks, String text, String naslov){
        ObavestenjeEmail thread = new ObavestenjeEmail(korisniks,javaMailSender,naslov,text);
        thread.start();
    }

    public Page<Korisnik>filterAllAdmin(KorisnikDTO korisnikDTO,Pageable pageable){
        Uloga uloga = ulogaRepository.findByName("ROLE_ADMIN");
        return userRepository.findByCustomCriteria(korisnikDTO,uloga,pageable);
    }

    public List<Korisnik> SveMusterije(){
        return userRepository.findAllByUlogeContainingAndPotvrdjenIsTrueAndOdobrenOdAdminaIsFalse(ulogaRepository.findById(3).orElse(null));
    }

    public void potvrdiMusteriju(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        korisnik.setOdobrenOdAdmina(true);
        userRepository.save(korisnik);
    }

    public boolean daLiSeVecKoristiEmail(String email){
        return userRepository.existsKorisnikByEmail(email);
    }

    public void register(RegistracijaDTO registracijaDTO){
        List<Uloga>listaUloga = new ArrayList<>();
        listaUloga.add(ulogaRepository.findByName(registracijaDTO.getUloga()));
        Korisnik korisnik = new Korisnik(registracijaDTO.getIme(), registracijaDTO.getPrezime(), registracijaDTO.getEmail(), registracijaDTO.getLozinka(), false,true,listaUloga);
        korisnik.setId(userRepository.findAll().size()+1);
        korisnik.setLozinka(customPasswordEncoder.encode(korisnik.getLozinka()));
        pravljenjePotvrde(userRepository.save(korisnik),"/verifikacijaRegistracija");
    }

    public void pravljenjeAdminNaloga(MusterijaDTO musterijaDTO){
        List<Uloga>listaUloga = new ArrayList<>();
        listaUloga.add(ulogaRepository.findById(2).orElse(null));
        Korisnik korisnik = new Korisnik(musterijaDTO.getIme(), musterijaDTO.getPrezime(), musterijaDTO.getEmail(), musterijaDTO.getLozinka(), false,true,listaUloga);
        korisnik.setId(userRepository.findAll().size()+1);
        korisnik.setLozinka(customPasswordEncoder.encode(korisnik.getLozinka()));
        pravljenjePotvrde(userRepository.save(korisnik),"/VerifikacijaAdminPravljenje");
    }

    public void slanjeEmailZaPromenuLozinke(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        pravljenjePotvrde(korisnik,"/primanjePromeneLozinke");
    }

    @Async
    public void pravljenjePotvrde(Korisnik korisnik,String adresa){
        String komeSalje = korisnik.getEmail();

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setKorisnik(korisnik);
        Date date = verificationToken.calculateExpiryDate(1440);
        verificationToken.setExpiryDate(date);
        verificationTokenRepository.save(verificationToken);

        VerifikacioniTokenSlanjeEmail thread = new VerifikacioniTokenSlanjeEmail(token,adresa,komeSalje,javaMailSender);
        thread.start();
    }

    public void promenaSifre(String token, String lozinka) throws Exception {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        Korisnik korisnik = verificationToken.getKorisnik();
        assert korisnik != null;
        CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
        Date date = new Date();
        if (!verificationToken.getExpiryDate().before(date))
            korisnik.setLozinka(passwordEncoder.encode(lozinka));
        else
            throw new Exception("Istekao");
        userRepository.save(korisnik);
    }

    public void potvrdaNaloga(String token) throws Exception {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        Korisnik korisnik = verificationToken.getKorisnik();
        assert korisnik != null;
        Date date = new Date();
        if (!verificationToken.getExpiryDate().before(date))
            korisnik.setPotvrdjen(true);
        else
            throw new Exception("Istekao");
        userRepository.save(korisnik);
    }

    public void delete(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        verificationTokenRepository.deleteByKorisnik(korisnik);
        userRepository.delete(korisnik);
    }

    public Boolean daLiJeOdobrenOdAdmina(String email){
        Korisnik korisnik = userRepository.findByEmail(email);
        return korisnik.isOdobrenOdAdmina() && korisnik.isPotvrdjen();
    }

    public Korisnik dajKorisnika(String email){
        return userRepository.findByEmail(email);
    }

    public CustomUserDetailsService() {
        this.customPasswordEncoder = new CustomPasswordEncoder();
    }
}
