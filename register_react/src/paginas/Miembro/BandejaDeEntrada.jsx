import React from 'react';
//COMPONENTES
import Rol from '../../componentes/Miembro/Datos/Rol';
import BandejaDeEntradaCliente from '../../componentes/Miembro/Vistas/Bandeja de entrada/Cliente';
import BandejaDeEntradaProveedor from '../../componentes/Miembro/Vistas/Bandeja de entrada/Proveedor';

function BandejaDeEntrada() {

    return (
        <Rol>
            {(rol) => {
                return (
                <div className=''>
                    {rol.idTipo === 1 ? (
                    <BandejaDeEntradaCliente />
                    ) : rol.idTipo === 2 ? (  
                    <BandejaDeEntradaProveedor />
                    ) : null}
                </div>
                )
            }}
        </Rol>
    );
}

export default BandejaDeEntrada;
