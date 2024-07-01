import React, { useEffect, useState } from 'react';
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock } from '@fortawesome/free-solid-svg-icons';
// SERVICIOS
import Reportes from '../../servicios/Reportes';

function ContratosRecientes() {
    const [contratos, setContratos] = useState([]);

    const fetchContratosRecientes = () => {
        Reportes.getContratosRecientes()
            .then((contratos) => {
                setContratos(contratos);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    useEffect(() => {
        fetchContratosRecientes();

        const intervalId = setInterval(() => {
            fetchContratosRecientes();
        }, 60000); // 60000ms = 1 minuto

        return () => clearInterval(intervalId); // Limpia el intervalo al desmontar el componente
    }, []);

    return (
        <div className='bg-white rounded-lg shadow-xl w-full p-10 mr-4 mb-10'>
            <h2 className="mb-5 font-bold text-gray-700 text-lg">Contratos recientes</h2>
            <div>
                {contratos.map((contrato) => {
                    const fechaCreacion = new Date(contrato.fh_creacion);

                    const horaCreacion = fechaCreacion.toLocaleTimeString('es-ES', {
                        hour: '2-digit',
                        minute: '2-digit'
                    });

                    return (
                        <div key={contrato.id_contrato} className='flex flex-wrap items-center justify-start'>
                            <div className='flex flex-wrap items-center justify-between w-3/4'>
                                <div className='p-4 w-1/2'>
                                    <h2 className='text-base text-gray-800 font-semibold mb-2'>{contrato.estado}</h2>
                                    <p className='text-sm font-normal text-gray-600'>S/ {contrato.precio_final}</p>
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

export default ContratosRecientes;
