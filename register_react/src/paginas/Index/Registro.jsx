import logo from "../../imagenes/logo-color.png";

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
                        <div className="mb-2 text-center">
                            <label className="mb-3 font-bold">EMPIEZA TU BÚSQUEDA</label>
                        </div>
                        <div className="mb-16 not-italic text-center">
                            <label className="mb-3 not-italic">Registrate para navegar en nuestra plataforma </label>
                        </div>
                        <div className="mb-16">
                        <button type="button" onClick={iniciarSesionConGoogle} className="flex justify-center w-full text-gray-800 bg-white border border-gray-400 focus:ring-4 focus:outline-none focus:ring-[#FCE6D8] font-medium rounded-lg text-sm px-5 py-2.5 items-center me-2 mb-2">
                                <img src="https://rotulosmatesanz.com/wp-content/uploads/2017/09/2000px-Google_G_Logo.svg_.png" className="w-4 h-4 me-2" alt="Google Logo" />
                                Registrarse con Google
                            </button>
                            
                        </div>
                        <div className="mb-5 flex justify-center">
                            <label className="block text-sm font-medium text-gray-900">¿Ya tienes una cuenta? <a href="/IniciarSesion" className="text-gray-500 ">Iniciar sesión</a></label>
                        </div>
                    </div>
                </div>
            </div>
    </form>
    )
}

export default IniciarSesion;