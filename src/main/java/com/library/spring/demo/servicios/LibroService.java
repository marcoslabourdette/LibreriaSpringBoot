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
    public void crearLibro(String isbn, String titulo,
            String descripcion, String imagen, String autorID, String editorialID) throws MyException {
        validar(isbn, titulo, descripcion, imagen, autorID, editorialID);
        Optional<Editorial> editorialEncontrada = editorialRepository.findById(editorialID);
        Optional<Autor> autorEncontrado = autorRepository.findById(autorID);
        Libro libro = new Libro();
        if (editorialEncontrada.isPresent()) {
            libro.setEditorial(editorialEncontrada.get());
        }
        if (autorEncontrado.isPresent()) {
            libro.setAutor(autorEncontrado.get());
        }
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setDescripcion(descripcion);
        libro.setImagen_url(imagen);
        libro.setAlta(new Date());
        libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public void validar(String isbn, String titulo, String descripcion, String imagen, String autorID, String editorialID) throws MyException {
        if (isbn == null || !isbn.matches("\\d+")) {
            throw new MyException("El ISBN debe contener solo números");
        } else if (!libroRepository.findById(isbn).isEmpty()) {
            throw new MyException("El ISBN ingresado ya está registrado");
        }
        if (!imagen.equals("")) {
            if (!imagen.matches("^(ftp|http|https)://[^\\s/$.?#].[^\\s]*$")) {
                throw new MyException("El formato de la URL es incorrecto");
            }
            if (titulo.isEmpty() || titulo == null) {
                throw new MyException("El titulo no puede ser nulo o estar vacío");
            }
            if (descripcion.isEmpty() || descripcion == null) {
                throw new MyException("La descripcion no puede estar vacía");
            }
        }
    }

    @Transactional
    public void modificarLibro(String isbn, String titulo, String descripcion, String imagen, String autorID, String editorialID) throws MyException {
        if (!imagen.equals("")) {
            if (!imagen.matches("^(ftp|http|https)://[^\\s/$.?#].[^\\s]*$")) {
                throw new MyException("El formato de la URL es incorrecto");
            }
        }
        Optional<Libro> optionalLibro = libroRepository.findById(isbn);
        if (optionalLibro.isPresent()) {
            Libro libro = optionalLibro.get();
            libro.setTitulo(titulo);
            libro.setDescripcion(descripcion);
            libro.setImagen_url(imagen);
            Optional<Autor> optionalAutor = autorRepository.findById(autorID);
            Optional<Editorial> optionalEditorial = editorialRepository.findById(editorialID);

            if (optionalAutor.isPresent() && optionalEditorial.isPresent()) {
                libro.setAutor(optionalAutor.get());
                libro.setEditorial(optionalEditorial.get());
            } else {
                throw new MyException("El autor o la editorial proporcionados no existen ");
            }

            libroRepository.save(libro);
        } else {
            throw new MyException("El libro a modificar no existe");
        }
    }

    public void eliminarLibro(String isbn) {
        libroRepository.deleteById(isbn);
    }
}
