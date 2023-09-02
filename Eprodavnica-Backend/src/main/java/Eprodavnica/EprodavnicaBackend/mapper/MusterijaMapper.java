package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.*;
import Eprodavnica.EprodavnicaBackend.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MusterijaMapper implements MapperInterface<Korisnik, MusterijaDTO> {

    @Override
    public Korisnik toModel(MusterijaDTO dto) {

        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail());
    }

    @Override
    public MusterijaDTO toDto(Korisnik entity) {
        return new MusterijaDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),"-1");
    }
}
