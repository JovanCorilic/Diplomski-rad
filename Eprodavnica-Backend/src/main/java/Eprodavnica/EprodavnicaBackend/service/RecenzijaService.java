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
public class RecenzijaService{
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ProduktRepository produktRepository;

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

    public Page<Recenzija>findAllAdminPageable(Pageable pageable){
        return recenzijaRepository.findAll(pageable);
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

    public Page<Recenzija>filterAdminPageable(Pageable pageable,FilterDTO filterDTO){
        List<Integer>ocene = new ArrayList<>();
        for (OcenaDTO ocenaDTO : filterDTO.getOcena()){
            if (ocenaDTO.isKoristiSe())
                ocene.add(ocenaDTO.getOcena());
        }
        return recenzijaRepository.findByCustomCriteriaAdmin(ocene,filterDTO.getDatum().getOdDatum(),
                filterDTO.getDatum().getDoDatum(),pageable);
    }

    public Recenzija findOne(Integer id) {
        return recenzijaRepository.findById(id).orElse(null);
    }

    public Recenzija dajRecenzijuMusterije(String serijskiBroj,String email){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return recenzijaRepository.findByMusterijaAndProdukt(korisnik,produkt);
    }

    public void create(Recenzija entity, String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        entity.setMusterija(korisnik);
        entity.setProdukt(produktRepository.findBySerijskiBroj(entity.getProdukt().getSerijskiBroj()).orElse(null));
        entity.setDatumPravljenja(new Date());
        recenzijaRepository.save(entity);
        IzracunajProsecnuOcenu(entity.getProdukt());
    }

    public void update(Recenzija entity, int id) {
        Recenzija recenzija = recenzijaRepository.findById(id).orElse(null);
        assert recenzija != null;
        recenzija.setOcena(entity.getOcena());
        recenzija.setKomentar(entity.getKomentar());
        recenzijaRepository.save(recenzija);
        IzracunajProsecnuOcenu(recenzija.getProdukt());
    }


    public void delete(Integer id) {
        Recenzija recenzija = recenzijaRepository.findById(id).orElse(null);
        assert recenzija != null;
        recenzija.setMusterija(null);
        Produkt produkt = recenzija.getProdukt();
        recenzija.setProdukt(null);
        recenzija = recenzijaRepository.save(recenzija);
        recenzijaRepository.delete(recenzija);

        IzracunajProsecnuOcenu(produkt);
    }

    public Boolean daLiImaRecenzijuZaProdukt(String serijskiBroj, String email){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return recenzijaRepository.existsByMusterijaAndProdukt(korisnik,produkt);
    }

    public void IzracunajProsecnuOcenu(Produkt produkt){
        double ocena = -1.0;
        int br = recenzijaRepository.countByProdukt(produkt);
        if (br != 0)
            ocena = recenzijaRepository.dajProsecnuOcenu(produkt);

        produkt.setOcena(ocena);
        produkt.PretvoriUPunBroj();
        produktRepository.save(produkt);
    }

    public Recenzija findOne(String id) {
        return null;
    }
}
