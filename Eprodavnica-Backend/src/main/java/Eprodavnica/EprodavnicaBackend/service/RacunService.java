package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.model.*;
import Eprodavnica.EprodavnicaBackend.other.KonverterDatum;
import Eprodavnica.EprodavnicaBackend.other.ObavestenjeEmail;
import Eprodavnica.EprodavnicaBackend.other.VerifikacioniTokenSlanjeEmail;
import Eprodavnica.EprodavnicaBackend.repository.ArtikalRepository;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.RacunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class RacunService implements ServiceInterface<Racun> {
    @Autowired
    private RacunRepository racunRepository;
    @Autowired
    private ArtikalRepository artikalRepository;
    @Autowired
    private ProduktRepository produktRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public List<Racun> findAll() {
        return racunRepository.findAll();
    }

    @Override
    public Racun findOne(String id) {
        return racunRepository.findByBrojRacuna(id).orElse(null);
    }

    public Racun dajAktivanRacun(){
        return racunRepository.findByKorpaIsTrue().orElse(null);
    }

    @Override
    public Racun create(Racun entity) {
        return null;
    }

    public void dodajArtikal(Artikal artikal,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        Racun racun = racunRepository.findByKorpaIsTrue().orElse(null);
        if (racun == null){
            Racun racun1 = new Racun();
            racun1.setMusterija(korisnik);
            racun1.setKonacnaCena(0.0);
            racun1.setBrojRacuna(generisiRandomBrojRacuna());
            racun1.getArtikals().add(artikal);
            racun1.setKorpa(true);
            racun1.setKonacnaCena(artikal.getUkupnaCena());
            createSaArtiklom(racun1,artikal);
        }else {
            artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
            artikal = artikalRepository.save(artikal);
            racun.getArtikals().add(artikal);
            racun.setKonacnaCena(racun.getKonacnaCena()+artikal.getUkupnaCena());

            racunRepository.save(racun);
        }
    }

    public void createSaArtiklom(Racun entity,Artikal artikal) {
        entity.setBrojRacuna(generisiRandomBrojRacuna());
        while (racunRepository.existsRacunByBrojRacuna(entity.getBrojRacuna())){
            entity.setBrojRacuna(generisiRandomBrojRacuna());
        }

        artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
        artikal = artikalRepository.save(artikal);
        entity.getArtikals().add(artikal);
        entity.setId((int) racunRepository.count());
        racunRepository.save(entity);
    }

    public String generisiRandomBrojRacuna() {
        byte[] array = new byte[64];
        Random random = new Random();
        random.nextBytes(array);

        return Base64.getUrlEncoder().encodeToString(array);
    }

    @Override
    public Racun update(Racun entity, String id) {
        Racun racun = racunRepository.findByBrojRacuna(id).orElse(null);
        assert racun != null;
        racun.setKonacnaCena(entity.getKonacnaCena());

        for (Artikal artikal : entity.getArtikals()){
            if (!racun.getArtikals().contains(artikal)) {
                artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
                racun.getArtikals().add(artikalRepository.save(artikal));
            }
        }

        for (Artikal artikal : racun.getArtikals()){
            if (!daLiSadrziArtikal(entity.getArtikals(),artikal)){
                racun.getArtikals().remove(artikal);
                artikal.setProdukt(null);
                artikalRepository.delete(artikal);
            }
        }

        return racunRepository.save(racun);
    }

    public boolean daLiSadrziArtikal(Set<Artikal> lista, Artikal artikal){
        for (Artikal artikal1 : lista){
            if (artikal1.getProdukt().getSerijskiBroj().equals(artikal.getProdukt().getSerijskiBroj()))
                return true;
        }
        return false;
    }

    public Page<Racun> getAllMusterija(Pageable pageable,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return racunRepository.findByMusterija(korisnik,pageable);
    }

    public Page<Racun>getAllAdmin(Pageable pageable){
        return racunRepository.findAll(pageable);
    }

    public Page<Racun>filterMusterija(FilterDTO filterDTO, Pageable pageable, String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        double od = -1;
        double do1 = -1;
        if (filterDTO.getCena()!=null)
            if (filterDTO.getCena().getDoCena()!=null && filterDTO.getCena().getOdCena()!=null){
                if (isNumeric(filterDTO.getCena().getDoCena()) && isNumeric(filterDTO.getCena().getOdCena())){
                    od = Double.parseDouble(filterDTO.getCena().getOdCena());
                    do1 = Double.parseDouble(filterDTO.getCena().getDoCena());
                }
            }
        return racunRepository.findByCustomCriteriaMusterija(od,do1,filterDTO.getDatum().getOdDatum(),
                filterDTO.getDatum().getDoDatum(), korisnik,pageable);
    }

    public Page<Racun>filterAdmin(FilterDTO filterDTO, Pageable pageable){
        double od = -1;
        double do1 = -1;
        if (filterDTO.getCena()!=null)
            if (filterDTO.getCena().getDoCena()!=null && filterDTO.getCena().getOdCena()!=null){
                if (isNumeric(filterDTO.getCena().getDoCena()) && isNumeric(filterDTO.getCena().getOdCena())){
                    od = Double.parseDouble(filterDTO.getCena().getOdCena());
                    do1 = Double.parseDouble(filterDTO.getCena().getDoCena());
                }
            }
        return racunRepository.findByCustomCriteriaAdmin(od,do1,filterDTO.getDatum().getOdDatum(),
                filterDTO.getDatum().getDoDatum(),pageable);
    }

    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public Page<Artikal>getAllArtikalPageable(String brojRacuna,Pageable pageable){
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna).orElse(null);
        return artikalRepository.findByRacun(racun,pageable);
    }

    @Override
    public void delete(String id) {

    }

    public void deleteArtikal(Integer id) {
        Artikal artikal = artikalRepository.findById(id).orElse(null);
        assert artikal != null;
        artikal.setProdukt(null);

        Racun racun = artikal.getRacun();
        racun.setKonacnaCena(racun.getKonacnaCena()-artikal.getUkupnaCena());
        racunRepository.save(racun);
        artikal.setRacun(null);

        artikal = artikalRepository.save(artikal);
        artikalRepository.delete(artikal);
    }

    public void plati(String brojRacuna,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna).orElse(null);
        assert racun != null;
        racun.setKorpa(false);
        Racun temp =racunRepository.save(racun);
        slanjeObavestenjaZaRacun(korisnik,racun);
        DodajUIStoriju(korisnik,temp);
    }

    @Async
    public void slanjeObavestenjaZaRacun(Korisnik korisnik,Racun racun){
        List<Korisnik>korisniks = new ArrayList<>();
        korisniks.add(korisnik);

        String text = "";

        for (Artikal artikal : racun.getArtikals()){
            text+="---------------------------------------------\n" +
                "Naziv produkta : "+artikal.getNazivProdukta()+"\n" +
                "Broj uzetih prdukata : "+artikal.getBroj() + "\n" +
                "Akcija : "+artikal.getAkcija()+"%\n" +
                "Cena :"+(artikal.getCena() - (artikal.getCena()*(artikal.getAkcija()/100))) + "din\n" +
                "Ukupna cena za artikal : "+artikal.getUkupnaCena()+"din\n" +
                "---------------------------------------------";
        }
        racun.setDatumKreiranja(new Date());
        LocalDate date = LocalDate.ofInstant(racun.getDatumKreiranja().toInstant(), ZoneId.systemDefault());
        text += "Konačna cena : "+racun.getKonacnaCena()+"\n"+
                "Datum pravljenja računa : "+ KonverterDatum.konvertovanjeSamoDatumUString(date);
        ObavestenjeEmail thread = new ObavestenjeEmail(korisniks,javaMailSender,"EProdavnica račun",text);
        thread.start();
        racunRepository.save(racun);
    }

    public void DodajUIStoriju(Korisnik korisnik, Racun racun){
        for (Artikal artikal : racun.getArtikals()) {
            if (!korisnik.getIstorijaKupljenihProdukata().contains(artikal.getProdukt())) {
                korisnik.getIstorijaKupljenihProdukata().add(artikal.getProdukt());
            }
        }
        korisnikRepository.save(korisnik);
    }
}
