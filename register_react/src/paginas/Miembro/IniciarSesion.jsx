import React from "react";
import logo from "../../imagenes/logo-color.png";
import logoGoogle from "../../imagenes/logos/logoGoogle.png";

const IniciarSesion = () => {
    
    const iniciarSesionConGoogle = () => {
        window.location.href = "http://localhost:4000/oauth2/authorization/google";
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
                            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900">Correo electrónico</label>
                            <input type="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5" required />
                        </div>
                        <div className="mb-5">
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Contraseña</label>
                            <input type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5" required />
                        </div>
                        <div className="flex justify-center my-8">
                            <button type="submit" className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">Iniciar sesión</button>
                        </div>
                        <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300">
                            <p className="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">O</p>
                        </div>
                        <div className="mb-5">
                            <button type="button" onClick={iniciarSesionConGoogle} className="flex justify-center w-full text-gray-800 bg-white border border-gray-400 focus:ring-4 focus:outline-none focus:ring-[#FCE6D8] font-medium rounded-lg text-sm px-5 py-2.5 items-center me-2 mb-2">
                                <img src={logoGoogle} className="w-4 h-4 me-2" alt="Google Logo" />
                                Iniciar sesión con Google
                            </button>
                        </div>
                        <div className="mb-5 flex justify-center">
                            <label className="block text-sm font-medium text-gray-900 dark:text-white">¿No tienes una cuenta? <a href="/registro" className="text-gray-500">Regístrate</a></label>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
};

export default IniciarSesion;
