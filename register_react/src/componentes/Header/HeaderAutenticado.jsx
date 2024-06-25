import React, { useState, useEffect, useRef } from 'react';
import { Link, useLocation } from 'react-router-dom';
import logo from "../../imagenes/logo_texto_color.png";
//FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faComment, faBell } from '@fortawesome/free-solid-svg-icons';
//COMPONENTES
import DropDownPerfil from '../Dropdown/DropDownPerfil';
import DropdownNotificaciones from '../Dropdown/DropdownNotificaciones';
import InformacionProveedor from '../Informacion/InformacionProveedor';
import InformacionCliente from '../Informacion/InformacionCliente';
import Rol from '../Rol';

export const HeaderAutenticado = () => {

  const [abrirNotificacion, setAbrirNotificacion] = useState(false);
  const [abrirPerfil, setAbrirPerfil] = useState(false);

  const location = useLocation();

  useEffect(() => {
    setAbrirNotificacion(false);
    setAbrirPerfil(false);
  }, [location]);

  const notificacionRef = useRef(null);
  const perfilRef = useRef(null);

  return (
    <Rol>
      {(rol) => (
        <div>
          <header className='relative'>
            <nav className="bg-white dark:bg-orange-900 w-full shadow-lg">
              <div className="px-6 xl:px-36 flex flex-wrap items-center justify-between mx-auto py-4">
                <Link to="/inicio" className="flex items-center space-x-3 rtl:space-x-reverse">
                  <img src={logo} className="h-auto w-36 p-2" alt="Logo de texto" />
                </Link>
                <div className="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                  <Link to="/bandeja" className="flex text-lg text-gray-400 md:me-0 pe-4">
                    <span className="sr-only">Mensajes</span>
                    <FontAwesomeIcon icon={faComment} />
                  </Link>
                  <button type="button" onClick={() => setAbrirNotificacion((prev) => !prev)} ref={notificacionRef} className="flex text-lg md:me-0 text-gray-400 pe-4">
                    <span className="sr-only">Notificaciones</span>
                    <FontAwesomeIcon icon={faBell} />
                  </button>
                  {rol.idTipo === 1 ? (
                    <InformacionCliente>
                      {(cliente) => {
                        return (
                          <button type="button" onClick={() => setAbrirPerfil((prev) => !prev)} ref={perfilRef} className="flex text-sm rounded-full md:me-0">
                            <span className="sr-only">Imagen de perfil </span>
                            {cliente.idUsuario && (
                              <img className="w-8 h-8 rounded-full object-cover" src={cliente.idUsuario.imagenUrl} alt="Foto de perfil" />
                            )}
                          </button>
                        )
                      }}
                    </InformacionCliente>
                  ) : rol.idTipo === 2 ? (
                    <InformacionProveedor>
                      {(proveedor) => {
                        return (
                          <button type="button" onClick={() => setAbrirPerfil((prev) => !prev)} ref={perfilRef} className="flex text-sm rounded-full md:me-0">
                            <span className="sr-only">Imagen de perfil </span>
                            {proveedor.idUsuario && (
                              <img className="w-8 h-8 rounded-full object-cover" src={proveedor.idUsuario.imagenUrl} alt="Foto de perfil" />
                            )}
                          </button>
                        );
                      }}
                    </InformacionProveedor>
                  ) : null}
                  <button data-collapse-toggle="navbar-user" type="button" className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-user" aria-expanded="false">
                    <span className="sr-only">Menu</span>
                    <FontAwesomeIcon icon={faBars} />
                  </button>
                </div>
                <div className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-user">
                  <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                    <li>
                      <Link to="/inicio" className={`block py-2 px-3 ${location.pathname === '/inicio' ? 'text-[#B4663F] font-bold' : 'text-gray-900'} md:p-0`}>Inicio</Link>
                    </li>
                    <li>
                      <Link to="/servicios" className={`block py-2 px-3 ${location.pathname === '/servicios' ? 'text-[#B4663F] font-bold' : 'text-gray-900'} md:p-0 md:dark:text-white`}>Servicios</Link>
                    </li>
                    <li>
                      <Link to="/precios" className={`block py-2 px-3 ${location.pathname === '/precios' ? 'text-[#B4663F] font-bold' : 'text-gray-900'} md:p-0 md:dark:text-white`}>Precios</Link>
                    </li>
                  </ul>
                </div>
              </div>
            </nav>
            {abrirNotificacion && <DropdownNotificaciones  onClose={() => setAbrirNotificacion(false)} />}
            {abrirPerfil && <DropDownPerfil onClose={() => setAbrirPerfil(false)} />}
          </header>
        </div>
      )}
    </Rol>
  );
}

export default HeaderAutenticado;
