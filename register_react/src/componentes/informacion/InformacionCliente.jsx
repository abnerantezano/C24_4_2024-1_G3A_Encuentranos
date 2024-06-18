import React, { useState, useEffect } from "react";
import ClienteService from "../../servicios/ClienteService";
import InformacionDeUsuario from '../InformacionDeUsuario';

const Cliente = ({ children }) => {
    
    const [cliente, setCliente] = useState('');

    useEffect(() => {
        InformacionDeUsuario().then(info => {
            ClienteService.getCliente(info.id)
                .then(response => {
                    setCliente(response);
                })
                .catch(error => {
                    console.log(error);
                });
        });
    }, []);

    useEffect(() => {
        if (cliente !== '') {
            children(true);
        }
    }, [cliente, children]);

    return null;
}

export default Cliente;


