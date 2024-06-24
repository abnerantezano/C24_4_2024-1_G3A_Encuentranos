import React, { useState } from "react";
import { useForm, Controller } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import perfil from "../../imagenes/perfil/perfil.png";
// PRIME REACT
import { Password } from 'primereact/password';
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
// SERVICIOS
import UsuarioService from "../../servicios/Miembro/UsuarioService";
// COMPONENTES
import Token from "../../componentes/Token";

const CrearUsuario = () => {
    const navigate = useNavigate();
    const { register, handleSubmit, watch, control, formState: { errors } } = useForm();

    //MOSTRAR LA IMAGEN
    const [selectedImage, setSelectedImage] = useState(null);
    //CARGAR LA IMAGEN
    const [nombreImagen, setNombreImagen] = useState(null);

    const handleImageChange = (e) => {
        if (e.target.files && e.target.files[0]) {
            const file = e.target.files[0];
            setNombreImagen(file.name);
            const reader = new FileReader();
            reader.onload = (event) => {
                setSelectedImage(event.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const EnviarDatos = (data, email) => {
        const fechaActual = new Date().toISOString().split('T')[0];
        const usuario = {
            idTipo: {idTipo: parseInt(data.idTipo)},
            correo: email,
            contrasena: data.contrasena,
            imagenUrl: nombreImagen || "https://www.transparentpng.com/download/user/gray-user-profile-icon-png-fP8Q1P.png",
            activo: true,
            fechaRegistro: fechaActual
        };

        console.log(usuario);

        UsuarioService.addUser(usuario)
            .then((response) => {
                console.log(response.data);
                navigate('/formulario');
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <Token>
            {(token) => (
                <form onSubmit={handleSubmit((data) => EnviarDatos(data, token.email))}>
                    <div className="bg-[#F0EEEC] w-full">
                        <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                            <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white lg:p-24 m-6 p-12 rounded-lg shadow-xl">
                                <div className="flex justify-center mb-5">
                                    <div className="relative">
                                        <input
                                            type="file"
                                            accept="image/*"
                                            onChange={handleImageChange}
                                            className="hidden"
                                            id="imageUpload"
                                        />
                                        <label htmlFor="imageUpload" className="cursor-pointer">
                                            <div className="w-24 h-24 rounded-full border border-gray-300 flex items-center justify-center overflow-hidden relative">
                                                {selectedImage ? (
                                                    <img src={selectedImage} alt="Preview" className="object-cover w-full h-full" />
                                                ) : (
                                                    <img src={perfil} alt="Default" className="h-full w-full object-cover" />
                                                )}
                                            </div>
                                        </label>
                                        <button
                                            type="button"
                                            className="absolute w-8 h-8 bottom-0 right-0 bg-[#E8A477] text-white rounded-full flex items-center justify-center"
                                            onClick={() => document.getElementById('imageUpload').click()}
                                        >
                                            <FontAwesomeIcon icon={faPlus} />
                                        </button>

                                    </div>
                                </div>
                                <div className="mb-5">
                                    <label className="block mb-2 text-sm font-medium text-gray-900">Contraseña</label>
                                    <Controller name="contrasena" control={control} rules={{ required: 'Ingresar una contraseña' }} render={({ field }) => (
                                        <Password id="contrasena" {...field} className="block w-full" feedback={false} toggleMask inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5"/>
                                    )}/>
                                    {errors.contrasena && <span className="text-red-500 text-sm">{errors.contrasena.message}</span>}
                                </div>
                                <div className="mb-5">
                                    <label className="block mb-2 text-sm font-medium text-gray-900">Confirmar contraseña</label>
                                    <Controller name="confirmarContrasena" control={control} rules={{ required: 'Confirmar la contraseña', validate: value => value === watch('contrasena') || 'Las contraseñas no coinciden'}} render={({ field }) => (
                                        <Password id="confirmarContrasena" {...field} className="block w-full" feedback={false} toggleMask inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5"/>
                                    )}/>
                                    {errors.confirmarContrasena && <span className="text-red-500 text-sm">{errors.confirmarContrasena.message}</span>}
                                </div>
                                <div className="mb-5"> 
                                    <label className="block text-sm font-medium text-gray-900 mb-2">¿Ofreces algún servicio?</label>
                                    <div className="grid md:grid-cols-3 md:gap-6">
                                        <div className="flex items-center mt-2">
                                            <input id="user-option-1" name="idTipo" type="radio" value="2" {...register("idTipo",{ required: true })} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-30" />
                                            <label htmlFor="user-option-1" className="block ms-2 text-sm font-medium text-gray-900">
                                                Si
                                            </label>
                                        </div>
                                        <div className="flex items-center mt-2">
                                            <input id="user-option-2" name="idTipo" type="radio" value="1" {...register("idTipo",{ required: true })} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300" />
                                            <label htmlFor="user-option-2" className="block ms-2 text-sm font-medium text-gray-900">
                                                No
                                            </label>
                                        </div>
                                    </div>
                                    {errors.idTipo && <span className="text-red-500 text-sm">Elija una opción</span>}
                                </div>
                                <div className="flex justify-center my-8">
                                    <button type="submit" className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">Crear cuenta</button>
                                </div>
                                <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300">
                                    <p className="mx-4 mb-0 text-center font-semibold">O</p>
                                </div>
                                <div className="mb-5 flex justify-center">
                                    <label className="block text-sm font-medium text-gray-900">¿Ya tienes una cuenta? <a href="/iniciarSesion" className="text-gray-500">Iniciar Sesión</a></label>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            )}
        </Token>
    );
}
export default CrearUsuario;
