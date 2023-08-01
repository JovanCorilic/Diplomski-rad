package Eprodavnica.EprodavnicaBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Privilegije  implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "privilegijes", fetch = FetchType.LAZY)
    private Set<Uloga> ulogas;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
