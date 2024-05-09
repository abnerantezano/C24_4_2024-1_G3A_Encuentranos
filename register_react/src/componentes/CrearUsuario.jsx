import React, { useState } from "react";
import logo from "../imagenes/logo-color.png";
import { useNavigate } from "react-router-dom";
import UsuarioService from "../servicios/UsuarioService";

const CrearUsuario = () => {

    //Variables para los campos de usuario
    const [correo,setCorreo] = useState('');
    const [contrasena,setContraseña] = useState('');
    const [idTipo,setIdTipo] = useState('');

    const navigate = useNavigate();
    
    const agregarUsuario = (e) => {
        e.preventDefault();
        const usuario = {idTipo,correo,contrasena};
        console.log(usuario);
        UsuarioService.addUser(usuario).then((response) => {
            console.log(response.data);
            navigate('/formulario');
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <form>
            <div className="bg-[#F0EEEC] w-full">
                <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                    <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white lg:p-24 m-6 p-12 rounded-lg shadow-xl">
                        <div className="flex mb-8 justify-center">
                            <img src={logo} className="w-24" alt="Logo" />
                        </div>
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo electrónico</label>
                            <input type="email" id="correo" value={correo} onChange={ (e) => setCorreo(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                            <input type="password" id="contrasena" value={contrasena} onChange={ (e) => setContraseña(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">¿Ofreces algún servicio?</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="user-option-1" name="ofrece-servicio" type="radio" value="2" onChange={(e) => setIdTipo(parseInt(e.target.value))} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600"  />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Si
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="user-option-2" name="ofrece-servicio" type="radio" value="1" onChange={(e) => setIdTipo(parseInt(e.target.value))} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    No
                                </label>
                            </div>
                        </div>
                    </div>
                        <div className="flex justify-center my-8">
                            <button onClick={(e) => agregarUsuario(e)} className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Crear cuenta</button>
                        </div>
                        <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300 dark:before:border-neutral-500 dark:after:border-neutral-500">
                            <p className="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">O</p>
                        </div>
                        <div className="mb-5 flex justify-center">
                            <label className="block text-sm font-medium text-gray-900 dark:text-white">¿Ya tienes una cuenta? <a href="/iniciarSesion" className="text-gray-500">Iniciar Sesión</a></label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    )
}
export default CrearUsuario;