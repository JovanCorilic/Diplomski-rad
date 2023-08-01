package Eprodavnica.EprodavnicaBackend.security.password;

import Eprodavnica.EprodavnicaBackend.security.Base64Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class HashSaltPassword {
    public HashSaltPassword() {
    }

    public void testIt() throws NoSuchAlgorithmException, IOException {
        // TODO: Izuciti hash & salt mehanizam zastite korisnicke lozinke i implementirati takav mehanizam prateci najbolje prakse

        String password = "!pA55w0rd0ne";

        System.out.println("===== Generisanje salt-a =====");
        byte[] salt = generateSalt();
        System.out.println(Arrays.equals(salt,Base64Utility.decode(Base64Utility.encode(salt))));
        System.out.println("Salt je: " + Base64Utility.encode(salt));
        //System.out.println("Salt je: " + salt);
        /*
        FileWriter writer = new FileWriter("MyFile.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write(Base64Utility.encode(salt)+"\n");

        bufferedWriter.close();

        FileReader reader = new FileReader("MyFile.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
        */



        System.out.println("===== Hesiranje lozinke =====");
        byte[] hashedPassword = hashPassword(password, salt);
        System.out.println("Hesirana lozinka je: " + Base64Utility.encode(hashedPassword));

        // Cuvanje salt-a
        String cuvanje = formatiranjeCuvanjeSifre(hashedPassword,salt);
        System.out.println("Cuvanje lozinke je: "+cuvanje);


        System.out.println("===== Korisnik unosi lozinku sa tastature =====");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Lozinka:");
        String attemptedPassword = "";
        try {
            attemptedPassword = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ekstrakcija salta
        byte[]tempSalt = ekstrakcijaSalta(cuvanje);
        System.out.println("Da li je jednako salt: "+Arrays.equals(tempSalt,salt));

        System.out.println("===== Vrsi se provera da li je unesena ispravna lozinka =====");
        if (authenticate(attemptedPassword, hashedPassword, salt)) {
            System.out.println("Uspesna prijava :)");
        } else {
            System.out.println("Neuspesna prijava :(");

        }
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[64];
        Random random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] addAll(final byte[] array1, byte[] array2) {
        byte[] joinedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        //byte[] spajanje = ArrayUtils.addAll(passwordBytes,salt);
        byte[] spajanje = addAll(passwordBytes,salt);
        return sha256.digest(spajanje);
    }

    public static String formatiranjeCuvanjeSifre(byte[] hashPassword, byte[] salt) throws IOException {
        String saltCuvanje = Base64Utility.encode(salt);
        if (saltCuvanje.length()!=88)
            throw new IOException("Nevalja salt");
        String temp = saltCuvanje.substring(0,44);
        String temp2 = saltCuvanje.substring(44,88);
        return temp+Base64Utility.encode(hashPassword)+temp2;
    }

    public static byte[] ekstrakcijaHesha(String HashSaltPassword) throws IOException {
        String temp = HashSaltPassword.substring(44,HashSaltPassword.length()-44);
        return Base64Utility.decode(temp);
    }

    public static byte[] ekstrakcijaSalta(String HashSaltPassword) throws IOException {
        String temp = HashSaltPassword.substring(0,44);
        String temp2 = HashSaltPassword.substring(HashSaltPassword.length()-44);
        return Base64Utility.decode(temp+temp2);
    }

    public static boolean authenticate(String attemptedPassword, byte[] storedPassword, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] attemptedPasswordBytes = attemptedPassword.getBytes(StandardCharsets.UTF_8);
        byte[] spajanjeAttemptedPassword = addAll(attemptedPasswordBytes,salt);
        byte[] attemptedPasswordDigest = sha256.digest(spajanjeAttemptedPassword);
        return Arrays.equals(attemptedPasswordDigest,storedPassword);
    }
}
