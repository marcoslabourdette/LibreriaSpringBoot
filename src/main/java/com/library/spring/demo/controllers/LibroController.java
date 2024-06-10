/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;

import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.reportes.LibroExporterPDF;
import com.library.spring.demo.servicios.DataLoadService;
import com.library.spring.demo.servicios.LibroService;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author marco
 */
@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private DataLoadService dataService;
    @Autowired
    private LibroExporterPDF LibroPdfExporter;

    @PostMapping("/registrar")
    public String registro(@RequestParam(required = false) String isbn, @RequestParam String titulo, @RequestParam String descripcion, @RequestParam String imagen, @RequestParam String autorID, @RequestParam String editorialID, RedirectAttributes redirectAttributes) {
        try {
            libroService.crearLibro(isbn.trim(), titulo.trim(), descripcion.trim(), imagen.trim(), autorID, editorialID);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se agregó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        }
        return "redirect:/#libros";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String isbn, @RequestParam String titulo, @RequestParam String descripcion, @RequestParam String imagen, @RequestParam String autorID, @RequestParam String editorialID, ModelMap modelo, RedirectAttributes redirectAttributes) throws MyException {
        try {
            libroService.modificarLibro(isbn.trim(), titulo.trim(), descripcion.trim(), imagen.trim(), autorID, editorialID);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se modificó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        dataService.cargarDatos(modelo);
        return "redirect:/#libros";
    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminarLibro(@PathVariable String isbn, RedirectAttributes redirectAttributes) {
        try {
            libroService.eliminarLibro(isbn);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se eliminó correctamente! 👋🏼");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/#libros";
    }

    @GetMapping("/reportes")
    public ResponseEntity<byte[]> descargarReporte(RedirectAttributes redirectAttributes) {
        // Nombre temporal del archivo PDF
        String nombreArchivo = "Libros[LibrarySB].pdf";

        try {
            LibroPdfExporter.exportar(libroService.listarLibros(), nombreArchivo);
            FileInputStream fis = new FileInputStream(nombreArchivo);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileCopyUtils.copy(fis, bos);
            byte[] contenidoPdf = bos.toByteArray();
            fis.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            redirectAttributes.addFlashAttribute("exito", "Reporte descargado exitosamente ");
            return new ResponseEntity<>(contenidoPdf, headers, HttpStatus.OK);
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage() + " 😬");
            return ResponseEntity.ok().body(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Archivo no encontrado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error de lectura/escritura: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
