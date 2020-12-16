package com.hostelregistration.hostelregistrtion.payload;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTLoginSucessResponse  {
    private boolean sucess;
    private String token;
    private String role;


    public JWTLoginSucessResponse(boolean sucess, String token, String role) {
        this.sucess = sucess;
        this.token = token;
        this.role = role;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
    public String toString() {
        return "JWTLoginSucessResponse{" +
                "sucess=" + sucess +
                ", token='" + token + '\'' +
                ", role='" + role +
                '}';
    }

}
