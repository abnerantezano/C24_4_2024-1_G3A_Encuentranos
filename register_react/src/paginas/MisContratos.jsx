import React, { useEffect, useState } from 'react'
import Configuracion_perfil from '../componentes/Diseños/Configuracion_perfil';
import ServicioProveedor from '../servicios/ServicioProveedor';
import InformacionProveedor from '../componentes/Informacion/InformacionProveedor';

function MisContratos() {

  const [servicios, setServicios] = useState([]);
  const [serviviosByP, setServiciosByP] = useState([]);

  return (
    <h1>hola contratoss</h1>
  )
}

export default MisContratos
