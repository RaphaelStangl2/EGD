package at.htl.testenMitMain;

import at.htl.model.User;
import at.htl.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class tests {


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = new User();
        user.setEmail("Ford");
        user.setPassword("rsheed");

        UserRepository userRepository = new UserRepository();
        //userRepository.check("sui", user.getPassword());
        System.out.println(userRepository.check("rsheed", user.getPassword()));
    }


}
