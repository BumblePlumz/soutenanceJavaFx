package fr.cda.scraping.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTools {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String getPasswordHashed(String pwd){
        return passwordEncoder.encode(pwd);
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
