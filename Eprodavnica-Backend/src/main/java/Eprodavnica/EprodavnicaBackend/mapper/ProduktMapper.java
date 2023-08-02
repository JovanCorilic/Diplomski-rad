package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.util.ArrayList;
import java.util.List;

public class ProduktMapper implements MapperInterface<Produkt, ProduktDTO> {
    private final TipMapper tipMapper;

    @Override
    public Produkt toModel(ProduktDTO dto) {
        List<Tip>tips = new ArrayList<>();
        for (TipDTO tipDTO : dto.getListaTipova()){
            tips.add(tipMapper.toModel(tipDTO));
        }
        return new Produkt(dto.getNaziv(),dto.getDeskripcija(),dto.getSerijskiBroj(),dto.getCena(),tips);
    }

    @Override
    public ProduktDTO toDto(Produkt entity) {
        List<TipDTO>tipDTOS = new ArrayList<>();
        for (Tip tip : entity.getListaTipova()){
            tipDTOS.add(tipMapper.toDto(tip));
        }
        return new ProduktDTO(entity.getNaziv(),entity.getDeskripcija(),entity.getSerijskiBroj(),entity.getCena(),tipDTOS);
    }

    public ProduktMapper() {
        this.tipMapper = new TipMapper();
    }
}
