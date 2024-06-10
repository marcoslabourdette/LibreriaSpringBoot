/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author marco
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    @Column(nullable = false)
    private String nombre;
     @Column(nullable = false)
    private String paisOrigen;
    @Column(nullable = false)
    private boolean alta;
    
    public static Comparator<Editorial> compararNombre = new Comparator<Editorial>(){
        @Override
        public int compare(Editorial e1, Editorial e2){
            return e1.getNombre().compareTo(e2.getNombre());
        }
    };
}
