import React, { useState, useEffect } from "react";
import UsuarioService from "../../servicios/UsuarioService";

const Cliente = ({ children }) => {
    
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

export default Cliente;


