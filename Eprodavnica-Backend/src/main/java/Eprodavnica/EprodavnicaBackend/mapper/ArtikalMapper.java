package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ArtikalDTO;
import Eprodavnica.EprodavnicaBackend.model.Artikal;

import java.util.ArrayList;
import java.util.List;

public class ArtikalMapper implements MapperInterface<Artikal, ArtikalDTO> {
    private final ProduktMapper produktMapper;

    @Override
    public Artikal toModel(ArtikalDTO dto) {
        return new Artikal(dto.getBroj(),produktMapper.toMini(dto.getProdukt()));
    }

    @Override
    public ArtikalDTO toDto(Artikal entity) {
        return new ArtikalDTO(entity.getBroj(),produktMapper.toDTOMini(entity.getProdukt()));
    }

    public List<ArtikalDTO>toDtoArtikal(List<Artikal>lista){
        List<ArtikalDTO>temp = new ArrayList<>();
        for (Artikal artikal : lista){
            temp.add(toDto(artikal));
        }
        return temp;
    }

    public ArtikalMapper() {
        this.produktMapper = new ProduktMapper();
    }
}
