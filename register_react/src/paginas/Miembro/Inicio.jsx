import React from 'react';
//COMPONENTES
import Rol from '../../componentes/Miembro/Datos/Rol';
import InicioProveedor from '../../componentes/Miembro/Vistas/Inicio/Proveedor';
import InicioCliente from '../../componentes/Miembro/Vistas/Inicio/Cliente';

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


