/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.entidades.Editorial;
import com.library.spring.demo.entidades.Libro;
import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.repositorios.AutorRepository;
import com.library.spring.demo.repositorios.EditorialRepository;
import com.library.spring.demo.repositorios.LibroRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author marco
 */
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private EditorialRepository editorialRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Transactional
    public void crearLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam String descripcion, @RequestParam String autorID, @RequestParam String editorialID) throws MyException {
        validar(isbn,titulo,descripcion,autorID,editorialID);
        Optional<Editorial> editorialEncontrada = editorialRepository.findById(editorialID);
        Optional<Autor> autorEncontrado = autorRepository.findById(autorID);
        Libro libro = new Libro();
        if(editorialEncontrada.isPresent()){
        libro.setEditorial(editorialEncontrada.get());
        }
        if(autorEncontrado.isPresent()){
        libro.setAutor(autorEncontrado.get());
        }
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setDescripcion(descripcion);
        libro.setAlta(new Date());
        libroRepository.save(libro);
    }
    public List<Libro> listarLibros(){
        return libroRepository.findAll();
    }
    public void validar(Long isbn, String titulo, String descripcion, String autorID, String editorialID) throws MyException {
        if(isbn == null){
            throw new MyException("El isbn no puede ser nulo");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new MyException("El titulo no puede ser nulo o estar vacío.");
        }
        if(descripcion.isEmpty() || descripcion == null){
            throw new MyException("La descripcion no puede estar vacía");
        }
        
        
    }
}
