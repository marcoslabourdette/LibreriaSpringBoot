/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.spring.demo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Comparator;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author marco
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Libro {
    @Id
    @Column(nullable=false)
    private String isbn;
    @Column(nullable=false)
    private String titulo;
    @Column(nullable=false)
    private String descripcion;
    @Column(nullable=false)
    private String imagen_url;
    @Temporal(TemporalType.DATE)
    private Date alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editorial editorial;
    
      public static Comparator<Libro> compararPorTitulo = new Comparator<Libro>() {
        @Override
        public int compare(Libro libro1, Libro libro2) {
            return libro1.getTitulo().compareTo(libro2.getTitulo());
        }
    };
}
