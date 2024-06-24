import React from 'react';
import Rol from '../componentes/Rol';
import InicioProveedor from '../componentes/Vistas/VInicio/Proveedor';
import InicioCliente from '../componentes/Vistas/VInicio/Cliente';

function Inicio() {
  
  return (
    <Rol>
      {(rol) => {
        return (
          <div>
            {rol.idTipo === 1 ? (
              <InicioCliente/>
            ) : rol.idTipo === 2 ? (
              <InicioProveedor/>
            ) : null }
          </div>
        );
      }}
    </Rol>
  );
}

export default Inicio;


