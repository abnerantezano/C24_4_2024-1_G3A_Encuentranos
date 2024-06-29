import React, { useState, useEffect } from "react";
import UsuarioService from "../../../servicios/Miembro/UsuarioService";

const InformacionCliente = ({ children }) => {
    
    const [info, setInfo] = useState('');

    useEffect(() => {
        UsuarioService.getClienteInfo()
            .then((response) => {
                setInfo(response);
            })
            .catch((error) => {
                console.error(error);
            })
    }, []);

    return <>{children(info)}</>;
}

export default InformacionCliente;


