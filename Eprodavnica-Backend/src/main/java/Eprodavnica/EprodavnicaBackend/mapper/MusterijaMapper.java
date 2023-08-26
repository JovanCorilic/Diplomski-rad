package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.*;
import Eprodavnica.EprodavnicaBackend.model.*;

import java.util.ArrayList;
import java.util.List;

public class MusterijaMapper implements MapperInterface<Korisnik, MusterijaDTO> {
    private final TipMapper tipMapper;
    private final RacunMapper racunMapper;
    private final RecenzijaMapper recenzijaMapper;
    private final ProduktMapper produktMapper;

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
        List<Produkt>produkts = new ArrayList<>();
        for (ProduktMiniDTO produktMiniDTO : dto.getWishlist()){
            produkts.add(produktMapper.toMini(produktMiniDTO));
        }
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail(),tips,racuns,recenzijas,produkts);
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
        List<ProduktMiniDTO>produktMiniDTOS = produktMapper.toDTOListaMiniProdukt(entity.getWishlist());
        return new MusterijaDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),"-1",tipDTOS,racunDTOS,
                recenzijaDTOS,produktMiniDTOS);
    }

    public MusterijaMapper() {
        this.tipMapper = new TipMapper();
        this.racunMapper = new RacunMapper();
        this.recenzijaMapper = new RecenzijaMapper();
        this.produktMapper = new ProduktMapper();
    }
}
