package at.htl.testenMitMain;

import at.htl.model.Users;
import at.htl.repository.UserRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.smallrye.common.annotation.Blocking;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

@QuarkusMain
@Transactional
public class Main {
    public static void main(String... args) {
        Quarkus.run(MyMain.class, "logIn");
    }

    public static class MyMain implements QuarkusApplication {
        @Inject
        UserRepository userRepository;

        @Override
        public int run(String... args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            if (args.length>0) {
                switch(args[0]) {

                    case "addUser":
                        addUser();
                        break;
                    case "logIn":
                        logIn();
                        break;
                    case "forGotPassword":
                        forGotPassword();
                        break;
                    case "changePassword":
                        changePassword();
                        break;

                }

            } else {
                System.out.println("Sie müssen was eingeben");

            }
            return 0;
        }



        private  void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Bitte geben Sie Ihre Email-Adresse ein:");
            String email = sc.nextLine();
            System.out.println("Bitte geben Sie Ihr altes Passwort ein:");
            String oldPassword = sc.nextLine();

            Users user = userRepository.findByEmail(email);

            if (user != null && userRepository.check(oldPassword, user.getPassword())) {
                System.out.println("Bitte geben Sie Ihr neues Passwort ein:");
                String newPassword = sc.nextLine();
                user.setPassword(UserRepository.getSaltedHash(newPassword));
                userRepository.update(user);
                System.out.println("Ihr Passwort wurde erfolgreich geändert.");
            } else {
                System.out.println("Email-Adresse oder Passwort ist ungültig. Bitte versuchen Sie es erneut.");
            }
        }






        private  void forGotPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Bitte geben Sie Ihre Email-Adresse ein:");
            String email = sc.nextLine();

            Users user = userRepository.findByEmail(email);
            if (user == null) {
                System.out.println("Es wurde kein Benutzer mit dieser Email-Adresse gefunden.");
                return;
            }

            String tempPassword = userRepository.generateTempPassword();
            user.setPassword(UserRepository.getSaltedHash(tempPassword));
            userRepository.update(user);

            //sendEmail(email, "Ihr neues temporäres Passwort", "Ihr temporäres Passwort lautet: " + tempPassword);
            System.out.println("Eine E-Mail mit Ihrem neuen temporären Passwort wurde an Sie gesendet.");
            System.out.println("Das Passwort lautet: " + tempPassword);
        }

        private void logIn() throws NoSuchAlgorithmException, InvalidKeySpecException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Bitte geben Sie Ihre Email-Adresse ein:");
            String email = sc.nextLine();
            System.out.println("Bitte geben Sie Ihr Passwort ein:");
            String password = sc.nextLine();
//
            Users user = null;

            try {
                 user = userRepository.findByEmail(email);
            }
            catch (Exception e){

            }
//
            try {
                String storedPassword = user.getPassword();
                if (user != null && UserRepository.check(password, storedPassword)) {
                    System.out.println("Login erfolgreich!");
                } else {
                    System.out.println("Login fehlgeschlagen. Bitte überprüfen Sie Ihre E-Mail-Adresse und das Passwort.");
                }
            }
            catch (Exception e){

            }
        }

        private void addUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Bitte geben Sie einen Benutzernamen ein:");
            String username = sc.nextLine();
            System.out.println("Bitte geben Sie eine Email-Adresse ein:");
            String email = sc.nextLine();
            System.out.println("Bitte geben Sie ein Passwort ein:");
            String password = sc.nextLine();

            Users user = new Users();
            user.setUserName(username);
            user.setEmail(email);
            user.setPassword(UserRepository.getSaltedHash(password));

            //
            //UserResource adden
            userRepository.addUser(user);

            System.out.println("Benutzer erfolgreich angelegt.");
        }

    }
}
