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

    @Override
    public Racun toModel(RacunDTO dto) {
        return new Racun(dto.getKonacnaCena(),dto.getBrojRacuna(),dto.getDatumKreiranja(),dto.isKorpa(),
                new Korisnik(dto.getEmailMusterija()));
    }

    @Override
    public RacunDTO toDto(Racun entity) {
        return new RacunDTO(entity.getKonacnaCena(),entity.getBrojRacuna(),entity.getMusterija().getEmail(),
                entity.getDatumKreiranja(),entity.isKorpa());
    }

    public List<RacunDTO> toDtoLista(List<Racun>lista){
        List<RacunDTO>temp = new ArrayList<>();
        for (Racun racun : lista){
            temp.add(toDto(racun));
        }
        return temp;
    }

}
