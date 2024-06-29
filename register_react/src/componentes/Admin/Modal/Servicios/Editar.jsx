import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import Servicios from '../../../servicios/Servicios';

const EditarModal = ({ servicio, closeModal, funcion }) => {

  const { register, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      archivo: servicio.archivo,
      nombre: servicio.nombre,
      descripcion: servicio.descripcion,
    },
  });

  const [imagenPrevia, setImagenPrevia] = useState(null);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagenPrevia(reader.result);
      };
      reader.readAsDataURL(file);
    } else {
      setImagenPrevia(null);
    }
  };

  const editarServicio = (data) => {
    const datos = {
      archivo: imagenPrevia,
      nombre: data.nombre,
      descripcion: data.descripcion,
    };

    console.log(datos, servicio.id_servicio);

    Servicios.patchServicio(datos, servicio.id_servicio)
      .then((response) => {
        console.log(response);
        console.log("Servicio actualizado");
        closeModal();
        funcion();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
      <div className="relative p-10 w-1/2">
        <div className="relative bg-white rounded-lg shadow">
          <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
            <h3 className="text-xl font-semibold text-gray-900">Editar servicio</h3>
            <button onClick={closeModal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
              <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
              </svg>
              <span className="sr-only">Cerrar</span>
            </button>
          </div>
          <form onSubmit={handleSubmit(editarServicio)}>
            <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
              <div className="w-3/4 mx-auto">
                <div className="mb-5 flex flex-col">
                  <label className="block mb-2 text-sm font-semibold text-gray-700">Nombre</label>
                  <input
                    name="nombre"
                    {...register("nombre", { required: true })}
                    type="text"
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                    placeholder="Nombre del servicio"
                  />
                  {errors.nombre && (
                    <span className="text-red-500 text-sm">Ingrese el nombre del servicio</span>
                  )}
                </div>
                <div className="mb-5 flex flex-col">
                  <label className="block mb-2 text-sm font-semibold text-gray-700">Imagen</label>
                  <div className="flex flex-col w-full">
                    <label htmlFor="imagen" className="flex flex-col items-center justify-center w-full h-auto border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
                      {imagenPrevia ? (
                        <img src={imagenPrevia} alt="Vista previa" className="w-full h-max object-contain rounded-lg" />
                      ) : (
                        <div className="flex flex-col items-center justify-center pt-5 pb-6">
                          <img name="imagen" src={imagenPrevia} alt="Foto del servicio" className="w-full h-max object-contain rounded-lg" />
                        </div>
                      )}
                      <input
                        id="imagen"
                        name="imagen"
                        type="file"
                        accept="image/*"
                        {...register("imagen", { required: true })}
                        onChange={handleImageChange}
                        className="hidden"
                      />
                    </label>
                    {errors.imagen && (
                      <span className="text-red-500 text-sm text-start">Agregue una imagen</span>
                    )}
                  </div>
                </div>
                <div className="mb-5 flex flex-col">
                  <label className="block mb-2 text-sm font-semibold text-gray-700">Descripción</label>
                  <textarea
                    rows="4"
                    name="descripcion"
                    placeholder="Descripción del servicio"
                    {...register("descripcion", { required: true })}
                    className="w-full text-sm text-gray-900 p-2.5 rounded-lg bg-gray-50 border border-gray-300 focus:ring-0"
                  ></textarea>
                  {errors.descripcion && (
                    <span className="text-red-500 text-sm">Llene la descripción del servicio</span>
                  )}
                </div>
              </div>
            </div>
            <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
              <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                Editar
              </button>
              <button onClick={closeModal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-200 focus:z-10">
                Cancelar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditarModal;
