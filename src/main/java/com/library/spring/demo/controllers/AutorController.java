/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;

import com.library.spring.demo.servicios.AutorService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorService as;
    @PostMapping("/registrar")
    public String registrarAutor(@RequestParam String nombre, @RequestParam String nacionalidad,@RequestParam String nacimiento,@RequestParam String imagenUrl, RedirectAttributes redirectAttributes){
            try{
            as.crearAutor(nombre,nacionalidad,nacimiento,imagenUrl);
            redirectAttributes.addFlashAttribute("exito", "¡El autor se registró correctamente! 😎");
            }catch(Exception e){
                redirectAttributes.addFlashAttribute("error","Error al ingresar la fecha.");
            }
        return "redirect:/#autores";   
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable String id,RedirectAttributes redirectAttributes){
        try {
            as.eliminarAutor(id);
            redirectAttributes.addFlashAttribute("exito","¡Se eliminó el autor correctamente! 👋🏼");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","El autor está registrado en un libro 😬");
        }
         return "redirect:/#autores";   
    } 
}
