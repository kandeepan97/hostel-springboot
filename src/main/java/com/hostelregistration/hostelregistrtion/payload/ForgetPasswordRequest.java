package com.hostelregistration.hostelregistrtion.payload;

import javax.validation.constraints.NotBlank;

public class ForgetPasswordRequest {
     @NotBlank(message="Email cannot be blank")
    private String email;
     private String requestURL;
     private Character servletPath;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Object getRequestURL() {
        return requestURL;
    }
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }


//    public CharSequence getServletPath() {
//        return servletPath;
//    }
//    public void setServletPath(Character servletPath){this.servletPath = servletPath;}
}
