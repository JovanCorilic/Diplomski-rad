package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ArtikalDTO;
import Eprodavnica.EprodavnicaBackend.model.Artikal;

import java.util.ArrayList;
import java.util.List;

public class ArtikalMapper implements MapperInterface<Artikal, ArtikalDTO> {

    @Override
    public Artikal toModel(ArtikalDTO dto) {
        return new Artikal(dto.getId(),dto.getBroj(),dto.getNazivProdukta(),dto.getCena(),dto.getAkcija(),
                dto.getUkupnaCena());
    }

    @Override
    public ArtikalDTO toDto(Artikal entity) {
        return new ArtikalDTO(entity.getId(),entity.getBroj(),entity.getNazivProdukta(),entity.getCena(),
                entity.getAkcija(),entity.getUkupnaCena(),entity.getProdukt().getSerijskiBroj());
    }

    public List<ArtikalDTO>toDtoArtikal(List<Artikal>lista){
        List<ArtikalDTO>temp = new ArrayList<>();
        for (Artikal artikal : lista){
            temp.add(toDto(artikal));
        }
        return temp;
    }

}
