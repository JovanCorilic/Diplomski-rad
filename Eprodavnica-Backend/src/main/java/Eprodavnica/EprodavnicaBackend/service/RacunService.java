package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.repository.ArtikalRepository;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.RacunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Racun create(Racun entity) {
        return null;
    }

    public Racun createSaArtiklom(Racun entity,Artikal artikal) {
        while (racunRepository.existsRacunByBrojRacuna(entity.getBrojRacuna())){
            entity.setBrojRacuna(generisiRandomBrojRacuna());
        }

        artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
        artikal = artikalRepository.save(artikal);
        entity.getArtikals().add(artikal);
        //entity.setMusterija(korisnikRepository.findByEmail(entity.getMusterija().getEmail()));
        return racunRepository.save(entity);
    }

    public void dodajArtikal(Artikal artikal,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        Racun racun = racunRepository.findByKonacnaCenaAndMusterija(0.0,korisnik).orElse(null);
        if (racun == null){
            Racun racun1 = new Racun();
            racun1.setMusterija(korisnik);
            racun1.setKonacnaCena(0.0);
            racun1.setBrojRacuna(generisiRandomBrojRacuna());
            racun1.getArtikals().add(artikal);
            createSaArtiklom(racun1,artikal);
        }else {
            artikal.setProdukt(produktRepository.findBySerijskiBroj(artikal.getProdukt().getSerijskiBroj()).orElse(null));
            artikal = artikalRepository.save(artikal);
            racun.getArtikals().add(artikal);
            racunRepository.save(racun);
        }
    }

    public String generisiRandomBrojRacuna() {
        byte[] array = new byte[64]; // length is bounded by 7
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

    @Override
    public void delete(String id) {

    }
}
