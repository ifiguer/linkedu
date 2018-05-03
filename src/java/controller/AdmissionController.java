package controller;


import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.LoginBean;

@ManagedBean
@SessionScoped
public class AdmissionController implements Serializable {

    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean loginBean;

    private String essay;

    private String university;

    public AdmissionController() {
    }

    public String apply() {
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
            message.setSubject("Application Confirmation");
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
        return "apply-confirmation";
    }

    public String buildContent() {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for applying!<br><br>")
                .append("Your application has ben successfuly submitted to the university's admissions office. ")
                .append("Here's a summary of your application for your records.<br><br>")
                .append("Name: ").append(loginBean.getFullname()).append("<br>")
                .append("ACT: ").append(loginBean.getActScore()).append("<br>")
                .append("SAT: ").append(loginBean.getSatScore()).append("<br>")
                .append("Major: ").append(loginBean.getMajorOfChoice()).append("<br>")
                .append("Essay: ").append("<br><br>").append(essay);
        
        return sb.toString();
    }

    public String applicationView() {
        String u = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("university");
        setUniversity(u);
        return "apply";
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

}
