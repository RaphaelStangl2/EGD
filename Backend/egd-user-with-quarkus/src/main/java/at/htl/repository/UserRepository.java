package at.htl.repository;

import at.htl.Classes.Mail;
import at.htl.model.Car;
import at.htl.model.UserCar;
import at.htl.model.Users;
import io.quarkus.mailer.Mailer;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class  UserRepository {

    @Inject
    EntityManager entityManager;

    //Mail
    @Inject
    Mailer mailer;

    @Inject
    UserCarRepository userCarRepository;

    //USERS
    public Users addUser(Users user) {
        entityManager.persist(user);
        return user;
    }

    public void removeUser(final long reservationId) {
        final UserCar userCar= userCarRepository.findByCarId(reservationId);
        entityManager.remove(userCar);

        final Users user = findById(reservationId);
        entityManager.remove(user);
    }

    public void updateUser(Users user){
        entityManager.merge(user);
    }

    public Users findById(long id) {
        return entityManager.find(Users.class, id);
    }



    //Password


    // algorithm for the hash function
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512"; // ist ein sicherer hash algorithm
    // number of iterations for the hash function
    private static final int ITERATIONS = 10000;
    // length of the generated hash
    private static final int KEY_LENGTH = 512;
    // pepper value
    private static final String PEPPER = "egd-is-king";



    public String sendTemporaryPassword(String email) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate a temporary password
        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);

        temporaryPassword = this.getSaltedHash(temporaryPassword);

        // Send Prozess mit email
        return temporaryPassword;

    }


    public static String getSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // hash the password with the salt

        //PBEKeySpec ist eine Klasse in Java, die verwendet wird, um eine Passwortbasierte
        // Schlüsselspezifikation (PBEKeySpec)
        // zu erstellen. Sie nimmt das Passwort als Zeichenfolge, den Salt-Wert, die Anzahl der
        // Iterationen und die Länge des
        // generierten Schlüssels als Argumente.
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        //SecretKeyFactory ist eine Klasse in Java, die verwendet wird, um geheime Schlüssel aus geheimen
        //Schlüsselspezifikationen (z.B. PBEKeySpec) zu erstellen oder geheime Schlüssel aus anderen geheimen
        //Schlüsseln zu importieren. Mit getInstance(ALGORITHM) wird eine neue Instanz von SecretKeyFactory
        //mit dem angegebenen Algorithmus erstellt.

        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        //verwendet die gegebenen PBEKeySpec und generiert einen geheimen Schlüssel.
        //getEncoded gibt es in byte

        //concatenate pepper and hash
        byte[] pepperHash = new byte[hash.length + PEPPER.getBytes().length];//länge vom gesamten hash

        //die Inhalte des Hash-Arrays und des Pepper-Arrays in ein neues Array kopiert.
        //Dadurch entsteht ein neues Array pepperHash welches sowohl Hash-Wert als auch Pepper enthält.
        System.arraycopy(hash, 0, pepperHash, 0, hash.length);
        System.arraycopy(PEPPER.getBytes(), 0, pepperHash, hash.length, PEPPER.getBytes().length);

        // return the salt and the hash as a single string
        return String.format("%s:%s", toHex(salt), toHex(pepperHash));
    }



    public static boolean check(String password, String stored) throws NoSuchAlgorithmException {
        // extract the salt and the hash from the stored string
        String[] parts = stored.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        // hash the password with the salt and pepper
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] testHash = new byte[0];

        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //concatenate pepper and testHash
        byte[] pepperTestHash = new byte[testHash.length + PEPPER.getBytes().length];
        System.arraycopy(testHash, 0, pepperTestHash, 0, testHash.length);
        System.arraycopy(PEPPER.getBytes(), 0, pepperTestHash, testHash.length, PEPPER.getBytes().length);

        // compare the two hashes
        return Arrays.equals(hash, pepperTestHash);
    }



    //fromHex(String hex) wandelt eine hexadezimale Zeichenfolge in ein Byte-Array um.
    //Hierfür wird jeder zwei Zeichen lange Teil des Strings in ein byte umgewandelt
    // und in das Array gespeichert.
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    //toHex(byte[] array) wandelt ein Byte-Array in eine hexadezimale Zeichenfolge um.
    //Hierfür wird eine BigInteger-Instanz mit dem Array erstellt und die toString-Methode
    //mit dem Radix 16 aufgerufen. Der resultierende String wird dann formatiert, um sicherzustellen,
    //dass er die richtige Länge hat.
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public List<Users> filterByName(String filter) {

        var users =  entityManager.createQuery("SELECT u FROM Users u where u.userName LIKE :filter", Users.class)
                .setParameter("filter", filter + "%") // fügt das Wildcard-Zeichen "%" hinzu
                .getResultList();

        return users;
    }

    public Users findByEmail(String email) {

            return entityManager.createQuery("SELECT u FROM Users u where u.email = : email", Users.class)
                    .setParameter("email", email)
                    .getSingleResult();

    }

    public Users findById(Long id) {

        return entityManager.createQuery("SELECT u FROM Users u where u.id = : id", Users.class)
                .setParameter("id", id)
                .getSingleResult();

    }

    public List<Users> getCarUsersById(long carId){//"select u from Users u where u.id in (select userCars.user.id from UserCar userCars where userCars.car.id =:carId)"
        var users =  entityManager.createQuery("select u from Users u where u.id in (select userCars.user.id from UserCar userCars where userCars.car.id =:carId)")
                .setParameter("carId", carId).getResultList();

        return users;
    }

    @Transactional
    public void update(Users user) {
        entityManager.merge(user);
    }

    public String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    public byte[] getDefaultImage() throws IOException {
        //default image holen und speichern
        Path imagePath = Path.of("src/main/resources/userDefaultImage.png");

        if(Files.exists(imagePath)){
            byte[] picture = Files.readAllBytes(imagePath);
            return  picture;
        }
        else{
            return null;
        }

    }

    public void sendEmailToAcount(Mail newMail){
        mailer.send(io.quarkus.mailer.Mail.withText(newMail.getMailTo().getEmail(), newMail.getSubject(), newMail.getText()));
    }


}
