package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.CenaDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.TipFilterDTO;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProduktService implements ServiceInterface<Produkt>{
    @Autowired
    private ProduktRepository produktRepository;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public List<Produkt> findAll() {
        return produktRepository.findAll();
    }

    public Page<Produkt>findAllPageable(Pageable pageable){
        return produktRepository.findAll(pageable);
    }

    public Page<Produkt>filterPageable(FilterDTO filterDTO, Pageable pageable){
        boolean cenaKoristi = false;
        List<Produkt>listaCena = new ArrayList<>();

        boolean tipKoristi = false;
        List<Produkt>listaTip = new ArrayList<>();

        boolean ocenaKoristi = false;
        List<Produkt>listaOcena = new ArrayList<>();

        List<Produkt>lista = findAll();

        CenaDTO cenaDTO = filterDTO.getCena();
        if (cenaDTO.getDo() != null && cenaDTO.getOd() != null){
            if (!cenaDTO.getOd().isBlank() && !cenaDTO.getDo().isBlank()){
                if (isNumeric(cenaDTO.getOd()) && isNumeric(cenaDTO.getDo())){
                    double od = Double.parseDouble(cenaDTO.getOd());
                    double do1 = Double.parseDouble(cenaDTO.getDo());
                    if (od < do1){
                        listaCena = produktRepository.findByCenaIsGreaterThanEqualAndCenaIsLessThanEqual(od,do1);
                        cenaKoristi = true;
                    }
                }
            }
        }

        if (filterDTO.getTip() != null){
            if (!filterDTO.getTip().isEmpty()){
                List<String>tempLista = new ArrayList<>();
                for (TipFilterDTO tipFilterDTO : filterDTO.getTip()){
                    if (tipFilterDTO.isKoristiSe())
                        tempLista.add(tipFilterDTO.getNaziv());
                }
                List<Tip>tempLista2 = tipRepository.findByNazivIgnoreCaseIn(tempLista);

            }
        }
    }

    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }

    @Override
    public Produkt findOne(String id) {
        return produktRepository.findBySerijskiBroj(id).orElse(null);
    }

    @Override
    public Produkt create(Produkt entity) {
        if (produktRepository.existsProduktBySerijskiBroj(entity.getSerijskiBroj()))
            return null;
        for (Tip tip : entity.getListaTipova()){
            tip = tipRepository.findByNaziv(tip.getNaziv()).orElse(null);
        }
        entity.setProdavac(korisnikRepository.findByEmail(entity.getProdavac().getEmail()));
        return produktRepository.save(entity);
    }

    @Override
    public Produkt update(Produkt entity, String id) {
        Produkt produkt = produktRepository.findBySerijskiBroj(id).orElse(null);

        assert produkt != null;
        produkt.setCena(entity.getCena());
        produkt.setNaziv(entity.getNaziv());
        produkt.setDeskripcija(entity.getDeskripcija());

        for (Tip tip : entity.getListaTipova()){
            if (!produkt.getListaTipova().contains(tip))
                produkt.getListaTipova().add(tip);
        }

        List<Tip>temp = produkt.getListaTipova();
        for (Tip tip : produkt.getListaTipova()){
            if(!daLiSadrziTip(entity.getListaTipova(),tip)){
                temp.remove(tip);
            }
        }
        produkt.setListaTipova(temp);

        return produktRepository.save(produkt);
    }

    public boolean daLiSadrziTip(List<Tip> lista, Tip tip){
        for (Tip tip1 : lista){
            if (tip1.getNaziv().equals(tip.getNaziv()))
                return true;
        }
        return false;
    }

    @Override
    public void delete(String id) {

    }
}
