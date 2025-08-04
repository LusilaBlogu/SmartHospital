package com.example.lusilab.SmartHospital.util;

import com.example.lusilab.SmartHospital.entity.Doctor;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerator {

    public byte[] generateDoctorDetailsPdf(Doctor doctor) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter pdfWriter = new PdfWriter(baos);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);


            Paragraph title = new Paragraph("Raport Mjeku")
                    .setBold()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            document.add(createDetailParagraph("ID e Mjekut: ",
                    String.valueOf(doctor.getId())));

            document.add(createDetailParagraph("Emri: ", doctor.getFirstName()));
            document.add(createDetailParagraph("Mbiemri: ", doctor.getLastName()));
            document.add(createDetailParagraph("Email: ", doctor.getEmail()));
            document.add(createDetailParagraph("Specializimi: ", doctor.getDoctorRole()
            != null ? String.valueOf(doctor.getDoctorRole()) : "Nuk eshte specifikuar"));
            document.add(createDetailParagraph("Roli: ", doctor.getDoctorRole().toString()));

            document.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private Paragraph createDetailParagraph(String label, String value) {
        Paragraph paragraph = new Paragraph();
        paragraph(new Text(label).setBold());
        paragraph.add(value);
        paragraph.setFontSize(12);
        return paragraph;
    }

    private void paragraph(Text text) {
    }
}
