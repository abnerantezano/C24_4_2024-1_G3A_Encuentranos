package com.proyecto.encuentranos.modelos;

import java.sql.Date;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="mensaje")
public class MensajeModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala", insertable = false, updatable = false)
    private SalaUsuarioModelo salaUsuario;

    @NotEmpty
    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Column(name = "hora_envio")
    private LocalDateTime horaEnvio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SalaUsuarioModelo getSalaUsuario() {
        return salaUsuario;
    }

    public void setSalaUsuario(SalaUsuarioModelo salaUsuario) {
        this.salaUsuario = salaUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(LocalDateTime horaEnvio) {
        this.horaEnvio = horaEnvio;
    }
}
