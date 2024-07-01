import React from 'react'
//COMPONENTES
import Rol from '../../componentes/Miembro/Datos/Rol'
import MisContratosProveedor from '../../componentes/Miembro/Vistas/MisContratos/Proveedor'
import MisContratosCliente from '../../componentes/Miembro/Vistas/MisContratos/Cliente'

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
