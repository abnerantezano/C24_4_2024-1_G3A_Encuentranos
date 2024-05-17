import React, { useState, useEffect } from "react";
import InformacionDeUsuario from '../componentes/InformacionDeUsuario';
import ServicioProveedor from "../servicios/ServicioProveedor";

const Proveedor = ({ children }) => {
    
    const [proveedor, setProveedor] = useState('');

    useEffect(() => {
        InformacionDeUsuario().then(info => {
            ServicioProveedor.getServicioRegistrados(info.id)
                .then(response => {
                    setProveedor(response);
                })
                .catch(error => {
                    console.log(error);
                });
        });
    }, []);

    // Notificar al componente padre si el proveedor está autenticado
    useEffect(() => {
        if (proveedor !== '') {
            // Proveedor está autenticado, notificar al componente padre
            children(true);
        }
    }, [proveedor, children]);

    return null;
}

export default Proveedor;
