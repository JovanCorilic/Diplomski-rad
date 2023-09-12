package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService{
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private ProduktRepository produktRepository;

    public List<Tip> findAll() {
        return tipRepository.findAll();
    }

    public Page<Tip>findAllPageable(Pageable pageable){
        return tipRepository.findAll(pageable);
    }

    public Page<Tip>filterAllPageable(Pageable pageable, FilterDTO filterDTO){
        return tipRepository.findByNazivContainingIgnoreCase(filterDTO.getNaziv(),pageable);
    }

    public Tip findOne(String id) {
        return tipRepository.findByNaziv(id).orElse(null);
    }

    public Tip create(Tip entity) throws Exception {
        if (!tipRepository.existsTipByNaziv(entity.getNaziv()))
            return tipRepository.save(entity);
        else
            throw new Exception();
    }

    public Tip update(Tip entity, String id) throws Exception {
        Tip tip = tipRepository.findByNaziv(id).orElse(null);
        assert tip != null;
        if (!tipRepository.existsTipByNaziv(entity.getNaziv()) && !tip.getNaziv().equals(entity.getNaziv())) {
            tip.setNaziv(entity.getNaziv());
            return tipRepository.save(tip);
        }else
            throw new Exception();
    }

    public void delete(String id) {

    }
}
