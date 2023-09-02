package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProdavacDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;

import java.util.ArrayList;
import java.util.List;

public class ProdavacMapper implements MapperInterface<Korisnik, ProdavacDTO> {

    @Override
    public Korisnik toModel(ProdavacDTO dto) {
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail());
    }

    @Override
    public ProdavacDTO toDto(Korisnik entity) {
        return new ProdavacDTO(entity.getIme(),entity.getPrezime(),entity.getEmail());
    }

}
