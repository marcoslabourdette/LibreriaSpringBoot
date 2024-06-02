/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.repositorios.AutorRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author marco
 */
@Service
public class AutorService {

    @Autowired
    private AutorRepository ar;

    public List<Autor> listarAutores() {
        return ar.findAll();
    }

    public void crearAutor(String nombre, String nacionalidad ,String nacimiento, String imagenUrl) {
        //Convierto String de fecha a Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
        // Cargo los atributos.
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        autor.setNacimiento(fechaNacimiento);
        autor.setImagenUrl(imagenUrl);
        autor.setAlta(true);
        ar.save(autor);
    }

    public void validar(String nombre, Date nacimiento) {

    }

    public void eliminarAutor(String id) {
        ar.deleteById(id);
    }
}
