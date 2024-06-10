import React from 'react';
import InformacionDeUsuario from './InformacionDeUsuario';
import { Link } from 'react-router-dom';

const DropDownPerfil = () => (
    <InformacionDeUsuario>
        {(info) => {

            if (!info || !info.nombre) {
                return null;
            }

            const primerNombre = info.nombre.split(' ')[0];

            const primerApellido = info.apellidoPaterno;

            const correo = info.idUsuario.correo;

            return (
                <div className="bg-white flex flex-wrap justify-end text-sm list-none bg-white rounded-lg shadow-lg p-4 mt-4 z-10 absolute right-16 md:right-4 lg:right-4 xl:right-40">
                    <div>
                        <div className="px-4 py-3">
                            <span className="block text-sm text-[#BC7547] font-bold">{primerNombre} {primerApellido}</span>
                            <span className="block text-sm text-gray-500 truncate dark:text-gray-400">{correo}</span>
                        </div>
                        <hr />
                        <ul className="py-2">
                            <li>
                                <Link to="/perfil" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Perfil</Link>
                            </li>
                            <li>
                                <Link to="/miInformacionPersonal" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Información personal</Link>
                            </li>
                            <li>
                                <Link to="/misServicios" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mis servicios</Link>
                            </li>
                            <li>
                                <Link to="/misContratos" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Mis contratos</Link>
                            </li>
                        </ul>
                        <hr />
                        <div className="py-2"> 
                            <a href="#" className="block px-4 py-2 text-sm text-gray-600 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Cerrar sesión</a>
                        </div>
                    </div>
                </div>
            );
        }}
    </InformacionDeUsuario>
);

export default DropDownPerfil;


