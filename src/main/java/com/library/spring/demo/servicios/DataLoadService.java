/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.entidades.Editorial;
import com.library.spring.demo.entidades.Libro;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 *
 * @author marco
 */
@Service
public class DataLoadService {

    @Autowired
    private AutorService autorService;
    
    @Autowired
    private EditorialService editorialService;
    
    @Autowired
    private LibroService libroService;

    public void cargarDatos(ModelMap modelo) {
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        List<Libro> libros = libroService.listarLibros();
        Collections.sort(autores,Autor.compararNombre);
        Collections.sort(editoriales,Editorial.compararNombre);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("libros", libros);
    }
}
