import React, { useState, useEffect } from "react";
import UsuarioService from "../../servicios/Miembro/UsuarioService";

const InformacionProveedor = ({ children }) => {
    
    const [info, setInfo] = useState('');

    useEffect(() => {
        UsuarioService.getProveedorInfo()
            .then((response) => {
                setInfo(response);
            })
            .catch((error) => {
                console.error(error);
            })
    }, []);

    return <>{children(info)}</>;
    
}

export default InformacionProveedor;
