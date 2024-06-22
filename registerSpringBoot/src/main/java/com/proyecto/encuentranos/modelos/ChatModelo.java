package com.proyecto.encuentranos.modelos;

import com.google.type.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private ProveedorModelo idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteModelo idCliente;

    @Column(nullable = false)
    private boolean eliminado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fh_creacion")
    private Date fh_creacion;
}
