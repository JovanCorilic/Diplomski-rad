package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.OcenaDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Page<Recenzija>findAllPageable(Pageable pageable,String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        return recenzijaRepository.findByProdukt(produkt,pageable);
    }

    public Page<Recenzija>findAllMusterijaPageable(Pageable pageable, String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return recenzijaRepository.findByMusterija(korisnik,pageable);
    }

    public Page<Recenzija>filterPageable(Pageable pageable, String serijskiBroj, FilterDTO filterDTO){
        List<Integer>ocene = new ArrayList<>();
        for (OcenaDTO ocenaDTO : filterDTO.getOcena()){
            if (ocenaDTO.isKoristiSe())
                ocene.add(ocenaDTO.getOcena());
        }
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        return recenzijaRepository.findByOcenaInAndProdukt(ocene,produkt,pageable);

    }

    public Page<Recenzija>filterMusterijaPageable(Pageable pageable,FilterDTO filterDTO,String email){
        List<Integer>ocene = new ArrayList<>();
        for (OcenaDTO ocenaDTO : filterDTO.getOcena()){
            if (ocenaDTO.isKoristiSe())
                ocene.add(ocenaDTO.getOcena());
        }
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return recenzijaRepository.findByCustomCriteria(ocene,filterDTO.getDatum().getOdDatum(),
                filterDTO.getDatum().getDoDatum(), korisnik,pageable);
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
