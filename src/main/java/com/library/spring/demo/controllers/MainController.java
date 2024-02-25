/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.controllers;

import com.library.spring.demo.servicios.DataLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author marco
 */
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    public DataLoadService dataService;
    @GetMapping("/")
    public String index(ModelMap modelo){
        dataService.cargarDatos(modelo);
        return "index";
    }
}
