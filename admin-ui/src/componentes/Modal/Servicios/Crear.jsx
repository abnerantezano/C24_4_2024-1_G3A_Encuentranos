import React from 'react'
import { useForm } from "react-hook-form"; // Importa el hook useForm
import ServiciosService from '../../../servicios/Servicios'; // Asegúrate de importar el servicio adecuado

const Crear = ({ closeModal, funcion }) => {
  const { register, handleSubmit, formState: { errors } } = useForm({});

  const agregarServicio = (data) => {
    
    const fechaActual = new Date()

    const servicio = {
      nombre: data.nombre,
      descripcion: data.descripcion,
      imagen_url:null,
      fh_creacion: fechaActual
    };

    console.log(servicio);
    ServiciosService.postServicio(servicio)
      .then((response) => {
        console.log(response);
        funcion();
        closeModal(); 
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 w-full">
        <div className="bg-white p-8 rounded-lg shadow-lg w-1/2">
            <form onSubmit={handleSubmit(agregarServicio)}>
                <div>
                    <h2 className="text-xl mb-4 font-bold text-gray-800">Agregar un servicio</h2>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Nombre</label>
                        <input
                            name="nombre"
                            {...register("nombre", { required: true })}
                            type="text"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
                            placeholder="Nombre del servicio"
                        />
                        {errors.nombre && (
                            <span className="text-red-500 text-sm">
                            Ingrese el nombre del servicio
                            </span>
                        )}
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-2">Descripción</label>
                        <textarea rows="4" name="descripcion" placeholder="Descripción del servicio"
                         {...register("descripcion", { required: true })}  className="w-full text-sm text-gray-900 p-2.5 px-2.5 rounded-lg bg-gray-50 border border-gray-300 dark:bg-gray-800 focus:ring-0 mb-5"></textarea>
                        {errors.descripcion && (
                            <span className="text-red-500 text-sm">
                                Llene la descripción del servicio
                            </span>
                        )}
                    </div>
                    <div className="flex justify-end mt-4">
                        <button type="submit" className="bg-[#E8A477] hover:bg-[#BC7547] p-2 px-4 rounded-lg text-sm text-white mr-2 font-semibold focus:ring-4 focus:ring-orange-200">
                            Agregar
                        </button>
                        <button type="button" onClick={closeModal} className="bg-gray-200 border p-2 px-4 rounded-lg text-sm text-gray-700 font-semibold">
                            Cancelar
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
  );
}

export default Crear;
