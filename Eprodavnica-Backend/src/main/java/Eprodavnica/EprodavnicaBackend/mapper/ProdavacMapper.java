package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.ProdavacDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktDTO;
import Eprodavnica.EprodavnicaBackend.dto.ProduktMiniDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;

import java.util.ArrayList;
import java.util.List;

public class ProdavacMapper implements MapperInterface<Korisnik, ProdavacDTO> {
    private final ProduktMapper produktMapper;

    @Override
    public Korisnik toModel(ProdavacDTO dto) {
        List<Produkt>produkts = new ArrayList<>();
        for (ProduktMiniDTO produktDTO : dto.getListaProdukata()){
            produkts.add(produktMapper.toMini(produktDTO));
        }
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail(),produkts);
    }

    @Override
    public ProdavacDTO toDto(Korisnik entity) {
        List<ProduktMiniDTO>produktDTOS = new ArrayList<>();
        for (Produkt produkt : entity.getListaProdukata()){
            produktDTOS.add(produktMapper.toDTOMini(produkt));
        }
        return new ProdavacDTO(entity.getIme(),entity.getPrezime(),entity.getEmail(),produktDTOS);
    }

    public ProdavacMapper() {
        this.produktMapper = new ProduktMapper();
    }
}
