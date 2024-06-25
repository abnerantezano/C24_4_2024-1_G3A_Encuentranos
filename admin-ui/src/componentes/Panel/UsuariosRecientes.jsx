import React, { useEffect, useState } from 'react';
import Reportes from '../../servicios/Reportes';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock } from '@fortawesome/free-regular-svg-icons';

function UsuariosRecientes() {
    const [usuarios, setUsuarios] = useState([]);

    const fetchUsuariosRecientes = () => {
        Reportes.getUsuariosRecientes()
            .then((usuarios) => {
                setUsuarios(usuarios);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    useEffect(() => {
        fetchUsuariosRecientes();

        const intervalId = setInterval(() => {
            fetchUsuariosRecientes();
        }, 60000); 

        return () => clearInterval(intervalId); 
    }, []);

    return (
        <div className='bg-white rounded-lg shadow-xl w-full p-10 mr-4 mb-10'>
            <h2 className="mb-5 font-bold text-gray-700 text-lg">Usuarios recientes</h2>
            <div>
                {usuarios.map((usuario) => {
                    const fechaCreacion = new Date(usuario.fh_creacion);

                    const horaCreacion = fechaCreacion.toLocaleTimeString('es-ES', {
                        hour: '2-digit',
                        minute: '2-digit'
                    });

                    return (
                        <div key={usuario.id_usuario} className='flex flex-wrap items-center justify-start'>
                            <div className='p-4 w-1/4'>
                                <img src={usuario.imagen_url} alt="Foto de perfil" className='border w-20 h-20 rounded-full'/>
                            </div>
                            <div className='flex flex-wrap items-center justify-between w-3/4'>
                                <div className='p-4 w-1/2'>
                                    <h2 className='text-base text-gray-800 font-semibold mb-2'>{usuario.correo}</h2>
                                    <p className='text-sm font-normal text-gray-600'>{usuario.tipo.nombre}</p>
                                </div>
                                <div className='p-4'>
                                    <FontAwesomeIcon icon={faClock} className='mx-4 text-[#BC7547]' />{horaCreacion}
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default UsuariosRecientes;
