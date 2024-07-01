import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { InputTextarea } from 'primereact/inputtextarea';
import clienteServiceInstance from '../../../../servicios/Miembro/ClienteService';
import ProveedorServiceInstance from '../../../../servicios/Miembro/ProveedorService';

const ModalS_Perfil = ({ usuario, Modal }) => {
    const { handleSubmit, register, formState: { errors } } = useForm();
    const [seleccionarPDF, setSeleccionarPDF] = useState(null);

    const actualizar = async (data, info) => {
        
        const updatedData = { ...data };

        if (seleccionarPDF) {
            updatedData.curriculumUrl = seleccionarPDF;
        } else {
            updatedData.curriculumUrl = info.curriculumUrl;
        }

        try {
            if (usuario.idUsuario.idTipo.idTipo === 1) {
                await clienteServiceInstance.putCliente(usuario.idCliente, updatedData);
            } else if (usuario.idUsuario.idTipo.idTipo === 2) {
                await ProveedorServiceInstance.putProveedor(usuario.idProveedor, updatedData);
            }
            Modal(); // Cierra el modal después de la actualización
            console.log('Datos actualizados:', updatedData);
        } catch (error) {
            console.error('Error al actualizar datos:', error);
        }
    };

    const handleFileChange = (e) => {
        setSeleccionarPDF(e.target.files[0]);
    };
    
    const renderTipoUsuario = () => {
        if (usuario && usuario.idUsuario && usuario.idUsuario.idTipo) {
            switch (usuario.idUsuario.idTipo.idTipo) {
                case 1:
                    return (
                        <div id="editarPerfil_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
                            <div className="relative p-10 w-1/2">
                                <div className="relative bg-white rounded-lg shadow">
                                    <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                                        <h3 className="text-xl font-semibold text-gray-700 ">
                                            Editar perfil
                                        </h3>
                                        <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                                            <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                                            </svg>
                                            <span className="sr-only">Cerrar</span>
                                        </button>
                                    </div>
                                    <form onSubmit={handleSubmit((data) => actualizar(data, usuario))}>
                                        <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                                            <div className='w-3/4 mx-auto'>
                                                <div className='my-5 flex flex-col'>
                                                    <label className="block mb-2 text-sm font-semibold text-gray-700">Descripción</label>
                                                    <InputTextarea type="text" id="descripcion" {...register("descripcion", { required: true })} defaultValue={usuario.descripcion || ""} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" placeholder="Ingrese una descripción para su perfil" rows={5} cols={50} />
                                                    {errors.descripcion && <span className="text-red-500 text-sm">Ingrese una descripción</span>}
                                                </div>
                                            </div>
                                        </div>
                                        <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                                            <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                                                Actualizar
                                            </button>
                                            <button onClick={Modal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10">
                                                Cerrar
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    );
                case 2:
                    return (
                        <div id="editarPerfil_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
                            <div className="relative p-10 w-1/2">
                                <div className="relative bg-white rounded-lg shadow">
                                    <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                                        <h3 className="text-xl font-semibold text-gray-900 ">
                                            Editar perfil
                                        </h3>
                                        <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                                            <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                                            </svg>
                                            <span className="sr-only">Cerrar</span>
                                        </button>
                                    </div>
                                    <form onSubmit={handleSubmit((data) => actualizar(data, usuario))}>
                                        <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                                            <div className='w-3/4 mx-auto'>
                                                <div className='my-5 flex flex-col'>
                                                    <label className="block mb-2 text-sm font-semibold text-gray-700">Descripción</label>
                                                    <InputTextarea type="text" id="descripcion" {...register("descripcion", { required: true })} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5"  defaultValue={usuario.descripcion || ""} placeholder="Ingrese una descripción para su perfil" rows={5} cols={50} />
                                                    {errors.descripcion && <span className="text-red-500 text-sm">Ingrese una descripción</span>}
                                                </div>
                                                <div className='mb-5 flex flex-col'>
                                                    <label  className="block mb-2 text-sm font-semibold text-gray-700">Curriculum Vitae</label>
                                                    <div className="flex items-center justify-center w-1/2 mt-1 mb-2">
                                                        <label className="flex flex-col items-center justify-center w-full h-10 border-2 border-gray-300 border-dashed rounded-lg bg-gray-50 hover:bg-gray-100">
                                                            <p className="text-sm text-[#787171]">{seleccionarPDF ? seleccionarPDF.name : usuario.curriculumUrl || "Sin cargar archivo"} </p>
                                                            <input  accept="application/pdf" type="file" className="hidden" onChange={handleFileChange} />
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                                            <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                                                Actualizar
                                            </button>
                                            <button onClick={Modal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10">
                                                Cerrar
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    );
                default:
                    return null;
            }
        } else {
            return null;
        }
    };

    return (
        <div>
            {renderTipoUsuario()}
        </div>
    );
}

export default ModalS_Perfil;
