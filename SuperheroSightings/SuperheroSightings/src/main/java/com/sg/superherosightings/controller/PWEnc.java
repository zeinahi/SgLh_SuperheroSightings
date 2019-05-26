/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

/**
 *
 * @author zissah
 */
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class PWEnc {
//
//    public static void main(String[] args) {
//        String clearTxtPw = "password";
//        // BCrypt
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String hashedPw = encoder.encode(clearTxtPw);
//        System.out.println(hashedPw);
//    }
//}
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PWEnc {

    public static void main(String[] args) {
        String clearTxtPw = "password";
        // BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPw = encoder.encode(clearTxtPw);
        System.out.println(hashedPw);
    }
}