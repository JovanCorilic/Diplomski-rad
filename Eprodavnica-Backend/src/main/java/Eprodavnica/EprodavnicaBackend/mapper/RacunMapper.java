package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ArtikalDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.model.Artikal;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RacunMapper implements MapperInterface<Racun, RacunDTO> {
    private final ArtikalMapper artikalMapper;

    @Override
    public Racun toModel(RacunDTO dto) {
        Set<Artikal> artikals = new HashSet<>();
        for (ArtikalDTO artikal : dto.getArtikals()){
            artikals.add(artikalMapper.toModel(artikal));
        }
        return new Racun(dto.getKonacnaCena(),dto.getBrojRacuna(),artikals,new Korisnik(dto.getEmailMusterija()));
    }

    @Override
    public RacunDTO toDto(Racun entity) {
        List<ArtikalDTO>artikalDTOS = new ArrayList<>();
        for (Artikal artikal : entity.getArtikals()){
            artikalDTOS.add(artikalMapper.toDto(artikal));
        }
        return new RacunDTO(entity.getKonacnaCena(),entity.getBrojRacuna(),artikalDTOS,entity.getMusterija().getEmail());
    }

    public RacunMapper() {
        this.artikalMapper = new ArtikalMapper();
    }
}
