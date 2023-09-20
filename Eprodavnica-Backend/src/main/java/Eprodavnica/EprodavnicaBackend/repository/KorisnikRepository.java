package Eprodavnica.EprodavnicaBackend.repository;

import Eprodavnica.EprodavnicaBackend.dto.KorisnikDTO;
import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import Eprodavnica.EprodavnicaBackend.model.Produkt;
import Eprodavnica.EprodavnicaBackend.model.Uloga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik,Integer> {
    Korisnik findByEmail(String email);

    Boolean existsKorisnikByEmail(String email);
    List<Korisnik> findAllByUlogeContainingAndPotvrdjenIsTrueAndOdobrenOdAdminaIsFalse(Uloga uloga);

    Page<Korisnik>findByUlogeContainingAndPotvrdjenIsTrueOrderByEmailAsc(Uloga uloga, Pageable pageable);

    Boolean existsKorisnikByEmailAndIstorijaKupljenihProdukataContains(String email, Produkt produkt);

    Page<Korisnik> findAll(Specification<Korisnik> korisnikSpecification, Pageable pageable);

    default Page<Korisnik> findByCustomCriteria(KorisnikDTO korisnik, Uloga uloga, Pageable pageable) {
        return findAll((Specification<Korisnik>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (uloga != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(uloga, root.get("uloge")));
            }

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.isTrue(root.get("potvrdjen")));

            if (korisnik != null) {
                if (korisnik.getEmail() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + korisnik.getEmail().toLowerCase() + "%"));
                }

                if (korisnik.getIme() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("ime")), "%" + korisnik.getIme().toLowerCase() + "%"));
                }

                if (korisnik.getPrezime() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("prezime")), "%" + korisnik.getPrezime().toLowerCase() + "%"));
                }
            }

            return predicate;
        }, pageable);
    }

}
