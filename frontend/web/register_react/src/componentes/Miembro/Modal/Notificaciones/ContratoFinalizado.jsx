import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
// SERVICIOS
import DetalleContratoServiceInstance from '../../../../servicios/Miembro/DetalleContrato';
import NotificacionServiceInstance from '../../../../servicios/Miembro/NotificacionService';
// PRIME REACT
import { Rating } from 'primereact/rating';
import { InputTextarea } from 'primereact/inputtextarea';
import CalificacionServiceInstance from '../../../../servicios/Miembro/CalificacionService';
import DetalleCalificacionServiceInstance from '../../../../servicios/Miembro/DetalleCalificacionService';

function ContratoFinalizado({ idContrato, onClose }) {
    const [contrato, setContrato] = useState(null); 
    const [puntaje, setPuntaje] = useState(null);
    const { handleSubmit, register, formState: { errors }, setError } = useForm();

    useEffect(() => {
        if (idContrato) {
            DetalleContratoServiceInstance.getDetallesContratos()
                .then(detallesContratos => {
                    const contratoEncontrado = detallesContratos.find(contrato => contrato.id.idContrato === idContrato.idContrato);
                    setContrato(contratoEncontrado);
                })
                .catch(error => {
                    console.error('Error al obtener detalles del contrato:', error);
                });
        }
    }, [idContrato]);

    const calificar = (data) => {

        const fechaActual = new Date().toISOString();

        const calificacion = {
            cliente: { idCliente: parseInt(idContrato.idCliente.idCliente) },
            calificacion: puntaje,
            comentario: data.comentario
        };

        const notificacion = {
            idContrato: { idContrato: parseInt(idContrato.idContrato) },
            idProveedor: { idProveedor: parseInt(contrato.idProveedor.idProveedor) },
            cliente: { idCliente: parseInt(idContrato.idCliente.idCliente) },
            titulo: "Tienes una nueva reseña",
            mensaje: "Mira los detalles de la reseña",
            estado: "No visto",
            fhCreacion: fechaActual,
        };

        CalificacionServiceInstance.postCalificacion(calificacion)
            .then((calificacion) => {
                console.log(calificacion);
                const idCalificacion = calificacion.idCalificacion;
                const detalleCalificacion = {
                    id: {
                        idCalificacion: parseInt(idCalificacion),
                        idProveedor: parseInt(contrato.idProveedor.idProveedor),
                        idServicio: parseInt(contrato.idServicio.idServicio)
                    },
                };
                DetalleCalificacionServiceInstance.postDetalleCalificacion(detalleCalificacion)
                    .then(() => {
                        NotificacionServiceInstance.postNotificacion(notificacion)
                            .then(() => {
                                onClose();
                            })
                            .catch(error => {
                                console.error('Error al enviar notificación:', error);
                            });
                            onClose();
                    })
                    .catch(error => {
                        console.error('Error al enviar detalle de calificación:', error);
                    });
            })
            .catch(error => {
                console.error('Error al enviar calificación:', error);
            });
    };

    return (
        <div>
            <div id="nuevoContrato_modal" className="fixed inset-0 z-30 flex justify-center items-center bg-black bg-opacity-30">
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
                        <form onSubmit={handleSubmit(calificar)}>
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
                                            {errors.puntaje && <span className="text-red-500 text-sm text-start">{errors.puntaje.message}</span>}
                                            <div>
                                                <div className='my-5 flex flex-col'>
                                                    <InputTextarea type="text" id="comentario" {...register("comentario", { required: true })} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" placeholder="Escribe tu experiencia" rows={5} cols={50} />
                                                    {errors.comentario && <span className="text-red-500 text-sm">Ingrese un comentario</span>}
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

export default ContratoFinalizado;
