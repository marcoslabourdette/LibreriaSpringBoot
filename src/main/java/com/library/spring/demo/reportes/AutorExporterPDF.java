/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.reportes;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.excepciones.MyException;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author marco
 */
@Service
public class AutorExporterPDF {
        public void exportar(List<Autor> autores, String nombreArchivo) throws MyException {
        Collections.sort(autores, Autor.compararNombre);
        if(autores.size() == 0){
            throw new MyException("No hay hay autores registrados para descargar");
        }
        try {
            PdfWriter writer = new PdfWriter(nombreArchivo);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Image logo = new Image(ImageDataFactory.create("classpath:/static/images/hoja.png"));        
            logo.scaleToFit(20, 20); 
                 
            Paragraph header = new Paragraph("Tus autores en LibrarySB ").setFontSize(20).setTextAlignment(TextAlignment.CENTER);
            header.add(logo);
            header.setMarginBottom(15);
            header.setMarginTop(15);
            
            Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
            table.addCell(new Cell().add(new Paragraph("Nombre")));
            table.addCell(new Cell().add(new Paragraph("Nacionalidad")));
            table.addCell(new Cell().add(new Paragraph("Fecha de nacimiento")));


            for (int i = 0; i < 3; i++) {
                Cell cell = table.getCell(0, i);
                cell.setBackgroundColor(new DeviceRgb(82, 141, 50));
                cell.setFontColor(new DeviceRgb(255, 255, 255));
                cell.setBold();
            }

            document.add(header);

            for (Autor autor : autores) {
                table.addCell(new Cell().add(new Paragraph(autor.getNombre())));
                table.addCell(new Cell().add(new Paragraph(autor.getNacionalidad())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(autor.getNacimiento()))));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new MyException("Error al crear PDF.");
        }
    }
}
