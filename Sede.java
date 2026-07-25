/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

public class Sede {
    private String nombreEstadio;
    private String ciudad;
    private int capacidad;

    public Sede(String nombreEstadio, String ciudad, int capacidad) {
        this.nombreEstadio = nombreEstadio;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
    }

    public String getNombreEstadio() { return nombreEstadio; }
    public void setNombreEstadio(String n) { this.nombreEstadio = n; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String c) { this.ciudad = c; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Override
    public String toString() {
        return nombreEstadio + " - " + ciudad;
        
    }
}
