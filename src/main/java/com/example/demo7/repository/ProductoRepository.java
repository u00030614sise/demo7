/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo7.model.Producto;

/**
 *
 * @author leoru
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}