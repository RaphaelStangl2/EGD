package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@ApplicationScoped
public class  UserRepository {

    @Inject
    EntityManager entityManager;


    //USERS
    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public void removeUser(final long reservationId) {
        final User user = findById(reservationId);
        entityManager.remove(user);
    }


    public User findById(long id) {
        return entityManager.find(User.class, id);
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

    public static String getSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // hash the password with the salt and pepper

        //PBEKeySpec ist eine Klasse in Java, die verwendet wird, um eine Passwortbasierte Schlüsselspezifikation (PBEKeySpec)
        // zu erstellen. Sie nimmt das Passwort als Zeichenfolge, den Salt-Wert, die Anzahl der Iterationen und die Länge des
        // generierten Schlüssels als Argumente.
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        //SecretKeyFactory ist eine Klasse in Java, die verwendet wird, um geheime Schlüssel aus geheimen
        //Schlüsselspezifikationen (z.B. PBEKeySpec) zu erstellen oder geheime Schlüssel aus anderen geheimen
        //Schlüsseln zu importieren. Mit getInstance(ALGORITHM) wird eine neue Instanz von SecretKeyFactory
        //mit dem angegebenen Algorithmus erstellt.
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();//verwendet die gegebenen PBEKeySpec und generiert einen geheimen Schlüssel.
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



    public static boolean check(String password, String stored) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // extract the salt and the hash from the stored string
        String[] parts = stored.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        // hash the password with the salt and pepper
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        //concatenate pepper and testHash
        byte[] pepperTestHash = new byte[testHash.length + PEPPER.getBytes().length];
        System.arraycopy(testHash, 0, pepperTestHash, 0, testHash.length);
        System.arraycopy(PEPPER.getBytes(), 0, pepperTestHash, testHash.length, PEPPER.getBytes().length);

        // compare the two hashes
        return Arrays.equals(hash, pepperTestHash);
    }


    //fromHex(String hex) wandelt eine hexadezimale Zeichenfolge in ein Byte-Array um.
    //Hierfür wird jeder zwei Zeichen lange Teil des Strings in ein byte umgewandelt und in das Array gespeichert.
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
}
