package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.RecenzijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.util.ArrayList;
import java.util.List;

public class ProduktMapper implements MapperInterface<Produkt, ProduktDTO> {
    private final TipMapper tipMapper;
    private final RecenzijaMapper recenzijaMapper;

    @Override
    public Produkt toModel(ProduktDTO dto) {
        List<Tip>tips = new ArrayList<>();
        for (TipDTO tipDTO : dto.getListaTipova()){
            tips.add(tipMapper.toModel(tipDTO));
        }
        List<Recenzija>recenzijas = new ArrayList<>();
        for (RecenzijaDTO recenzijaDTO : dto.getListaRecenzija()){
            recenzijas.add(recenzijaMapper.toModel(recenzijaDTO));
        }
        return new Produkt(dto.getNaziv(),dto.getDeskripcija(),dto.getSerijskiBroj(),dto.getCena(),dto.getOcena(),
                tips,new Korisnik(dto.getEmailProdavac()),recenzijas);
    }

    @Override
    public ProduktDTO toDto(Produkt entity) {
        List<TipDTO>tipDTOS = new ArrayList<>();
        for (Tip tip : entity.getListaTipova()){
            tipDTOS.add(tipMapper.toDto(tip));
        }
        List<RecenzijaDTO>recenzijaDTOS = new ArrayList<>();
        for (Recenzija recenzija : entity.getListaRecenzija()){
            recenzijaDTOS.add(recenzijaMapper.toDto(recenzija));
        }
        return new ProduktDTO(entity.getNaziv(),entity.getDeskripcija(),entity.getSerijskiBroj(),entity.getCena(),
                entity.getOcena(),tipDTOS,recenzijaDTOS,entity.getProdavac().getEmail());
    }

    public ProduktMapper() {
        this.tipMapper = new TipMapper();
        this.recenzijaMapper = new RecenzijaMapper();
    }
}
