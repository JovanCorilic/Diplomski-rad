package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Racun;
import Eprodavnica.EprodavnicaBackend.model.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RacunRepository extends JpaRepository<Racun,Integer> {
    Optional<Racun>findByBrojRacuna(String id);
    Boolean existsRacunByBrojRacuna(String id);
    Optional<Racun>findByKorpaIsTrueAndMusterija(Korisnik korisnik);
    Page<Racun>findByMusterija(Korisnik korisnik, Pageable pageable);

    @Query("SELECT r FROM Racun r " +
            "WHERE ( :od = -1.0 or :do1 = -1.0 or r.konacnaCena BETWEEN :od AND :do1 )" +
            "AND ( CAST( :odDatum AS date ) is null or CAST( :doDatum AS date ) is null or r.datumKreiranja BETWEEN :odDatum AND :doDatum)" +
            "AND ( :korisnik = r.musterija )")
    Page<Racun>findByCustomCriteriaMusterija(
            @Param("od") double od, @Param("do1") double do1, @Param("odDatum")Date odDatum,@Param("doDatum")Date doDatum,
            @Param("korisnik")Korisnik korisnik, Pageable pageable
    );

    @Query("SELECT r FROM Racun r " +
            "WHERE ( :od = -1.0 or :do1 = -1.0 or r.konacnaCena BETWEEN :od AND :do1 )" +
            "AND ( CAST( :odDatum AS date ) is null or CAST( :doDatum AS date ) is null or r.datumKreiranja BETWEEN :odDatum AND :doDatum)")
    Page<Racun>findByCustomCriteriaAdmin(
            @Param("od") double od, @Param("do1") double do1, @Param("odDatum")Date odDatum,@Param("doDatum")Date doDatum,
            Pageable pageable
    );
}
