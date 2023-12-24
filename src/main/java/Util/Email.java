package Util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Email {
    //    String pass="auogzmiqdjuyxbji";
    static final String form = "tilemarket2022@gmail.com";
    static final String password = "auogzmiqdjuyxbji";

    public static void sendMail(String to, String content, String subject) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //TLS 587 SSL 465
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        //create Authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(form, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        //Create Message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-tyoe", "text/HTML; charset=UTF-8");
            Address address = new InternetAddress(form, "Tile Market");
            msg.setFrom(address);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setContent(content, "text/HTML; charset=UTF-8");

            //send mail
            Transport.send(msg);
            System.out.println("Gửi Email thành công");
        } catch (MessagingException e) {
            System.out.println("Gửi Email không thành công");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Gửi Email không thành công");
            throw new RuntimeException(e);
        }
    }

    public static void sendMailWithAttachment(String to, String subject, String content, String publicKeyContent, String privateKeyContent) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //TLS 587 SSL 465
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create Authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(form, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // Create Message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            Address address = new InternetAddress(form, "Tile Market");
            msg.setFrom(address);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);

            // Create Multipart
            Multipart multipart = new MimeMultipart();

            // Add Content
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/HTML; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            // Add Public and Private Key
            addAttachment(multipart, publicKeyContent, privateKeyContent);

            // Set Content
            msg.setContent(multipart);

            // Send mail
            Transport.send(msg);
            System.out.println("Gửi Email thành công");
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Gửi Email không thành công");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addAttachment(Multipart multipart, String publicKeyContent, String privateKeyContent) throws MessagingException, IOException {
        // Create the content
        String content = "Public Key:\n" + publicKeyContent + "\n\nPrivate Key:\n" + privateKeyContent;

        // Create a DataSource from the content
        DataSource source = new ByteArrayDataSource(content, "text/plain");

        // Create a BodyPart and add the DataSource to it
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("key.txt");

        // Add the BodyPart to the Multipart
        multipart.addBodyPart(messageBodyPart);
    }
    public static void sendMailWithE_Sign(String to, String subject, String content, String eSign) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //TLS 587 SSL 465
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create Authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(form, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // Create Message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            Address address = new InternetAddress(form, "Tile Market");
            msg.setFrom(address);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);

            // Create Multipart
            Multipart multipart = new MimeMultipart();

            // Add Content
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/HTML; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            // Add Public and Private Key
            addAttachmentE_Sign(multipart, eSign);

            // Set Content
            msg.setContent(multipart);

            // Send mail
            Transport.send(msg);
            System.out.println("Gửi Email thành công");
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Gửi Email không thành công");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void addAttachmentE_Sign(Multipart multipart, String eSign) throws MessagingException, IOException {
        // Create the content
        String content = "Electronic Signature:\n" + eSign;

        // Create a DataSource from the content
        DataSource source = new ByteArrayDataSource(content, "text/plain");

        // Create a BodyPart and add the DataSource to it
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ElectronicSignature.txt");

        // Add the BodyPart to the Multipart
        multipart.addBodyPart(messageBodyPart);
    }


    public static void main(String[] args) {
//        boolean status = "1".equals("1");
//        System.out.println(status);
//        test
//        sendMailWithAttachment("tranbuituanngoc@gmail.com", "Key mã hóa","abcaabacaxaca", "abxasdacacasdasdc ác", "qưkjhcv123kjhcaushuirfkjajsdygwecjkabsch");
    }
}



