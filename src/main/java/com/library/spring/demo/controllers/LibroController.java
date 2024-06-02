/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;
import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.servicios.DataLoadService;
import com.library.spring.demo.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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


    @PostMapping("/registrar")
      public String registro(@RequestParam(required = false) String isbn, @RequestParam String titulo, @RequestParam String descripcion,@RequestParam String imagen, @RequestParam String autorID, @RequestParam String editorialID, RedirectAttributes redirectAttributes) {
        try {
            libroService.crearLibro(isbn, titulo, descripcion,imagen, autorID, editorialID);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se agregó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        }
        return "redirect:/#libros";
    }
    
   @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String isbn, @RequestParam String titulo, @RequestParam String descripcion,@RequestParam String imagen ,@RequestParam String autorID, @RequestParam String editorialID, ModelMap modelo,RedirectAttributes redirectAttributes) throws MyException {
    try {
        libroService.modificarLibro(isbn, titulo, descripcion, imagen, autorID, editorialID);
        redirectAttributes.addFlashAttribute("exito", "¡El libro se modificó correctamente! 😎");
    } catch (MyException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
    }
    dataService.cargarDatos(modelo);
    return "redirect:/#libros";
}

    @GetMapping("/eliminar/{isbn}")
    public String eliminarLibro(@PathVariable String isbn, RedirectAttributes redirectAttributes){
        try {
            libroService.eliminarLibro(isbn);            
            redirectAttributes.addFlashAttribute("exito", "¡El libro se eliminó correctamente! 👋🏼" );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/#libros";
    }
}
