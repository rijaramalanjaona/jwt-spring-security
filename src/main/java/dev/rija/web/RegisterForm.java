package dev.rija.web;

import lombok.Data;

/**
 * Utiliser RegsiterForm pour la creation de AppUser car cette operation necessite la confirmation de password
 *
 */
@Data
public class RegisterForm {
    private String username;
    private String password;
    private String repassword;

}
