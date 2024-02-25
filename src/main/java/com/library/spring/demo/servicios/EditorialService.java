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
    
    public List<Editorial> listarEditoriales(){
        return er.findAll();
    }
}
