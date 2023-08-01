package Eprodavnica.EprodavnicaBackend.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifikacioniTokenSlanjeEmail extends Thread{
    private String token;
    private String adresa;
    private String email;
    private JavaMailSender javaMailSender;

    @Override
    public void run() {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verifikacija");
        simpleMailMessage.setText("Aktivacioni link: "+ "\r\n"+"https://localhost:4200"+adresa+"/"+token);
        javaMailSender.send(simpleMailMessage);
    }


}
