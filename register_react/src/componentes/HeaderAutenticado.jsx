import React, { useState, useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';
import logo from "../imagenes/logo_texto_color.png";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faComment, faBell } from '@fortawesome/free-solid-svg-icons';
import DropDownPerfil from './DropDownPerfil';
import DropdownMensaje from './DropdownMensaje';
import DropdownNotificaciones from './DropdownNotificaciones';
import InformacionDeUsuario from './InformacionDeUsuario';

export const HeaderAutenticado = () => {
  const [abrirMensaje, setAbrirMensaje] = useState(false);
  const [abrirNotificacion, setAbrirNotificacion] = useState(false);
  const [abrirPerfil, setAbrirPerfil] = useState(false);

  const location = useLocation();

  useEffect(() => {
    setAbrirMensaje(false);
    setAbrirNotificacion(false);
    setAbrirPerfil(false);
  }, [location]);

  const mensajeRef = useRef(null);
  const notificacionRef = useRef(null);
  const perfilRef = useRef(null);

  return (
    <InformacionDeUsuario>
      {(info) => (
        <div>
          <header className='relative'>
            <nav className="bg-white dark:bg-orange-900 w-full shadow-lg">
              <div className="px-6 xl:px-36 flex flex-wrap items-center justify-between mx-auto py-4">
                <a href="/inicio" className="flex items-center space-x-3 rtl:space-x-reverse">
                  <img src={logo} className="h-auto w-36 p-2" alt="Logo de texto" />
                </a>
                <div className="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                  <button type="button" onClick={() => setAbrirMensaje((prev) => !prev)} ref={mensajeRef} className="flex text-lg text-gray-400 md:me-0 pe-4">
                    <span className="sr-only">Mensajes</span>
                    <FontAwesomeIcon icon={faComment} />
                  </button>
                  <button type="button" onClick={() => setAbrirNotificacion((prev) => !prev)} ref={notificacionRef} className="flex text-lg md:me-0 text-gray-400 pe-4">
                    <span className="sr-only">Notificaciones</span>
                    <FontAwesomeIcon icon={faBell} />
                  </button>
                  {info.idUsuario && (
                    <button type="button" onClick={() => setAbrirPerfil((prev) => !prev)} ref={perfilRef} className="flex text-sm rounded-full md:me-0">
                      <span className="sr-only">Menu del Perfil</span>
                      <img className="w-8 h-8 rounded-full object-cover" src={info.idUsuario.imagenUrl} alt="Foto de perfil" />
                    </button>
                  )}
                  <button data-collapse-toggle="navbar-user" type="button" className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-user" aria-expanded="false">
                    <span className="sr-only">Menu</span>
                    <FontAwesomeIcon icon={faBars} />
                  </button>
                </div>
                <div className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-user">
                  <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                    <li>
                      <a href="/inicio" className={`block py-2 px-3 ${location.pathname === '/inicio' ? 'text-[#B4663F]' : 'text-gray-900'} md:p-0 md:dark:text-white`}>Inicio</a>
                    </li>
                    <li>
                      <a href="/servicios" className={`block py-2 px-3 ${location.pathname === '/servicios' ? 'text-[#B4663F]' : 'text-gray-900'} md:p-0 md:dark:text-white`}>Servicios</a>
                    </li>
                    <li>
                      <a href="/precios" className={`block py-2 px-3 ${location.pathname === '/precios' ? 'text-[#B4663F]' : 'text-gray-900'} md:p-0 md:dark:text-white`}>Precios</a>
                    </li>
                  </ul>
                </div>
              </div>
            </nav>
            {abrirMensaje && <DropdownMensaje onClose={() => setAbrirMensaje(false)} />}
            {abrirNotificacion && <DropdownNotificaciones  onClose={() => setAbrirNotificacion(false)} />}
            {abrirPerfil && <DropDownPerfil onClose={() => setAbrirPerfil(false)} />}
          </header>
        </div>
      )}
    </InformacionDeUsuario>
  );
}

export default HeaderAutenticado;
