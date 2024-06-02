/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.servicios;

import com.library.spring.demo.entidades.Editorial;
import com.library.spring.demo.repositorios.EditorialRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author marco
 */
@Service
public class EditorialService {
    @Autowired
    private EditorialRepository er;
      public void crearEditorial(String nombre){
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        er.save(editorial);
    }
    
    public void eliminarEditorial(String id){
        er.deleteById(id);
    }
    public List<Editorial> listarEditoriales(){
        return er.findAll();
    }
}
