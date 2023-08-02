package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProduktService implements ServiceInterface<Produkt>{
    @Autowired
    private ProduktRepository produktRepository;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public List findAll() {
        return produktRepository.findAll();
    }

    @Override
    public Produkt findOne(String id) {
        return produktRepository.findBySerijskiBroj(id).orElse(null);
    }

    @Override
    public Produkt create(Produkt entity) {
        if (produktRepository.existsProduktBySerijskiBroj(entity.getSerijskiBroj()))
            return null;
        for (Tip tip : entity.getListaTipova()){
            tip = tipRepository.findByNaziv(tip.getNaziv()).orElse(null);
        }
        entity.setProdavac(korisnikRepository.findByEmail(entity.getProdavac().getEmail()));
        return produktRepository.save(entity);
    }

    @Override
    public Produkt update(Produkt entity, String id) {
        Produkt produkt = produktRepository.findBySerijskiBroj(id).orElse(null);

        assert produkt != null;
        produkt.setCena(entity.getCena());
        produkt.setNaziv(entity.getNaziv());
        produkt.setDeskripcija(entity.getDeskripcija());

        for (Tip tip : entity.getListaTipova()){
            if (!produkt.getListaTipova().contains(tip))
                produkt.getListaTipova().add(tip);
        }

        List<Tip>temp = produkt.getListaTipova();
        for (Tip tip : produkt.getListaTipova()){
            if(!daLiSadrziTip(entity.getListaTipova(),tip)){
                temp.remove(tip);
            }
        }
        produkt.setListaTipova(temp);

        return produktRepository.save(produkt);
    }

    public boolean daLiSadrziTip(List<Tip> lista, Tip tip){
        for (Tip tip1 : lista){
            if (tip1.getNaziv().equals(tip.getNaziv()))
                return true;
        }
        return false;
    }

    @Override
    public void delete(String id) {

    }
}
