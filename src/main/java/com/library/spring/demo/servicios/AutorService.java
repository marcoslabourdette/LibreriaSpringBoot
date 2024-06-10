/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.excepciones.MyException;
import com.library.spring.demo.repositorios.AutorRepository;
import jakarta.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
public class AutorService {

    @Autowired
    private AutorRepository ar;

    public List<Autor> listarAutores() {
        return ar.findAll();
    }

    public void crearAutor(String nombre, String nacionalidad, String nacimiento, String bio, String imagenUrl) throws MyException{
        validar(nombre, nacionalidad,imagenUrl, bio);
        //Convierto String de fecha a Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
        // Cargo los atributos.
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        autor.setNacimiento(fechaNacimiento);
        autor.setBio(bio);
        autor.setImagenUrl(imagenUrl);
        autor.setAlta(true);
        ar.save(autor);
    }

    public void validar(String nombre, String nacionalidad, String imagenUrl, String bio) throws MyException {
        if (nombre == null || !nombre.matches("^[a-zA-ZÀ-ÿ\\s']*$")) {
            throw new MyException("El nombre no puede contener números, o carácteres especiales");
        } 
        else if (nacionalidad == null || nacionalidad.matches("\\d+")) {
            throw new MyException("La nacionalidad no puede contener números");
        } else if (bio == null) {
            throw new MyException("La biografía no puede ser nula");
        }
        else if(!bio.matches("^(ftp|http|https)://[^\\s/$.?#].[^\\s]*$")){
            throw new MyException("El formato de biografía URL es incorrecto");
        }
        else if(!imagenUrl.matches("^(ftp|http|https)://[^\\s/$.?#].[^\\s]*$")){
         throw new MyException("El formato de la iamgen URL es incorrecto");
        }
    }

    @Transactional
    public void modificarAutor(String id, String nombre, String nacionalidad, String nacimiento, String imagenUrl, String bio) throws MyException {
        validar(nombre, nacionalidad,imagenUrl, bio);
        Optional<Autor> optionalAutor = ar.findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            autor.setNombre(nombre);
            autor.setNacionalidad(nacionalidad);
            autor.setNacimiento(fechaNacimiento);
            autor.setImagenUrl(imagenUrl);
            autor.setBio(bio);
            ar.save(autor);
        } else {
            throw new MyException("El autor a modificar no existe");
        }
    }

    public void eliminarAutor(String id) {
        ar.deleteById(id);
    }
}
