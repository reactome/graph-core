//package uk.ac.ebi.reactome.util;
//
//import org.apache.log4j.Logger;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
///**
// * Created by:
// *
// * @author Florian Korninger (florian.korninger@ebi.ac.uk)
// * @since 20.11.15.
// */
//public class MailUtil {
//
//    private static final Logger logger = Logger.getLogger(MailUtil.class);
//
//    private Properties properties;
//
//    public MailUtil(String host, Integer port){
//        properties = new Properties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.port", String.valueOf(port));
//    }
//
//    public void send(String from, String to, String subject, String text) {
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            // Set Subject: header field
//            message.setSubject(subject);
//            // Now set the actual message
//            message.setText(text);
//            // Send message
//            Transport.send(message);
//        } catch (MessagingException e) {
//            logger.error("Error sending notification message");
//        }
//    }
//}
//
