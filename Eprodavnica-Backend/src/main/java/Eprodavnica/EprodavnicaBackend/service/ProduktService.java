package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.OcenaDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.TipFilterDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<Integer>ocene = new ArrayList<>();
        for (OcenaDTO ocenaDTO : filterDTO.getOcena()){
            if (ocenaDTO.isKoristiSe())
                ocene.add(ocenaDTO.getOcena());
        }
        List<String>tipovi = new ArrayList<>();
        for (TipFilterDTO tipFilterDTO : filterDTO.getTip()){
            if (tipFilterDTO.isKoristiSe())
                tipovi.add(tipFilterDTO.getNaziv());
        }
        List<Tip> listaTipova;
        if (tipovi.isEmpty()) {
            listaTipova = new ArrayList<>();
        }
        else {
            listaTipova = tipRepository.findByNazivIgnoreCaseIn(tipovi);
        }
        double od = -1;
        double do1 = -1;
        if (filterDTO.getCena()!=null)
            if (filterDTO.getCena().getDoCena()!=null && filterDTO.getCena().getOdCena()!=null){
                if (isNumeric(filterDTO.getCena().getDoCena()) && isNumeric(filterDTO.getCena().getOdCena())){
                    od = Double.parseDouble(filterDTO.getCena().getOdCena());
                    do1 = Double.parseDouble(filterDTO.getCena().getDoCena());
                }
            }
        return produktRepository.findByCustomCriteria(filterDTO.getNaziv(), od,do1,listaTipova,ocene,pageable);
        //return produktRepository.findByNazivContainingIgnoreCaseOrCenaIsBetweenOrListaTipovaInOrOcenaPunBrojInOrderByDatumPravljenja(pageable,filterDTO.getNaziv(),od,do1, listaTipova,ocene);
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
        entity.setDatumPravljenja(new Date());
        entity.setOcena(-1.0);
        return produktRepository.save(entity);
    }

    public void dodajUWishlist(String email, String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        if (produkt!=null){
            Korisnik korisnik = korisnikRepository.findByEmail(email);
            korisnik.getWishlist().add(produkt);
            korisnikRepository.save(korisnik);
        }
    }

    public Boolean daLiJeUWishlist(String email, String serijskiBroj){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return korisnik.getWishlist().contains(produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null));
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
