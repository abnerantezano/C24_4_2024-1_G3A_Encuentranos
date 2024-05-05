import logo from "../images/logo-color.png";
import React, { useEffect, useState } from "react";
import LoginService from "../services/LoginService";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import LoginService from "../services/LoginService";

const IniciarSesion = () => {
    const { register, handleSubmit } = useForm();
    const navigate = useNavigate();
    
    const iniciarSesionConGoogle = () => {
        window.location.href = "http://localhost:4000/oauth2/authorization/google";
        navigate('/Formulario')
    }

    const onSubmit = async (data) => {
        try {
            const response = await axios.post("http://localhost:8080/login", data);
            // Maneja la respuesta del backend (por ejemplo, almacena el token de acceso)
            console.log(response.data);
            // Redirige al usuario a la página de inicio después del inicio de sesión exitoso
            navigate("/");
        } catch (error) {
            console.error(error);
            // Maneja el error de inicio de sesión (por ejemplo, muestra un mensaje de error)
        }
    };

    
    const {} = useForm();

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="bg-[#F0EEEC] w-full">
                <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                    <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white lg:p-24 m-6 p-12 rounded-lg shadow-xl">
                        <div className="flex mb-8 justify-center">
                            <img src={logo} className="w-24" alt="Logo" />
                        </div>
                        <div className="mb-5">
                            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo electrónico</label>
                            <input type="email" id="email" {...register("email")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="mb-5">
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                            <input type="password" id="password" {...register("password")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="flex justify-center my-8">
                            <button type="submit" className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Iniciar sesión</button>
                        </div>
                        <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300 dark:before:border-neutral-500 dark:after:border-neutral-500">
                            <p className="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">O</p>
                        </div>
                        <div className="mb-5">
                            <button type="button" onClick={iniciarSesionConGoogle} className="flex justify-center w-full text-gray-800 bg-white border border-gray-400 focus:ring-4 focus:outline-none focus:ring-[#FCE6D8] font-medium rounded-lg text-sm px-5 py-2.5 inline-flex items-center me-2 mb-2">
                                <img src="https://rotulosmatesanz.com/wp-content/uploads/2017/09/2000px-Google_G_Logo.svg_.png" className="w-4 h-4 me-2" alt="Google Logo" />
                                Iniciar sesión con Google
                            </button>
                        </div>
                        <div className="mb-5 flex justify-center">

                            <label className="block text-sm font-medium text-gray-900 dark:text-white">¿No tienes una cuenta? <a href="/Registro" className="text-gray-500">Regístrate</a></label>

                            <label className="block text-sm font-medium text-gray-900 dark:text-white">¿No tienes una cuenta? <a href="/crud" className="text-gray-500 ">Regístrate</a></label>

                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
};

export default IniciarSesion;