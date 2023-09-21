package Eprodavnica.EprodavnicaBackend.other;

import Eprodavnica.EprodavnicaBackend.model.Korisnik;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObavestenjeEmail extends Thread{
    private List<Korisnik>listaKorisnika;
    private JavaMailSender javaMailSender;
    private String subject;
    private String text;

    @Override
    public void run() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        System.out.println(listaKorisnika.size());
        for (Korisnik korisnik : listaKorisnika){
            simpleMailMessage.setTo(korisnik.getEmail());
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }

    }
}
