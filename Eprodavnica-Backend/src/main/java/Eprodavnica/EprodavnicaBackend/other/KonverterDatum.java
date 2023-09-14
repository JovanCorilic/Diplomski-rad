package Eprodavnica.EprodavnicaBackend.other;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class KonverterDatum {
    public static String konvertovanjeUString(LocalDateTime temp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return temp.format(dateTimeFormatter);
    }

    public static LocalDateTime konvertovanjeUDateTime(String temp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(temp, dateTimeFormatter);
    }

    public static String konvertovanjeSamoDatumUString(LocalDate temp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return temp.format(dateTimeFormatter);
    }

    public static LocalDate konvertovanjeSamoDatumUDate(String temp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(temp, dateTimeFormatter);
    }

    public static LocalDate konvertovanjeDateULocalDate(Date date){
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
