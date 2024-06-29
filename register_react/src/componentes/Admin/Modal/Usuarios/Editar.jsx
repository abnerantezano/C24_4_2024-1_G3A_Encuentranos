import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
//SERVICIOS
import Servicios from '../../../servicios/Servicios';


const EditarUsuarioModal = ({ usuario, closeModal, funcion }) => {

    const { register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: {
            nombre: usuario.nombre,
            email: usuario.email,
        },
    });

    const editarUsuario = (data) => {
        const datos = {
            nombre: data.nombre,
            email: data.email,
        };

        console.log(datos, usuario.id_usuario);

        Servicios.putUsuario(datos, usuario.id_usuario)
            .then((response) => {
                console.log(response);
                console.log("Usuario actualizado");
                closeModal();
                funcion();
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 w-full">
            <div className="bg-white p-8 rounded-lg shadow-lg w-1/2">
                <h2 className="text-xl mb-4 font-bold text-gray-800">Editar usuario</h2>
                <form onSubmit={handleSubmit(editarUsuario)}>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Nombre</label>
                        <input name="nombre" {...register("nombre", { required: true })} type="text"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5" placeholder="Nombre del usuario"
                        />
                        {errors.nombre && (
                            <span className="text-red-500 text-sm">Ingrese el nombre del usuario</span>
                        )}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Email</label>
                        <input name="email" {...register("email", { required: true })} type="email"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5" placeholder="Email del usuario"
                        />
                        {errors.email && (
                            <span className="text-red-500 text-sm">Ingrese el email del usuario</span>
                        )}
                    </div>
                    <div className="flex justify-end mt-4">
                        <button type="submit" className="bg-[#E8A477] hover:bg-[#BC7547] p-2 px-4 rounded-lg text-sm text-white mr-2 font-semibold focus:ring-4 focus:ring-orange-200">
                            Editar
                        </button>
                        <button type="button" onClick={closeModal} className="bg-gray-200 border p-2 px-4 rounded-lg text-sm text-gray-700 font-semibold">
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditarUsuarioModal;
