package Eprodavnica.EprodavnicaBackend.mapper;

import Eprodavnica.EprodavnicaBackend.dto.*;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Recenzija;
import Eprodavnica.EprodavnicaBackend.model.Tip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProduktMapper implements MapperInterface<Produkt, ProduktDTO> {
    private final TipMapper tipMapper;
    private static final String LOKACIJA_SLIKA = "src/main/resources/slike/";

    @Override
    public Produkt toModel(ProduktDTO dto) {
        List<Tip>tips = new ArrayList<>();
        for (TipDTO tipDTO : dto.getListaTipova()){
            tips.add(tipMapper.toModel(tipDTO));
        }
        return new Produkt(dto.getNaziv(),dto.getDeskripcija(),dto.getSerijskiBroj(),dto.getCena(),dto.getOcena(),
                dto.getDatumPravljenja(),dto.getAkcija(),dto.getBrojProdato(),dto.isOdobrenOdAdmina(),
                dto.getSlika().getName(), tips,new Korisnik(dto.getEmailProdavac()));
    }

    @Override
    public ProduktDTO toDto(Produkt entity) {
        List<TipDTO>tipDTOS = new ArrayList<>();
        for (Tip tip : entity.getListaTipova()){
            tipDTOS.add(tipMapper.toDto(tip));
        }
        ImageModel imageModel = UcitajImageModel(entity.getNazivSlike());

        return new ProduktDTO(entity.getNaziv(),entity.getDeskripcija(),entity.getSerijskiBroj(),entity.getCena(),
                entity.getOcena(),entity.getDatumPravljenja(),entity.getAkcija(),entity.getBrojProdato(),tipDTOS,
                entity.getProdavac().getEmail(),entity.isOdobrenOdAdmina(),imageModel);
    }

    public ProduktMiniDTO toDTOMini(Produkt entity){
        ImageModel imageModel = UcitajImageModel(entity.getNazivSlike());
        return new ProduktMiniDTO(entity.getNaziv(),entity.getSerijskiBroj(),entity.getCena(),entity.getOcena(),
                entity.getAkcija(),entity.isOdobrenOdAdmina(),imageModel);
    }

    public Produkt toMini(ProduktMiniDTO dto){
        return new Produkt(dto.getNaziv(),dto.getSerijskiBroj(),dto.getCena(),dto.getOcena(),dto.getAkcija(),dto.isOdobrenOdAdmina());
    }

    public List<ProduktMiniDTO> toDTOListaMiniProdukt(List<Produkt>lista){
        List<ProduktMiniDTO>temp = new ArrayList<>();
        for (Produkt produkt : lista){
            temp.add(toDTOMini(produkt));
        }
        return temp;
    }

    public List<ProduktDTO> toDTOListProdukt(List<Produkt>lista){
        List<ProduktDTO>temp = new ArrayList<>();
        for (Produkt produkt : lista){
            temp.add(toDto(produkt));
        }
        return temp;
    }

    public ImageModel UcitajImageModel(String naziv){
        ImageModel imageModel = new ImageModel();
        try {
            if (Files.exists(Paths.get(LOKACIJA_SLIKA+naziv))){
                byte[]temp = Files.readAllBytes(Paths.get(LOKACIJA_SLIKA+naziv));
                imageModel.setName(naziv);
                imageModel.setPicByte(temp);
            }else {
                imageModel.setName("nema");
            }
        }catch (IOException e){
            e.printStackTrace();
            imageModel.setName("nema");
        }
        return imageModel;
    }

    public ProduktMapper() {
        this.tipMapper = new TipMapper();
    }
}
