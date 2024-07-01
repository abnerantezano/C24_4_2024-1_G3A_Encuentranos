import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import DetalleContratoServiceInstance from '../../../../servicios/Miembro/DetalleContrato';
import ContratoServiceInstance from '../../../../servicios/Miembro/ContratosService';
import ConfirmarModal from '../General/ConfirmarAccion';
import NotificacionServiceInstance from '../../../../servicios/Miembro/NotificacionService';


function NuevoContrato({ idContrato, onClose }) {

    const [contrato, setContrato] = useState(null); 
    const [showConfirmModal, setShowConfirmModal] = useState(false);

    const { handleSubmit } = useForm();

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

    // FORMATEAR FECHA
    const formatDate = (date) => {
        const d = new Date(date);
        const day = d.getDate().toString().padStart(2, '0');
        const month = (d.getMonth() + 1).toString().padStart(2, '0');
        const year = d.getFullYear();
        return `${day}/${month}/${year}`;
    };

    // FORMATEAR HORA
    const formatTime = (time) => {
        const d = new Date(`1970-01-01T${time}`);
        let hours = d.getHours();
        const minutes = d.getMinutes().toString().padStart(2, '0');
        const period = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12 || 12;
        return `${hours}:${minutes} ${period}`;
    };

    const aceptarContrato = () => {
        const fechaActual = new Date().toISOString();
        const notificacion = {
            idContrato: { idContrato: parseInt(idContrato.idContrato)},
            idProveedor: { idProveedor: parseInt(contrato.idProveedor.idProveedor)},
            idCliente: { idCliente: parseInt(idContrato.idCliente.idCliente) },
            titulo: "¡A hora buena!",
            mensaje: "El proveedor aceptó tu solicitud",
            estado: "No visto",
            fhCreacion: fechaActual,
        };

        ContratoServiceInstance.putAceptarContrato(idContrato.idContrato);
        NotificacionServiceInstance.postNotificacion(notificacion);
        onClose();
    };

    const denegarContrato = () => {
        setShowConfirmModal(true);
    };

    const cancelarDenegacion = () => {
        setShowConfirmModal(false);
    };

    return (
        <div>
            <ConfirmarModal
                isOpen={showConfirmModal}
                message="¿Estás seguro de rechazar el contrato?"
                onConfirm={() => {
                    ContratoServiceInstance.putDenegarContrato(idContrato.idContrato);
                    const fechaActual = new Date().toISOString();
                    const notificacion = {
                        idContrato: { idContrato: parseInt(idContrato.idContrato)},
                        idProveedor: { idProveedor: parseInt(contrato.idProveedor.idProveedor)},
                        idCliente: { idCliente: parseInt(idContrato.idCliente.idCliente) },
                        titulo: "Lo sentimos...",
                        mensaje: "El proveedor rechazó tu solicitud",
                        estado: "No visto",
                        fhCreacion: fechaActual,
                    };
                    NotificacionServiceInstance.postNotificacion(notificacion);
                    setShowConfirmModal(false);
                    onClose();
                }}
                onCancel={cancelarDenegacion}
            />
            <div id="nuevoContrato_modal" className="fixed inset-0 z-30 flex justify-center items-center bg-black bg-opacity-30">
                <div className="relative p-10 w-auto">
                    <div className="relative bg-white rounded-lg shadow">
                        <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                            <h3 className="text-xl font-semibold text-gray-900">
                                Nuevo contrato
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
                        <form onSubmit={handleSubmit(aceptarContrato)}>
                            <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-10">
                                <div className=" mx-auto">
                                    {contrato && (
                                        <div>
                                            <div className='flex flex-row items-center justify-center mt-5 mb-10'>
                                                <img className="w-24 h-24 object-cover rounded-full border" src={idContrato.idCliente.idUsuario?.imagenUrl} />
                                                <div className='pl-4'>
                                                    <p className='text-sm text-[#787171]'>Cliente</p>
                                                    <h1 className='text-base text-[#635F5F] font-semibold'>{idContrato.idCliente.nombre?.split(' ')[0]} {idContrato.idCliente.apellidoPaterno}</h1>
                                                </div>
                                            </div>
                                            <div className='mb-5'>
                                                <div className='flex flex-row items-center justify-between'>
                                                    <p>{contrato.idServicio.nombre}</p>
                                                    <p className='text-[#BC7547] font-bold'>S/ {parseFloat(idContrato.precioFinal).toFixed(2)}</p>
                                                </div>
                                                <p className='font-semibold text-sm text-[#787171]'>{idContrato.estado}</p>
                                            </div>
                                            <div className='mb-5'>
                                                <h1 className='mb-2'>Fecha de servicio</h1>
                                                <div className='flex flex-row items-center justify-between mb-2'>
                                                    <p className='font-semibold text-sm text-[#787171]'>Inicio</p>
                                                    <p className='text-sm text-[#787171]'>{formatDate(idContrato.fechaInicio)} - {formatTime(idContrato.hiServicio)}</p>
                                                </div>
                                                <div className='flex flex-row items-center justify-between'>
                                                    <p className='font-semibold text-sm text-[#787171]'>Fin</p>
                                                    <p className='text-sm text-[#787171]'>{formatDate(idContrato.fechaFin)} - {formatTime(idContrato.hfServicio)}</p>
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
                                    Aceptar contrato
                                </button>
                                <button
                                    type="button"
                                    onClick={denegarContrato}
                                    className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10"
                                >
                                    Rechazar contrato
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default NuevoContrato;
