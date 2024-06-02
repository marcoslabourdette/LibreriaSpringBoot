/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;

import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.servicios.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @PostMapping("/registrar")
    public String registrarEditorial(@RequestParam String nombre, RedirectAttributes redirectAttributes){
        es.crearEditorial(nombre);
        redirectAttributes.addFlashAttribute("exito","¡Se registró la editorial correctamente! 😎");
        return "redirect:/#autores";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(@PathVariable String id, RedirectAttributes redirectAttributes){
        try{
         es.eliminarEditorial(id);
         redirectAttributes.addFlashAttribute("exito","¡Se eliminó la editorial correctamente! 👋🏼");
        }catch(Exception e){
         redirectAttributes.addFlashAttribute("error","La editorial está registrada en un libro 😬");
        }
        return "redirect:/#autores";
    }
    
}
