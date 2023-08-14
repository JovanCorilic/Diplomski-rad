package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.MusterijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.RacunDTO;
import Eprodavnica.EprodavnicaBackend.dto.RecenzijaDTO;
import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.util.ArrayList;
import java.util.List;

public class MusterijaMapper implements MapperInterface<Korisnik, MusterijaDTO> {
    private final TipMapper tipMapper;
    private final RacunMapper racunMapper;
    private final RecenzijaMapper recenzijaMapper;

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
        List<Recenzija>recenzijas = new ArrayList<>();
        for (RecenzijaDTO recenzijaDTO : dto.getListaRecenzija()){
            recenzijas.add(recenzijaMapper.toModel(recenzijaDTO));
        }
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail(),tips,racuns,recenzijas);
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
        List<RecenzijaDTO>recenzijaDTOS = new ArrayList<>();
        for (Recenzija recenzija : entity.getListaRecenzija()){
            recenzijaDTOS.add(recenzijaMapper.toDto(recenzija));
        }
        return new MusterijaDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),"-1",tipDTOS,racunDTOS,recenzijaDTOS);
    }

    public MusterijaMapper() {
        this.tipMapper = new TipMapper();
        this.racunMapper = new RacunMapper();
        this.recenzijaMapper = new RecenzijaMapper();
    }
}
