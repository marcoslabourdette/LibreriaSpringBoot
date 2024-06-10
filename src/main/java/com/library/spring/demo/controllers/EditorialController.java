/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;

import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.reportes.EditorialExporterPDF;
import com.library.spring.demo.servicios.EditorialService;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService es;
    @Autowired
    private EditorialExporterPDF EditorialExporterPDF;

    @PostMapping("/registrar")
    public String registrarEditorial(@RequestParam String nombre, @RequestParam String paisOrigen, RedirectAttributes redirectAttributes) {
        try {
            es.crearEditorial(nombre, paisOrigen);
            redirectAttributes.addFlashAttribute("exito", "¡Se registró la editorial correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        }
        return "redirect:/#editoriales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            es.eliminarEditorial(id);
            redirectAttributes.addFlashAttribute("exito", "¡Se eliminó la editorial correctamente! 👋🏼");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La editorial está registrada en un libro 😬");
        }
        return "redirect:/#editoriales";
    }

    @GetMapping("/reportes")
    public ResponseEntity<byte[]> descargarReporte(RedirectAttributes redirectAttributes) {
        String nombreArchivo = "Editoriales[LibrarySB].pdf";
        try {
            EditorialExporterPDF.exportar(es.listarEditoriales(), nombreArchivo);
            FileInputStream fis = new FileInputStream(nombreArchivo);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileCopyUtils.copy(fis, bos);
            byte[] contenidoPdf = bos.toByteArray();
            fis.close();
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            redirectAttributes.addFlashAttribute("exito", "Reporte descargado exitosamente");
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
