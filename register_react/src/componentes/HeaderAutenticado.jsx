import React, { useState, useEffect, useRef } from 'react'
import logo from "../imagenes/logo_texto_color.png"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faComment, faBell } from '@fortawesome/free-solid-svg-icons'
import DropdrownPerfil from './DropdrownPerfil'
import DropdownMensaje from './DropdownMensaje'
import DropdownNotificaciones from './DropdownNotificaciones'

export const HeaderAutenticado = () => {

    const [abrirMensaje, setAbrirMensaje] = useState(false);
    const [abrirNotificacion, setAbrirNotificacion] = useState(false);
    const [abrirPerfil, setAbrirPerfil] = useState(false);

    const mensajeRef = useRef(null);
    const notificacionRef = useRef(null);
    const perfilRef = useRef(null);

    //FUNCION PARA CERRAR SI SELECCIONO FUERA (MENSAJE)
    useEffect(() => {
        function handleClickOutside(event) {
            if (mensajeRef.current && !mensajeRef.current.contains(event.target)) {
                setAbrirMensaje(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [mensajeRef]);

    //FUNCION PARA CERRAR SI SELECCIONO FUERA (NOTIFICACION)
    useEffect(() => {
        function handleClickOutside(event) {
            if (notificacionRef.current && !notificacionRef.current.contains(event.target)) {
                setAbrirNotificacion(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [notificacionRef]);

    ////FUNCION PARA CERRAR SI SELECCIONO FUERA (PEFIL)
    useEffect(() => {
        function handleClickOutside(event) {
            if (perfilRef.current && !perfilRef.current.contains(event.target)) {
                setAbrirPerfil(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [perfilRef]);
    return (
        <div>
            <header className='relative'>
                <nav class="bg-white dark:bg-orange-900 w-full shadow-lg">
                    <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                        <a href="/inicio" class="flex items-center space-x-3 rtl:space-x-reverse">
                            <img src={logo} class="h-auto w-36 p-2" alt="Logo de texto" />
                        </a>
                        <div class="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                            <button type="button" onClick={() => setAbrirMensaje((prev) => !prev)} ref={mensajeRef} class="flex text-lg text-gray-400 md:me-0 pe-4">
                                <span class="sr-only">Mensajes</span>
                                <FontAwesomeIcon icon={faComment} />
                            </button>
                            <button type="button" onClick={() => setAbrirNotificacion((prev) => !prev)} ref={notificacionRef} class="flex text-lg md:me-0 text-gray-400 pe-4">
                                <span class="sr-only">Notificaciones</span>
                                <FontAwesomeIcon icon={faBell} />
                            </button>
                            <button type="button" onClick={() => setAbrirPerfil((prev) => !prev)} ref={perfilRef} class="flex text-sm rounded-full md:me-0">
                                <span class="sr-only">Menu del Perfil</span>
                                <img class="w-8 h-8 rounded-full" src="https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8dXNlcnxlbnwwfHwwfHx8MA%3D%3D" alt="Foto de usuario" />
                            </button>
                            <button data-collapse-toggle="navbar-user" type="button" class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-user" aria-expanded="false">
                                <span class="sr-only">Menu</span>
                                <FontAwesomeIcon icon={faBars} />
                            </button>
                        </div>
                        <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-user">
                            <ul class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
                                <li>
                                    <a href="/inicio" class="block py-2 px-3 text-white md:text-[#B4663F] md:p-0 md:dark:text-white" aria-current="page">Inicio</a>
                                </li>
                                <li>
                                    <a href="/servicios" class="block py-2 px-3 text-gray-900 md:hover:text-[#B4663F] md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Servicios</a>
                                </li>
                                <li>
                                    <a href="/precios" class="block py-2 px-3 text-gray-900 md:hover:text-[#B4663F] md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">Precios</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                {
                    abrirMensaje && (
                        <DropdownMensaje />
                    )
                }

                {
                    abrirNotificacion && (
                        <DropdownNotificaciones/>
                    )
                }
                {
                    abrirPerfil && (
                        <DropdrownPerfil />
                    )
                    
                }
            </header>
            
        </div>
        
    )
}

export default HeaderAutenticado;