import React from 'react';
import { useForm } from 'react-hook-form';
import ContratosService from '../../../servicios/Contratos'; 

const EditarModal = ({ contrato, closeModal, funcion }) => {
    const { register, handleSubmit, formState: { errors }, setValue, watch } = useForm({
        defaultValues: {
            estado: contrato.estado,
            precio_final: parseFloat(contrato.precio_final).toFixed(2), 
            fecha_inicio: contrato.fecha_inicio,
            fecha_fin: contrato.fecha_fin,
            hi_servicio: contrato.hi_servicio,
            hf_servicio: contrato.hf_servicio
        },
    });


    const precioFinalValue = watch("precio_final");

    const editarContrato = (data) => {
        const datos = {
            id_cliente: contrato.id_cliente,
            estado: data.estado,
            precio_final: Number(data.precio_final),
            fecha_inicio: data.fecha_inicio,
            fecha_fin: data.fecha_fin,
            hi_servicio: data.hi_servicio,
            hf_servicio: data.hf_servicio,
        };

        console.log("Datos antes de enviar:", datos);

        ContratosService.patchContrato(datos, contrato.id_contrato)
            .then((response) => {
                console.log(response);
                console.log("Contrato actualizado");
                closeModal();
                funcion();
            })
            .catch((error) => {
                console.log(error);
            });
    };

    console.log("Precio final en el formulario:", precioFinalValue); // Log precio_final from watch

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 w-full">
            <div className="bg-white p-8 rounded-lg shadow-lg w-1/2">
                <h2 className="text-xl mb-4 font-bold text-gray-800">Editar contrato</h2>
                <form onSubmit={handleSubmit(editarContrato)}>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Estado</label>
                        <input
                            name="estado"
                            {...register("estado", { required: true })}
                            type="text"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
                            placeholder="Estado del contrato"
                        />
                        {errors.estado && (
                            <span className="text-red-500 text-sm">Ingrese el estado del contrato</span>
                        )}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Precio</label>
                        <input
                            name="precio_final"
                            {...register("precio_final", { required: true })}
                            type="number" // Ensure it's treated as a number
                            step="0.01"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
                            placeholder="Precio del contrato"
                        />
                        {errors.precio_final && (
                            <span className="text-red-500 text-sm">Ingrese el precio final del contrato</span>
                        )}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Duración</label>
                        <div className='flex flex-wrap'>
                            <div className='pr-2 w-1/2'>
                                <input
                                    name="fecha_inicio"
                                    {...register("fecha_inicio", { required: true })}
                                    type="date"
                                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
                                    placeholder="Fecha de inicio"
                                />
                                {errors.fecha_inicio && (
                                    <span className="text-red-500 text-sm">Ingrese la fecha de inicio del contrato</span>
                                )}
                            </div>
                            <div className='pl-2 w-1/2'>
                                <input
                                    name="fecha_fin"
                                    {...register("fecha_fin", { required: true })}
                                    type="date"
                                    className="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 mb-5"
                                    placeholder="Fecha de fin"
                                />
                                {errors.fecha_fin && (
                                    <span className="text-red-500 text-sm">Ingrese la fecha de fin del contrato</span>
                                )}
                            </div>
                        </div>
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Horario</label>
                        <div className='flex flex-wrap'>
                            <div className='pr-2 w-1/2'>
                                <input
                                    name="hi_servicio"
                                    {...register("hi_servicio", { required: true })}
                                    type="time"
                                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
                                    placeholder="Hora de inicio"
                                />
                                {errors.hi_servicio && (
                                    <span className="text-red-500 text-sm">Ingrese la hora de inicio del contrato</span>
                                )}
                            </div>
                            <div className='pl-2 w-1/2'>
                                <input
                                    name="hf_servicio"
                                    {...register("hf_servicio", { required: true })}
                                    type="time"
                                    className="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 mb-5"
                                    placeholder="Hora de fin"
                                />
                                {errors.hf_servicio && (
                                    <span className="text-red-500 text-sm">Ingrese la hora de fin del contrato</span>
                                )}
                            </div>
                        </div>
                    </div>
                    <div className="flex justify-end mt-4">
                        <button
                            type="submit"
                            className="bg-[#E8A477] hover:bg-[#BC7547] p-2 px-4 rounded-lg text-sm text-white mr-2 font-semibold focus:ring-4 focus:ring-orange-200"
                        >
                            Editar
                        </button>
                        <button
                            type="button"
                            onClick={closeModal}
                            className="bg-gray-200 border p-2 px-4 rounded-lg text-sm text-gray-700 font-semibold"
                        >
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditarModal;
