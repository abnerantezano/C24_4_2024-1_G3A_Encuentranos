import { useForm } from "react-hook-form"

const IniciarSesion = () => {
    const {} = useForm();
    return (
        <form>
            <div className="mt-12 mb-12 lg:mb-24 lg:mt-24 h-full bg-red-100">
                <div className="flex h-full items-center justify-center lg:p-24">
                    <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white lg:p-24 m-6 p-12 rounded-lg shadow-xl">
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo eléctronico</label>
                            <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                            <input type="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        </div>
                        <div className="mb-5">
                                <button type="button" className="focus:outline-none w-full text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Iniciar sesión</button>
                        </div>
                        <div
                            className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300 dark:before:border-neutral-500 dark:after:border-neutral-500">
                            <p
                            class="mx-4 mb-0 text-center font-semibold dark:text-neutral-200">
                            O
                            </p>
                        </div>
                        <div className="mb-5">
                            <button type="button" className="flex justify-center w-full text-gray-400 bg-white border border-gray-400 hover:bg-gray-300 hover:text-gray-500 focus:ring-4 focus:outline-none focus:ring-[#4285F4]/50 font-medium rounded-lg text-sm px-5 py-2.5 inline-flex items-center dark:focus:ring-[#4285F4]/55 me-2 mb-2">
                                <img className="w-4 h-4 me-2" src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png"></img>
                                Iniciar sesión con Google
                            </button>
                        </div>
                    </div>
                </div>
            </div>
    </form>
    )
}

export default IniciarSesion;