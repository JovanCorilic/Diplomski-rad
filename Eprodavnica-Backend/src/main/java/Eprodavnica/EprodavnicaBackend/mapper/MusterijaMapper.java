package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.util.ArrayList;
import java.util.List;

public class MusterijaMapper implements MapperInterface<Korisnik, MusterijaDTO> {
    private final TipMapper tipMapper;
    private final RacunMapper racunMapper;

    @Override
    public Korisnik toModel(MusterijaDTO dto) {
        List<Tip>tips = new ArrayList<>();
        for (TipDTO tipDTO : dto.getListaTipova()){
            tips.add(tipMapper.toModel(tipDTO));
        }
        List<Racun>racuns = new ArrayList<>();
        for (RacunDTO racunDTO : dto.getListaRacuna()){
            racuns.add(racunMapper.toModel(racunDTO));
        }
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail(),tips,racuns);
    }

    @Override
    public MusterijaDTO toDto(Korisnik entity) {
        List<TipDTO>tipDTOS = new ArrayList<>();
        for (Tip tip : entity.getListaTipova()){
            tipDTOS.add(tipMapper.toDto(tip));
        }
        List<RacunDTO>racunDTOS = new ArrayList<>();
        for (Racun racun : entity.getListaRacuna()){
            racunDTOS.add(racunMapper.toDto(racun));
        }
        return new MusterijaDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),"-1",tipDTOS,racunDTOS);
    }

    public MusterijaMapper() {
        this.tipMapper = new TipMapper();
        this.racunMapper = new RacunMapper();
    }
}
