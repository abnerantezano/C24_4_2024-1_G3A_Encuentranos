package com.proyecto.encuentranos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "prestador_servicio")
public class PrestadorServicioModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_prestador", referencedColumnName = "id_prestador")
    private PrestadorModel prestador;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    private ServicioModel servicio;

    @Column(name = "precio")
    private double precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrestadorModel getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorModel prestador) {
        this.prestador = prestador;
    }

    public ServicioModel getServicio() {
        return servicio;
    }

    public void setServicio(ServicioModel servicio) {
        this.servicio = servicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
