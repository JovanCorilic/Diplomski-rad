package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.RecenzijaDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;

public class RecenzijaMapper implements MapperInterface<Recenzija, RecenzijaDTO> {
    @Override
    public Recenzija toModel(RecenzijaDTO dto) {
        return new Recenzija(dto.getId(),dto.getOcena(),dto.getKomentar(),dto.getDatumPravljenja(),
                new Korisnik(dto.getEmailMustarija()), new Produkt(dto.getSerijskiBrojProdukt()));
    }

    @Override
    public RecenzijaDTO toDto(Recenzija entity) {
        return new RecenzijaDTO(entity.getId(),entity.getOcena(),entity.getKomentar(),entity.getDatumPravljenja(),
                entity.getMusterija().getEmail(), entity.getProdukt().getSerijskiBroj());
    }
}
