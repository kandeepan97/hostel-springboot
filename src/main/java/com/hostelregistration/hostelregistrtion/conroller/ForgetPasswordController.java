package com.hostelregistration.hostelregistrtion.conroller;

import com.hostelregistration.hostelregistrtion.Utility;
import com.hostelregistration.hostelregistrtion.exceptions.WardenNotFoundException;
import com.hostelregistration.hostelregistrtion.model.Admin;
import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.payload.ForgetPasswordRequest;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.AdminRepository;
import com.hostelregistration.hostelregistrtion.repository.StudentRepository;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import com.hostelregistration.hostelregistrtion.services.WardenService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class ForgetPasswordController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private WardenRepository wardenRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private WardenService wardenService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/forget_password/{email}")
    public String processForgetPasswordForm(@PathVariable String email) throws UnsupportedEncodingException, MessagingException {
//        String email = request.getParameter("email");
        String token = RandomString.make(45);

        System.out.println("Email " + email);
        System.out.println("Token " + token);
        Warden warden = wardenRepository.findByEmail(email);
        Student student = studentRepository.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if (null != warden) {
            warden.setResetPasswordToken(token);
            wardenRepository.save(warden);
        } else if (null != student) {
            student.setResetPasswordToken(token);
            studentRepository.save(student);
        } else if (null != admin) {
            admin.setResetPasswordToken(token);
            adminRepository.save(admin);
        } else {
            return null;
        }
        String resetPasswordLink = "http://localhost:3000/resetForForgetPassword/" + token;
        System.out.println("Link " + resetPasswordLink);
        sendEmail(email,resetPasswordLink);
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
                + "<p><b><a href = " + resetPasswordLink + " > Change my password </a><b> </p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
