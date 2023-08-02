package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ArtikalDTO;
import Eprodavnica.EprodavnicaBackend.model.Artikal;

public class ArtikalMapper implements MapperInterface<Artikal, ArtikalDTO> {
    private final ProduktMapper produktMapper;

    @Override
    public Artikal toModel(ArtikalDTO dto) {
        return new Artikal(dto.getBroj(),produktMapper.toModel(dto.getProdukt()));
    }

    @Override
    public ArtikalDTO toDto(Artikal entity) {
        return new ArtikalDTO(entity.getBroj(),produktMapper.toDto(entity.getProdukt()));
    }

    public ArtikalMapper() {
        this.produktMapper = new ProduktMapper();
    }
}
