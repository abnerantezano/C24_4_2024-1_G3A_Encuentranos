import React from 'react'
import Rol from '../componentes/Rol'
import MisContratosProveedor from '../componentes/Vistas/VMisContratos/Proveedor'
import MisContratosCliente from '../componentes/Vistas/VMisContratos/Cliente'


function MisContratos() {

  return (
    <Rol>
      {(rol) => {
        return (
          <div>
            {rol.idTipo === 1 ? (
              <MisContratosCliente/>
            ) : rol.idTipo === 2 ? (  
              <MisContratosProveedor/>
            ) : null}
          </div>
        )
      }}
    </Rol>
  )
}

export default MisContratos
