import React, { useState, useEffect } from "react";
import UsuarioService from "../../../servicios/Miembro/UsuarioService";

const InformacionDeUsuario = ({ children }) => {
    const [info, setInfo] = useState({});

    useEffect(() => {
        UsuarioService.getInfo()
            .then(response => {
                setInfo(response);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return <>{children(info)}</>;
}

export default InformacionDeUsuario;