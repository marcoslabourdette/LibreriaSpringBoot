/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Autor;
import com.library.spring.demo.repositorios.AutorRepository;
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
    public List<Autor> listarAutores(){
        return ar.findAll();
    } 
}
