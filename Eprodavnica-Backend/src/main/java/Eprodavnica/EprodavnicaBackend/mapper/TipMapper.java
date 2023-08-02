package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.TipDTO;
import Eprodavnica.EprodavnicaBackend.model.Tip;

public class TipMapper implements MapperInterface<Tip, TipDTO> {
    @Override
    public Tip toModel(TipDTO dto) {
        return new Tip(dto.getNaziv(),dto.getBrojPojavljivanja());
    }

    @Override
    public TipDTO toDto(Tip entity) {
        return new TipDTO(entity.getNaziv(),entity.getBrojPojavljivanja());
    }
}
