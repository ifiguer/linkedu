package controller;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.LoginBean;

@ManagedBean
@RequestScoped
public class Appointment {
    
    @ManagedProperty(value="#{LoginBean}")
    private LoginBean loginBean;

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    
    private String date;
    private String time;
    
    public Appointment() {
    }
    
    public String schedule() {
        String to = "";
        String from = "";
        String host = "outlook.office365.com";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("Appointment Confirmation");
            String content = buildContent();

            message.setContent(content,
                    "text/html");

            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "profile";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String buildContent() {
         StringBuilder sb = new StringBuilder();

        sb.append("Thank you for scheduling your appointment, ")
                .append(loginBean.getFullname())
                .append("!<br><br>")
                .append("Your appointment has been successfully shceduled with the university.<br>")
                .append("Please contact the unviversity if you have any questions or you need to cancel.<br><br>")
                .append("Date: ")
                .append(date)
                .append("<br>")
                .append("Time: ")
                .append(time)
                .append("<br><br>")
                .append("Sincerely, <br><br>")
                .append("LinkedU");
        
        return sb.toString();
    }
    
}
