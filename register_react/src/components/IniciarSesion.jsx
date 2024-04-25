import { useForm } from "react-hook-form"

const IniciarSesion = () => {
    const {} = useForm();
    return (
        <form>
            <div className="py-60 px-8 container h-full">
                <div className="flex h-full flex-wrap items-center justify-center lg:justify-between">
                    <div className="mb-12 md:mb-0 md:w-8/12 lg:w-6/12">
                        <img className="px-8" src="https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen-1.jpg"></img>
                    </div>
                    <div className="mb-12 md:mb-0 md:w-8/12 lg:w-6/12">
                        <div className="mb-5">
                            <label for="base-input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo eléctronico</label>
                            <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>
                        <div className="mb-5">
                            <label for="base-input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                            <input type="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>
                        <div className=" mb-5">
                                <button type="button" className="focus:outline-none w-full text-white bg-yellow-500 hover:bg-yellow-600 focus:ring-4 focus:ring-yellow-400 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-yellow-900">Registrar</button>
                        </div>
                        <div
                            className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300 dark:before:border-neutral-500 dark:after:border-neutral-500">
                            <p
                            class="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">
                            OR
                            </p>
                        </div>
                    </div>
                </div>
            </div>
    </form>
    )
}

export default IniciarSesion;