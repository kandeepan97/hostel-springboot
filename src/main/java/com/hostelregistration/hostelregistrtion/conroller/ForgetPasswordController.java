package com.hostelregistration.hostelregistrtion.conroller;

import com.hostelregistration.hostelregistrtion.Utility;
import com.hostelregistration.hostelregistrtion.exceptions.WardenNotFoundException;
import com.hostelregistration.hostelregistrtion.payload.ForgetPasswordRequest;
import com.hostelregistration.hostelregistrtion.services.WardenService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class ForgetPasswordController {

    @Autowired
    private WardenService wardenService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/forget_password")
    public String processForgetPasswordForm( HttpServletRequest request) throws WardenNotFoundException {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        System.out.println("Email " + email);
        System.out.println("Token " + token);

        try {
            wardenService.updateResetPasswordToken(token, email);

            //create reset password Link

            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token" + token;

            sendEmail(email,resetPasswordLink);

        }catch (WardenNotFoundException e){
            System.out.println("error");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return token;
       }

       private void sendEmail(String email,String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hostelword2021@gmail.com" ,"Hostel support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password.";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><b><a herf=\"" + resetPasswordLink + "\" > Change my password </a><b> </p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
