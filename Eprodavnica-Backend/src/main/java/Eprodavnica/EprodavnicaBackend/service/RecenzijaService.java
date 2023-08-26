package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecenzijaService implements ServiceInterface<Recenzija>{
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ProduktRepository produktRepository;

    @Override
    public List<Recenzija> findAll() {
        return recenzijaRepository.findAll();
    }

    public Recenzija findOne(Integer id) {
        return recenzijaRepository.findById(id).orElse(null);
    }

    @Override
    public Recenzija create(Recenzija entity) {
        entity.setMusterija(korisnikRepository.findByEmail(entity.getMusterija().getEmail()));
        entity.setProdukt(produktRepository.findBySerijskiBroj(entity.getProdukt().getSerijskiBroj()).orElse(null));
        entity.setDatumPravljenja(new Date());
        return recenzijaRepository.save(entity);
    }

    public Recenzija update(Recenzija entity, int id) {
        Recenzija recenzija = recenzijaRepository.findById(id).orElse(null);
        assert recenzija != null;
        recenzija.setOcena(entity.getOcena());
        recenzija.setKomentar(entity.getKomentar());
        return recenzijaRepository.save(recenzija);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Recenzija update(Recenzija entity, String id) {
        return null;
    }

    @Override
    public Recenzija findOne(String id) {
        return null;
    }
}
