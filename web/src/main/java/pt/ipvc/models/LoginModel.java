package pt.ipvc.models;

import javax.validation.constraints.*;

public class LoginModel {

    @NotBlank
    public String email;

    @NotBlank
    public String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

