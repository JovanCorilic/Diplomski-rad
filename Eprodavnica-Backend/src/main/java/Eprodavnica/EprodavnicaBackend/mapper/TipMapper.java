package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.util.ArrayList;
import java.util.List;

public class TipMapper implements MapperInterface<Tip, TipDTO> {
    @Override
    public Tip toModel(TipDTO dto) {
        return new Tip(dto.getNaziv());
    }

    @Override
    public TipDTO toDto(Tip entity) {
        return new TipDTO(entity.getNaziv());
    }

    public List<TipDTO>toDTOLista(List<Tip>lista){
        List<TipDTO>temp = new ArrayList<>();
        for (Tip tip : lista){
            temp.add(toDto(tip));
        }
        return temp;
    }
}
