import React, { useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
//COMPONENTES
import Rol from '../Rol';
import InformacionProveedor from '../Informacion/InformacionProveedor';
import InformacionCliente from '../Informacion/InformacionCliente';

const DropDownPerfil = ({ onClose }) => {
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        onClose();
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [onClose]);

  return (
    <Rol>
      {(rol) => (
        <div>
          {rol.idTipo === 1 ? (
            <InformacionCliente>
              {(cliente) => {
                if (!cliente || !cliente.nombre) {
                  return null;
                }
        
                const primerNombre = cliente.nombre.split(' ')[0];
                const primerApellido = cliente.apellidoPaterno;
                const correo = cliente.idUsuario.correo;
        
                return (
                  <div ref={dropdownRef} className="bg-white flex flex-wrap justify-start text-sm list-none rounded-lg shadow-lg p-4 mt-4 z-10 absolute right-16 md:right-4 lg:right-4 xl:right-36 w-fit">
                    <div>
                      <div className="px-4 py-3 w-full">
                        <span className="block text-sm text-[#BC7547] font-bold">{primerNombre} {primerApellido}</span>
                        <span className="block text-sm text-gray-500 truncate dark:text-gray-400">{correo}</span>
                      </div>
                      <hr className='w-full'/>
                      <ul className="py-2">
                        <li>
                          <Link to="/configuracion/mi-cuenta" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mi cuenta</Link>
                        </li>
                        <li>
                          <Link to="/configuracion/mis-contratos" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mis contratos</Link>
                        </li>
                      </ul>
                      <hr />
                      <div className="py-2">
                        <Link to="#" className="block px-4 py-2 text-sm text-gray-600 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Cerrar sesión</Link>
                      </div>
                    </div>
                  </div>
                );
              }}
            </InformacionCliente>
          ) : rol.idTipo === 2 ? (
            <InformacionProveedor>
              {(proveedor) => {
                if (!proveedor || !proveedor.nombre) {
                  return null;
                }

                const primerNombre = proveedor.nombre.split(' ')[0];
                const primerApellido = proveedor.apellidoPaterno;
                const correo = proveedor.idUsuario.correo;

                return (
                  <div ref={dropdownRef} className="bg-white flex flex-wrap justify-start text-sm list-none rounded-lg shadow-lg p-4 mt-4 z-10 absolute right-16 md:right-4 lg:right-4 xl:right-36 w-fit">
                    <div>
                      <div className="px-4 py-3 w-full">
                        <span className="block text-sm text-[#BC7547] font-bold">{primerNombre} {primerApellido}</span>
                        <span className="block text-sm text-gray-500 truncate dark:text-gray-400">{correo}</span>
                      </div>
                      <hr className='w-full'/>
                      <ul className="py-2">
                        <li>
                          <Link to="/configuracion/mi-cuenta" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mi cuenta</Link>
                        </li>
                        <li>
                          <Link to="/configuracion/mis-servicios" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mis servicios</Link>
                        </li>
                        <li>
                          <Link to="/configuracion/mis-contratos" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mis contratos</Link>
                        </li>
                      </ul>
                      <hr />
                      <div className="py-2">
                        <Link to="#" className="block px-4 py-2 text-sm text-gray-600 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Cerrar sesión</Link>
                      </div>
                    </div>
                  </div>
                );
              }}
            </InformacionProveedor>
          ) : null }
        </div>
      )}
    </Rol>
  );
};

export default DropDownPerfil;
