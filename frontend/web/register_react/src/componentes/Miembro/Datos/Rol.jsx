import React, { useEffect, useState } from 'react'
import usuarioServiceInstance from '../../../servicios/Miembro/UsuarioService';

const Rol = ({children}) => {

    //ESTADO DEL USUARIO
    const [usuario, setUsuario] = useState([]);

    //ESTADO DEL TIPO DE USUARIO
    const [tipo, setTipo] = useState('');

    useEffect(() => {
        usuarioServiceInstance.getInfo()
            .then((usuario) => {
                setUsuario(usuario);
            })
            .catch((error) => {
                console.error('Error al obtener información del usuario: ', error);
            });
    }, []);

    useEffect(() => {
        usuarioServiceInstance.getTipoUsuario(usuario.idUsuario)
        .then((tipoUsuario) => {
            setTipo(tipoUsuario);
        })
        .catch((error) => {
            console.error('Error al obtener tipo de usuario: ' , error);
        });
    }, [usuario]);

    return (
        <>
            {tipo !== '' && children(tipo)}
        </>
    );
}

export default Rol
