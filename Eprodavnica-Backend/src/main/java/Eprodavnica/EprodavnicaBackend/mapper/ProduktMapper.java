package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
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

    @Override
    public Produkt toModel(ProduktDTO dto) {
        List<Tip>tips = new ArrayList<>();
        for (TipDTO tipDTO : dto.getListaTipova()){
            tips.add(tipMapper.toModel(tipDTO));
        }
        return new Produkt(dto.getNaziv(),dto.getDeskripcija(),dto.getSerijskiBroj(),dto.getCena(),dto.getOcena(),
                dto.getDatumPravljenja(),dto.getAkcija(),tips,new Korisnik(dto.getEmailProdavac()));
    }

    @Override
    public ProduktDTO toDto(Produkt entity) {
        List<TipDTO>tipDTOS = new ArrayList<>();
        for (Tip tip : entity.getListaTipova()){
            tipDTOS.add(tipMapper.toDto(tip));
        }
        return new ProduktDTO(entity.getNaziv(),entity.getDeskripcija(),entity.getSerijskiBroj(),entity.getCena(),
                entity.getOcena(),entity.getDatumPravljenja(),entity.getAkcija(),tipDTOS,entity.getProdavac().getEmail());
    }

    public ProduktMiniDTO toDTOMini(Produkt entity){
        return new ProduktMiniDTO(entity.getNaziv(),entity.getSerijskiBroj(),entity.getCena(),entity.getOcena(),
                entity.getAkcija());
    }

    public Produkt toMini(ProduktMiniDTO dto){
        return new Produkt(dto.getNaziv(),dto.getSerijskiBroj(),dto.getCena(),dto.getOcena(),dto.getAkcija());
    }

    public List<ProduktMiniDTO> toDTOListaMiniProdukt(List<Produkt>lista){
        List<ProduktMiniDTO>temp = new ArrayList<>();
        for (Produkt produkt : lista){
            temp.add(toDTOMini(produkt));
        }
        return temp;
    }

    public List<ProduktDTO> toDTOListProdukt(List<Produkt>lista){
        List<ProduktDTO>temp = new ArrayList<>();
        for (Produkt produkt : lista){
            temp.add(toDto(produkt));
        }
        return temp;
    }

    public ProduktMapper() {
        this.tipMapper = new TipMapper();
    }
}
