package Eprodavnica.EprodavnicaBackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenStateDTO {

    private String accessToken;
    private int expiresIn;

}
