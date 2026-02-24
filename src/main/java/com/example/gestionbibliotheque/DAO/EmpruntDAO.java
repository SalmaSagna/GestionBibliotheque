package com.example.gestionbibliotheque.DAO;

import com.example.gestionbibliotheque.Config.FactoryJPA;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Model.Livre;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javax.persistence.EntityManager;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;

public class EmpruntDAO {
    EntityManager em = FactoryJPA.getEntityManager();

    public void addEmprunt(Emprunt emprunt){
        em.getTransaction().begin();
        em.persist(emprunt);
        em.getTransaction().commit();
    }

    public List<Emprunt> getAllEmprunts(){
        return em.createQuery("SELECT e FROM Emprunt e " +
                        "JOIN FETCH e.etudiant " +//Avec FETCH, il force le chargement dans la première requête parce que LAZY ne charge qu'à l'appelle du get
                        "JOIN FETCH e.livre",
                Emprunt.class
        ).getResultList();
    }

    public List<Emprunt> getEmpruntsByStudent(int id){
        return em.createQuery("FROM Emprunt WHERE idEtudiant=:idEtudiant",Emprunt.class)
                .setParameter("idEtudiant",id).getResultList();
    }

    public List<Emprunt> getEmpruntsByBook(int id){
        return em.createQuery("FROM Emprunt WHERE idLivre=:idLivre",Emprunt.class)
                .setParameter("idLivre",id).getResultList();
    }

    public void genererPDFEmprunt(Emprunt emprunt, String nomFichier) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            // Stylisation
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
            Font subTitleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);

            // Contenu du PDF
            document.add(new Paragraph("NOM DE LA BIBLIOTHÈQUE : La bibliotheque de ISI", titleFont));
            document.add(new Paragraph("----------------------------------------------------------"));
            document.add(new Paragraph("FICHE D'EMPRUNT", titleFont));
            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Informations Étudiant:", subTitleFont));
            document.add(new Paragraph("Matricule : " + emprunt.getEtudiant().getMatricule(), normalFont));
            document.add(new Paragraph("Étudiant : " + emprunt.getEtudiant().getPrenom() + " " + emprunt.getEtudiant().getNom(), normalFont));
            document.add(new Paragraph("Email : " + emprunt.getEtudiant().getEmail(), normalFont));
            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Informations Livre:", subTitleFont));
            document.add(new Paragraph("ISBN : " + emprunt.getLivre().getIsbn(), normalFont));
            document.add(new Paragraph("Livre : " + emprunt.getLivre().getTitre(), normalFont));
            document.add(new Paragraph("Auteur : " + emprunt.getLivre().getAuteur(), normalFont));
            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Dates:", subTitleFont));
            document.add(new Paragraph("Date d'emprunt : " + emprunt.getDateEmprunt(), normalFont));
            document.add(new Paragraph("Date de retour prévue : " + emprunt.getDateRetour(), normalFont));
            document.add(new Paragraph("\n\n"));

            String dateGen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            document.add(new Paragraph("Document généré le : " + dateGen, FontFactory.getFont(FontFactory.TIMES_ITALIC, 10)));

            document.close();
            System.out.println("PDF généré avec succès : " + nomFichier);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void envoyerEmail(Emprunt emprunt, String cheminPdf) {
        // 1. Configuration du serveur SMTP (Exemple pour Gmail)
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

        String monEmail = "oumysali.sagna@gmail.com";
        String monMotDePasse = "oito elko rwug gqhs"; // Mot de passe d'application généré par Google

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(monEmail, monMotDePasse);
            }
        });

        try {
            // 2. Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(monEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emprunt.getEtudiant().getEmail()));
            message.setSubject("Fiche d'emprunt : " + emprunt.getLivre().getTitre());

            // 3. Corps du message (Texte personnalisé)
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String contenu = "Bonjour " + emprunt.getEtudiant().getPrenom() + " " + emprunt.getEtudiant().getNom() + ",\n\n"
                    + "Voiçi ci-joint la fiche de ton emprunt pour le livre : " + emprunt.getLivre().getTitre() + ".\n"
                    + "La date de retour prévue est le : " + emprunt.getDateRetour() + ".\n\n"
                    + "Cordialement,\nLa Bibliothèque de ISI.";
            messageBodyPart.setText(contenu);

            // 4. Ajout de la pièce jointe (Le PDF)
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(cheminPdf));

            // Assemblage du message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // 5. Envoi
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + emprunt.getEtudiant().getEmail());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
