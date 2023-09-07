package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.KorisnikDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;

import java.util.ArrayList;
import java.util.List;

public class KorisnikMapper implements MapperInterface<Korisnik, KorisnikDTO> {
    @Override
    public Korisnik toModel(KorisnikDTO dto) {
        return new Korisnik(dto.getIme(),dto.getPrezime(),dto.getEmail());
    }

    @Override
    public KorisnikDTO toDto(Korisnik entity) {
        return new KorisnikDTO(entity.getIme(),entity.getPrezime(),entity.getEmail());
    }

    public List<KorisnikDTO>toDtoKorisnikLista(List<Korisnik>lista){
        List<KorisnikDTO>temp = new ArrayList<>();
        for (Korisnik korisnik : lista){
            temp.add(toDto(korisnik));
        }
        return temp;
    }
}
