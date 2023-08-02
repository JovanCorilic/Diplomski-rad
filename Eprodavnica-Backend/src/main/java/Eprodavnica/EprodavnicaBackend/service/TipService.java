package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService implements ServiceInterface<Tip>{
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private ProduktRepository produktRepository;


    @Override
    public List<Tip> findAll() {
        return tipRepository.findAll();
    }

    @Override
    public Tip findOne(String id) {
        return tipRepository.findByNaziv(id).orElse(null);
    }

    @Override
    public Tip create(Tip entity) {
        if (!tipRepository.existsTipByNaziv(entity.getNaziv()))
            return tipRepository.save(entity);
        else
            return null;
    }

    @Override
    public Tip update(Tip entity, String id) {
        Tip tip = tipRepository.findByNaziv(id).orElse(null);
        assert tip != null;
        if (!tipRepository.existsTipByNaziv(entity.getNaziv()) && !tip.getNaziv().equals(entity.getNaziv()))
            tip.setNaziv(entity.getNaziv());
        return tipRepository.save(tip);
    }

    @Override
    public void delete(String id) {

    }
}
