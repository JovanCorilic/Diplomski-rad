package Eprodavnica.EprodavnicaBackend.service;

import Eprodavnica.EprodavnicaBackend.dto.Filter.FilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.OcenaDTO;
import Eprodavnica.EprodavnicaBackend.dto.Filter.TipFilterDTO;
import Eprodavnica.EprodavnicaBackend.dto.ImageModel;
import Eprodavnica.EprodavnicaBackend.model.*;
import Eprodavnica.EprodavnicaBackend.other.KonverterDatum;
import Eprodavnica.EprodavnicaBackend.other.ObavestenjeEmail;
import Eprodavnica.EprodavnicaBackend.repository.KorisnikRepository;
import Eprodavnica.EprodavnicaBackend.repository.ProduktRepository;
import Eprodavnica.EprodavnicaBackend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ProduktService {
    @Autowired
    private ProduktRepository produktRepository;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    private static final String LOKACIJA_SLIKA = "src/main/resources/slike/";

    public List<Produkt> findAll() {
        return produktRepository.findAll();
    }

    public Page<Produkt>findAllPageable(Pageable pageable){
        return produktRepository.findAllByOdobrenOdAdminaIsTrueAndOdobrenOdProdavcaIsTrueAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(pageable);
    }

    public Page<Produkt>findByIstorijaProdukataPageable(Pageable pageable,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByIstorijaKupacaContainsAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(korisnik,pageable);
    }

    public  Page<Produkt>findByWishlist(Pageable pageable,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByWishlistContainsAndProdavacOdobrenOdAdminaIsTrueOrderByDatumPravljenjaDesc(korisnik,pageable);
    }

    public  Page<Produkt>findByProdavac(Pageable pageable,String email){
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByProdavacOrderByDatumPravljenjaDesc(korisnik,pageable);
    }

    public Page<Produkt>findByAdmin(Pageable pageable){
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
    }

    public Page<Produkt>filterPageableIstorijaProdukata(FilterDTO filterDTO, Pageable pageable,String email){
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
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByCustomCriteriaIstorijaProdukata(filterDTO.getNaziv(), od,do1,listaTipova,ocene,korisnik,pageable);
    }

    public Page<Produkt>filterPageableWishlist(FilterDTO filterDTO, Pageable pageable,String email){
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
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByCustomCriteriaWishlist(filterDTO.getNaziv(), od,do1,listaTipova,ocene,korisnik,pageable);
    }

    public Page<Produkt>filterPageableProdavac(FilterDTO filterDTO, Pageable pageable,String email){
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
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return produktRepository.findByCustomCriteriaProdavac(filterDTO.getNaziv(), od,do1,listaTipova,ocene,korisnik,pageable);
    }

    public Page<Produkt>filterPageableAdmin(FilterDTO filterDTO, Pageable pageable){
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

        return produktRepository.findByCustomCriteriaAdmin(filterDTO.getNaziv(), od,do1,listaTipova,ocene,pageable);
    }

    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public Produkt findOne(String id) {
        return produktRepository.findBySerijskiBroj(id).orElse(null);
    }

    public Produkt create(Produkt entity, ImageModel img,String email) {
        entity.getListaTipova().replaceAll(tip -> tipRepository.findByNaziv(tip.getNaziv()).orElse(null));

        Korisnik korisnik = korisnikRepository.findByEmail(email);
        entity.setProdavac(korisnik);
        entity.setDatumPravljenja(new Date());
        entity.setOcena(-1.0);
        entity.setOcenaPunBroj(-1);
        entity.setId((int) produktRepository.count());
        entity.setOdobrenOdAdmina(true);
        entity.setOdobrenOdProdavca(true);

        entity.setSerijskiBroj(generisiRandomSerijskiBroj());
        while (produktRepository.existsProduktBySerijskiBroj(entity.getSerijskiBroj())){
            entity.setSerijskiBroj(generisiRandomSerijskiBroj());
        }

        entity = produktRepository.save(entity);
        if (!img.getName().equals("nema"))
            SacuvajSliku(img);
        return entity;
    }

    public void SacuvajSliku(ImageModel img){
        try {
            Files.write(Paths.get(LOKACIJA_SLIKA+img.getName()),img.getPicByte());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generisiRandomSerijskiBroj() {
        byte[] array = new byte[64];
        Random random = new Random();
        random.nextBytes(array);

        return Base64.getUrlEncoder().encodeToString(array);
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


    public Produkt update(Produkt entity, String id, ImageModel img) {
        Produkt produkt = produktRepository.findBySerijskiBroj(id).orElse(null);

        assert produkt != null;
        produkt.setCena(entity.getCena());
        produkt.setNaziv(entity.getNaziv());
        produkt.setDeskripcija(entity.getDeskripcija());

        entity.getListaTipova().replaceAll(tip -> tipRepository.findByNaziv(tip.getNaziv()).orElse(null));

        for (Tip tip : entity.getListaTipova()){
            if (!produkt.getListaTipova().contains(tip))
                produkt.getListaTipova().add(tip);
        }

        List<Tip>temp = new ArrayList<>(produkt.getListaTipova());
        for (Tip tip : produkt.getListaTipova()){
            if(!daLiSadrziTip(entity.getListaTipova(),tip)){
                temp.remove(tip);
            }
        }

        if ( entity.getAkcija()!=0 && entity.getAkcija()!=produkt.getAkcija() ){
            dodajAkciju(produkt.getSerijskiBroj(),entity.getAkcija());
        }

        produkt.setListaTipova(temp);

        if (!img.getName().equals("nema")) {
            produkt.setNazivSlike(entity.getNazivSlike());
            SacuvajSliku(img);
        }

        produkt = produktRepository.save(produkt);
        return produkt;
    }

    public boolean daLiSadrziTip(List<Tip> lista, Tip tip){
        for (Tip tip1 : lista){
            if (tip1.getNaziv().equals(tip.getNaziv()))
                return true;
        }
        return false;
    }

    public void delete(String id) {

    }

    public void izbaciIzWishlista(String serijskiBroj, String email){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        korisnik.getWishlist().remove(produkt);
        korisnikRepository.save(korisnik);
    }

    public Boolean daLiJeUIstorijiProdukata(String serijskiBroj, String email){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        return korisnikRepository.existsKorisnikByEmailAndIstorijaKupljenihProdukataContains(email,produkt);
    }

    public void povuciProizvodAdmin(String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        assert produkt != null;
        produkt.setOdobrenOdAdmina(false);
        produktRepository.save(produkt);

        String text = "Povučen je proizvod sa nazivom :"+produkt.getNaziv()+"\n"+
                "Povučen je : " +KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>(produkt.getWishlist());
        lista.add(produkt.getProdavac());
        slanjeObavestenja(lista,text,"Povlačenje produkta");
    }

    public void povuciProizvodProdavac(String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        assert produkt != null;
        produkt.setOdobrenOdProdavca(false);
        produktRepository.save(produkt);

        String text = "Povučen je proizvod sa nazivom :"+produkt.getNaziv()+"\n"+
                "Povučen je : " +KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>(produkt.getWishlist());
        slanjeObavestenja(lista,text,"Povlačenje produkta");

    }

    public void vratiProizvodAdmin(String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        assert produkt != null;
        produkt.setOdobrenOdAdmina(true);
        produktRepository.save(produkt);

        String text = "Vraćen je proizvod sa nazivom :"+produkt.getNaziv()+"\n"+
                "Vraćen je : " +KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>(produkt.getWishlist());
        lista.add(produkt.getProdavac());
        slanjeObavestenja(lista,text,"Vraćanje produkta");
    }

    public void vratiProizvodProdavac(String serijskiBroj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        assert produkt != null;
        produkt.setOdobrenOdProdavca(true);
        produktRepository.save(produkt);

        String text = "Vraćen je proizvod sa nazivom :"+produkt.getNaziv()+"\n"+
                "Vraćen je : " +KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik>lista = new ArrayList<>(produkt.getWishlist());
        slanjeObavestenja(lista,text,"Vraćanje produkta");

    }

    public void dodajAkciju(String serijskiBroj,Integer broj){
        Produkt produkt = produktRepository.findBySerijskiBroj(serijskiBroj).orElse(null);
        assert produkt != null;
        produkt.setAkcija(broj);
        produktRepository.save(produkt);

        String text = "Proizvod sa nazivom :"+produkt.getNaziv()+" . Data mu je akcija "+produkt.getAkcija()+"%\n"+
                "Akcija je data : " +KonverterDatum.konvertovanjeSamoDatumUString(KonverterDatum.konvertovanjeDateULocalDate(new Date()));
        List<Korisnik> lista = new ArrayList<>(produkt.getWishlist());
        lista.add(produkt.getProdavac());
        slanjeObavestenja(lista,text,"Akcija !");
    }

    @Async
    public void slanjeObavestenja(List<Korisnik> korisniks, String text, String naslov){
        ObavestenjeEmail thread = new ObavestenjeEmail(korisniks,javaMailSender,naslov,text);
        thread.start();
    }
}
