package com.proyecto.encuentranos.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "chat")
public class ChatModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChat;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo idCliente;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fh_creacion", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fhCreacion;

    // Constructor vacío requerido por Hibernate
    public ChatModelo() {
    }

    // Constructor con parámetros
    public ChatModelo(ClienteModelo idCliente, ProveedorModelo idProveedor) {
        this.idCliente = idCliente;
        this.idProveedor = idProveedor;
    }
}
