package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProdavacDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;

import java.util.ArrayList;
import java.util.List;

public class ProdavacMapper implements MapperInterface<Korisnik, ProdavacDTO> {
    private final ProduktMapper produktMapper;

    @Override
    public Korisnik toModel(ProdavacDTO dto) {
        List<Produkt>produkts = new ArrayList<>();
        for (ProduktDTO produktDTO : dto.getListaProdukata()){
            produkts.add(produktMapper.toModel(produktDTO));
        }
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail(),produkts);
    }

    @Override
    public ProdavacDTO toDto(Korisnik entity) {
        List<ProduktDTO>produktDTOS = new ArrayList<>();
        for (Produkt produkt : entity.getListaProdukata()){
            produktDTOS.add(produktMapper.toDto(produkt));
        }
        return new ProdavacDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),produktDTOS);
    }

    public ProdavacMapper() {
        this.produktMapper = new ProduktMapper();
    }
}
