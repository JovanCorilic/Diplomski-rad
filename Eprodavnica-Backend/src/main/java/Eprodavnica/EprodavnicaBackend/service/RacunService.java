package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.repository.ArtikalRepository;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.RacunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
            racun1.setDatumKreiranja(new Date());
            racun1.setKorpa(true);
            createSaArtiklom(racun1,artikal, korisnik);
        }else {
            artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
            artikal = artikalRepository.save(artikal);
            racun.getArtikals().add(artikal);
            DodajUIStoriju(korisnik,artikal.getProdukt());
            racunRepository.save(racun);
        }
    }

    public void createSaArtiklom(Racun entity,Artikal artikal,Korisnik korisnik) {
        entity.setBrojRacuna(generisiRandomBrojRacuna());
        while (racunRepository.existsRacunByBrojRacuna(entity.getBrojRacuna())){
            entity.setBrojRacuna(generisiRandomBrojRacuna());
        }

        artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
        artikal = artikalRepository.save(artikal);
        entity.getArtikals().add(artikal);
        DodajUIStoriju(korisnik,artikal.getProdukt());
        //entity.setMusterija(korisnikRepository.findByEmail(entity.getMusterija().getEmail()));
        racunRepository.save(entity);
    }

    public void DodajUIStoriju(Korisnik korisnik, Produkt produkt){
        if (!korisnik.getIstorijaKupljenihProdukata().contains(produkt)){
            korisnik.getIstorijaKupljenihProdukata().add(produkt);
            korisnikRepository.save(korisnik);
        }
    }

    public String generisiRandomBrojRacuna() {
        byte[] array = new byte[64];
        Random random = new Random();
        random.nextBytes(array);

        return new String(array);
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
        artikal.setRacun(null);
        artikal = artikalRepository.save(artikal);
        artikalRepository.delete(artikal);
    }

    public void plati(String brojRacuna){
        Racun racun = racunRepository.findByBrojRacuna(brojRacuna).orElse(null);
        assert racun != null;
        racun.setKorpa(false);
        racunRepository.save(racun);
    }
}
