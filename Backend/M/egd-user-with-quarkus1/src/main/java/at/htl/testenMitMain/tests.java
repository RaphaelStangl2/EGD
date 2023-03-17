package at.htl.testenMitMain;

import at.htl.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class tests {


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User();
        user.setUserName("Ford");
        user.setPassword("rsheed");
        System.out.println(user.getPassword());
    }


}
