import React, { useEffect, useState } from 'react'
//SERVICIOS
import usuarioServiceInstance from '../servicios/UsuarioService'
import servicioProveedorServiceInstance from '../servicios/ServicioProveedor';
//FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashCan } from '@fortawesome/free-solid-svg-icons';
//COMPONENTES
import AgregarServicio from '../componentes/Funciones/AgregarVariosServicios';


function MisServicios() {

  //MANEJAR ESTADOS DE LAS VARIABLES
  const [proveedor, setProveedor] = useState([]);
  const [serviciosRegistrados, setServiciosregistrados] = useState([]);

  useEffect(() => {
    usuarioServiceInstance.getProveedorInfo()
        .then((proveedor) => {
            setProveedor(proveedor);
        })
        .catch((error) => {
            console.error(error);
        });
  }, []);
  
  useEffect(() => {
    ActualizarLista();
  }, [proveedor]);

  const ActualizarLista = () => {
      if (proveedor) {
          servicioProveedorServiceInstance.getServiciosDisponibles(proveedor.idProveedor)
              .then((serviciosregistrados) => {
                  setServiciosregistrados(serviciosregistrados);
              })
              .catch((error) => {
                  console.error(error);
              });
      }
  }

  return (
    <div>
      {serviciosRegistrados.length === 0 ? (
        <div>
          <p>No tienes servicios registrados.</p>
        </div>
      ) : (
        <table>
            <thead>
              <tr>
                <th className='text-start py-2 pr-8 text-gray-700 text-sm'>Nombre del Servicio</th>
                <th className='text-start py-2 px-8 text-gray-700 text-sm'>Precio</th>    
                <th className='text-start py-2 px-8 text-gray-700 text-sm'>Negociable</th> 
                <th className='text-start py-2 pl-8 pr-4 text-gray-700'></th>
                <th className='text-start py-2 px-8 text-gray-700 text-sm'></th>
              </tr>
            </thead>
            <tbody>
              {serviciosRegistrados.map((servicio) => (
                <tr key={servicio.idServicio.idServicio}>
                  <td className='text-start py-2 pr-8 text-sm text-gray-700'>{servicio.idServicio.nombre}</td>
                  <td className='text-start py-2 px-8 text-sm text-gray-700'>S/ {parseFloat(servicio.precio).toFixed(2)}</td>
                  <td className='text-start py-2 px-8 text-sm text-gray-700'>{servicio.negociable ? 'Si' : 'No'}</td>
                  <td className='text-start py-2 pl-8 pr-4 text-sm text-gray-700'><button className='text-lg text-[#E8A477]'><FontAwesomeIcon icon={faEdit}/></button></td>
                  <td className='text-start py-2 px-8 text-sm text-gray-700'><button className='text-lg text-red-600'><FontAwesomeIcon icon={faTrashCan}/></button></td>
                </tr>
              ))}
            </tbody>
        </table>
      )}
      <AgregarServicio onServiciosAgregados={ActualizarLista()} />
    </div>
  );
}

export default MisServicios;
