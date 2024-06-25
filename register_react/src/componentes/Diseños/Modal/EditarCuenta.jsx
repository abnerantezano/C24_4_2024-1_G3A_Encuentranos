import React, { useState } from 'react'
import { useForm, Controller } from "react-hook-form";
//FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare, faPlus } from '@fortawesome/free-solid-svg-icons';
//PRIME REACT
import { Password } from 'primereact/password';
//COMPONENTES
import InformacionDeUsuario from '../../Informacion/InformacionDeUsuario';
//SERVICIOS
import usuarioServiceInstance from '../../../servicios/Miembro/UsuarioService';

function EditarCuenta() {

    //VARIABLE PARA ABRIR EL MODAL
    const [visible, setVisible] = useState(false);

    //PARAMETROS DE REACT HOOK FORM
    const { handleSubmit, watch, control,reset, formState: { errors } } = useForm();

    //FUNCION PARA ABRIR EL MODAL Y RESETEAR CUANDO SE CIERRE
    const Modal = () => {
        if (visible) {
            reset();
        }
        setVisible(!visible);
    };
    
    const actualizar = (data, info) => {
        const datos = {
            contrasenaActual: data.actual_contrasena,
            nuevaContrasena: data.nueva_contrasena
        }

        usuarioServiceInstance.putUsuario(info.idUsuario,datos)
            .then((response) => {
                console.log(response);
                Modal();
                alert("Cuenta actualizada");
            })
            .catch((error) => {
                console.error(error);
                alert("Contraseña incorrecta");
            });
    }

    return (
        <InformacionDeUsuario>
            {(info) => {
                return(
                    <div>
                        <button onClick={Modal} className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold' >
                            Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} />
                        </button>
                        {visible && (
                            <div id="editarCuenta_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
                                <div className="relative p-10 w-1/2">
                                    <div className="relative bg-white rounded-lg shadow">
                                        <div className="flex items-centerjustify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                                            <h3 className="text-xl font-semibold text-gray-900 ">
                                                Editar cuenta
                                            </h3>
                                            <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                                                <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                                                </svg>
                                                <span className="sr-only">Cerrar</span>
                                            </button>
                                        </div>
                                        <form onSubmit={handleSubmit((data) => actualizar(data, info))}>
                                            <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                                                <div className='w-3/4 mx-auto'>
                                                    <div className='mb-5 flex flex-col'>
                                                        <label className="block mb-2 text-sm font-semibold text-gray-700">Correo electrónico</label>
                                                        <input className="border p-2 rounded-lg text-sm text-[#787171]" placeholder={info.correo} value={info.correo} disabled />
                                                    </div>
                                                    <div className='mb-5 flex flex-col'>
                                                        <label className="block mb-2 text-sm font-semibold text-gray-700">Actual contraseña</label>
                                                        <Controller name="actual_contrasena" control={control} rules={{ required: 'Ingresar la actual contraseña' }} render={({ field }) => (
                                                            <Password id="actual_contrasena" {...field} className="block w-full" feedback={false} toggleMask inputClassName="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 " />
                                                        )} />
                                                        {errors.actual_contrasena && <span className="text-red-500 text-sm">{errors.actual_contrasena.message}</span>}
                                                    </div>
                                                    <div className='mb-5 flex flex-col'>
                                                        <label className="block mb-2 text-sm font-semibold text-gray-700">Nueva contraseña</label>
                                                        <Controller name="nueva_contrasena" control={control} rules={{ required: 'Ingresar la nueva contraseña' }} render={({ field }) => (
                                                            <Password id="nueva_contrasena" {...field} className="block w-full" feedback={false} toggleMask inputClassName="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 " />
                                                        )} />
                                                        {errors.nueva_contrasena && <span className="text-red-500 text-sm">{errors.nueva_contrasena.message}</span>}
                                                    </div>
                                                    <div className='mb-5 flex flex-col'>
                                                        <label className="block mb-2 text-sm font-semibold text-gray-700">Confirmar contraseña</label>
                                                        <Controller name="confirmar_contrasena" control={control} rules={{ required: 'Confirmar la nueva contraseña', validate: value => value === watch('nueva_contrasena') || 'La contraseña no coincide con la nueva contraseña' }} render={({ field }) => (
                                                            <Password id="confirmar_contrasena" {...field} className="block w-full" feedback={false} toggleMask inputClassName="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 " />
                                                        )} />
                                                        {errors.confirmar_contrasena && <span className="text-red-500 text-sm">{errors.confirmar_contrasena.message}</span>}
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
                        )}
                    </div>
                )
            }}
        </InformacionDeUsuario>
    )
}

export default EditarCuenta