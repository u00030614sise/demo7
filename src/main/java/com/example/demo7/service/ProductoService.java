/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo7.service;

import com.example.demo7.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo7.model.Producto;
import org.springframework.stereotype.Service;

/**
 *
 * @author leoru
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    /**
     * Funcion para listar la tabla persona
     *
     * @return
     */
    public List<Producto> listarTodas() {
        return repository.findAll();
    }
}
