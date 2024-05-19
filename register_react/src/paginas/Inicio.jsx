import React from 'react'
import imagen1 from '../imagenes/inicio/imagen1.png'
import InformacionDeUsuario from '../componentes/InformacionDeUsuario';

function Inicio() {
  return (
    <InformacionDeUsuario>
      {(info) => {
        <div className='w-full bg-white'>
          <div>
            <div>
              <h2>¡Bienvenido, {info.nombre}!</h2>
              <p></p>
            </div>
            <div>
              <img src={imagen1}/>
            </div>
          </div>
        </div>
      }}
    </InformacionDeUsuario>
  )
}

export default Inicio;

