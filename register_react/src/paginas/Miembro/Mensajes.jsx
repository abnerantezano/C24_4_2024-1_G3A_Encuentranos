import React from 'react';
//COMPONENTES
import MensajesCliente from '../../componentes/Miembro/Vistas/Mensajes/Cliente';
import MensajesProveedor from '../../componentes/Miembro/Vistas/Mensajes/Proveedor';
import Rol from '../../componentes/Miembro/Datos/Rol';

function Mensajes({ chat }) {
    return (
        <Rol>
            {(rol) => (
                <div>
                    {rol.idTipo === 1 ? (
                        <MensajesCliente chat={chat} />
                    ) : rol.idTipo === 2 ? (
                        <MensajesProveedor chat={chat} />
                    ) : null}
                </div>
            )}
        </Rol>
    );
}

export default Mensajes;
