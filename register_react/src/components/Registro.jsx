import { useForm } from "react-hook-form";
import logo from "../logo-color.png";

const Registro = () => {

    const { register, formState:{errors}, handleSubmit } = useForm();

    const onSubmit = (data) => {
        console.log(data);
    }

    return (
        
    <form onSubmit={handleSubmit(onSubmit)}>
        <div className="mt-12 lg:mt-24 bg-red-100 bg-[url('bg-20.png')]">
            <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                    <div className="mb-8">
                        <img className=""></img>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo eléctronico</label>
                        <input type="text"{...register("email",{pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/ })} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 lg:block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                        {errors.email?.type === 'pattern' && <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">Oops!</span> El formato del email es incorrecto</p>}
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                        <input type="password"{...register("contraseña")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Nombre</label>
                        <input type="text"{...register("nombre")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="grid md:grid-cols-2 md:gap-6">
                        <div className="relative z-0 w-full mb-5 group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido paterno</label>
                            <input type="text"{...register("apellido_p")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="relative z-0 w-full mb-5 group mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido materno</label>
                            <input type="text"{...register("apellido_m")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                    </div>
                    <div className="mb-5">
                        <label className="block text-sm font-medium text-gray-900 dark:text-white">Celular</label>
                        <input type="text" {...register("celular" )} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5 grid md:grid-cols-3 md:gap-6">
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Departamento</label>
                            <select {...register("departamento")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>

                                <option>United States</option>
                                <option>Canada</option>
                                <option>France</option>
                                <option>Germany</option>
                            </select>
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Provincia</label>
                            <select {...register("provincia")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>

                                <option>United States</option>
                                <option>Canada</option>
                                <option>France</option>
                                <option>Germany</option>
                            </select>
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Distrito</label>
                            <select {...register("distrito")} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-400 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>

                                <option>United States</option>
                                <option>Canada</option>
                                <option>France</option>
                                <option>Germany</option>
                            </select>
                        </div>
                    </div>
                    <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">¿Ofreces algún servicio?</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="service-option-1" name="option-cliente" type="radio" {...register("rol")} value="prestador" className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" defaultChecked />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Si
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="country-option-2" name="option-cliente" type="radio" {...register("rol")} value="cliente" className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    No
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="mb-5">
                                <button type="submit" className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Registrar</button>
                        </div>
                    <div className="mb-5 flex justify-center text-center">
                        <label className="block text-sm font-medium text-gray-900 dark:text-white">¿Ya tienes una cuenta? <a href="" className="text-gray-500 ">Iniciar sesión</a></label>
                    </div>
                </div>
            </div>
        </div>
    </form>

    )
}

export default Registro;