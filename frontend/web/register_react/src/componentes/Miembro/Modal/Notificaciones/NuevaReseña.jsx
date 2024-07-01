import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
// SERVICIOS
import DetalleContratoServiceInstance from '../../../../servicios/Miembro/DetalleContrato';
import NotificacionServiceInstance from '../../../../servicios/Miembro/NotificacionService';
// PRIME REACT
import { Rating } from 'primereact/rating';
import { InputTextarea } from 'primereact/inputtextarea';
import DetalleCalificacionServiceInstance from '../../../../servicios/Miembro/DetalleCalificacionService';

function NuevaReseña({ idContrato, onClose }) {
    const [calificacion, setCalificacion] = useState(null); 
    const [puntaje, setPuntaje] = useState(null);

    useEffect(() => {
        if (idContrato) {
            DetalleCalificacionServiceInstance.getCalificaciones(idContrato.idCliente)
                .then(calificaciones => {
                    console.log(calificaciones);
                    const reseñaEncontrado = calificaciones.find(reseña => reseña.idCliente === idContrato.idCliente);
                    setCalificacion(reseñaEncontrado);
                })
                .catch(error => {
                    console.error('Error al obtener detalles del contrato:', error);
                });
        }
    }, [idContrato]);

    console.log(calificacion);

    return (
        <div>
            <div id="nuevaReseña_modal" className="fixed inset-0 z-30 flex justify-center items-center bg-black bg-opacity-30">
                <div className="relative p-10 w-auto">
                    <div className="relative bg-white rounded-lg shadow">
                        <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                            <h3 className="text-xl font-semibold text-gray-900">
                                Reseña
                            </h3>
                            <button
                                onClick={onClose}
                                type="button"
                                className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center"
                            >
                                <svg
                                    className="w-3 h-3"
                                    aria-hidden="true"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 14 14"
                                >
                                    <path
                                        stroke="currentColor"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth="2"
                                        d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                                    />
                                </svg>
                                <span className="sr-only">Cerrar</span>
                            </button>
                        </div>
                        <form >
                            <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-10">
                                <div className="mx-auto">
                                    {idContrato && (
                                        <div>
                                            <div className='flex flex-col items-center justify-center mt-5 mb-5'>
                                                <img className="w-24 h-24 object-cover rounded-full border mb-4" src={idContrato.idCliente.idUsuario?.imagenUrl} />
                                                <div className='pl-4'>
                                                    <h1 className='text-base font-semibold'>{idContrato.idCliente.nombre} {idContrato.idCliente.apellidoPaterno} {idContrato.idCliente.apellidoMaterno}</h1>
                                                </div>
                                            </div>
                                            <div className='mb-2 flex justify-center'>
                                                <Rating value={puntaje} onChange={(e) => setPuntaje(e.value)} cancel={false} pt={{ root: 'focus:ring-0', onIcon: 'text-[#EBC351] focus:ring-0', offIcon: 'text-[#B7B7B7] focus:ring-0' }}/>
                                            </div>
                                            
                                            <div>
                                                <div className='my-5 flex flex-col'>
                                                    
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                            <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                                <button
                                    type="submit"
                                    className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                                >
                                    Aceptar
                                </button>
                                <button
                                    type="button"
                                    onClick={onClose}
                                    className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10"
                                >
                                    Cerrar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default NuevaReseña;
