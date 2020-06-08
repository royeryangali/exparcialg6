package exparcialg6.demo.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class Application {

    @Autowired
    private JavaMailSender javaMailSender;

    public static void sendEmail(String clave, String mail) {

        final String username = "samuelcarolopez@gmail.com";
        final String password = "samuel321";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("samuelcarolopez@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Envio de Contraseña - Bodega de Samuca");
            message.setText("Querido Gestor, se le hace envio de su contraseña:" + clave);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}