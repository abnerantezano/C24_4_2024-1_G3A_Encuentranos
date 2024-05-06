import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import logo from "../images/logo-color.png";
import DepartamentosService from "../services/DepartamentosService";
import ServicioService from "../services/ServicioService";

const Formulario = () => {

    const [departamentos, setDepartamentos] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [distritos, setDistritos] = useState([]);
    const [ByDepartamento, setByDepartamento] = useState("");
    const [ByProvincia, setByProvincia] = useState("");

    const [servicios, setServicios] = useState([]);

    //TRAER LA LISTA DE DEPARTAMENTOS
    useEffect(() => {
        DepartamentosService.getDepartamentos()
            .then(DepartamentoResponse => {
                setDepartamentos(DepartamentoResponse);
            })
            .catch(error => {

            });
    }, []); 

    //TRAER LA LISTA DE PROVINCIAS
    useEffect(() => {
        DepartamentosService.getProvincias(ByDepartamento)
            .then(ProvinciaResponse => {
                setProvincias(ProvinciaResponse);
            })
            .catch(error => {

            });
    }, [[ByDepartamento]]); 

    //TRAER LA LISTA DE DISTRITOS
    useEffect(() => {
        DepartamentosService.getDistritos(ByDepartamento,ByProvincia)
            .then(DistritoResponse => {
                setDistritos(DistritoResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, [[ByDepartamento],[ByProvincia]]); 

    const handleDepartamentoChange = (e) => {
        const selectedDepartamento = e.target.value;
        setByDepartamento(selectedDepartamento);
        setByProvincia(""); 
        DepartamentosService.getProvincias(selectedDepartamento)
            .then(data => setProvincias(data))
            .catch(error => console.error(`Error al obtener provincias para ${selectedDepartamento}:`, error));
    };

    const handleProvinciaChange = (e) => {
        const selectedProvincia = e.target.value;
        setByProvincia(selectedProvincia);
        DepartamentosService.getDistritos(ByDepartamento, selectedProvincia)
            .then(data => setDistritos(data))
            .catch(error => console.error(`Error al obtener distritos para ${ByDepartamento} - ${selectedProvincia}:`, error));
    };

    useEffect(() => {
        ServicioService.getAll()
            .then(response => {
                setServicios(response);
            })
            .catch(error => {
                console.log(error);
            });
    }, []); 

    const { register, formState:{errors}, handleSubmit } = useForm();

    const onSubmit = (data) => {
        console.log(data);
    }

    return (
        
    <form >
        <div className="bg-[#FFF0E7] w-full">
            <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                    <div className="flex mb-8 justify-center">
                        <img src={logo} className="w-24" alt="Logo" />
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
                            <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Celular</label>
                        <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5 grid md:grid-cols-3 md:gap-6">
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Departamento</label>
                            <select onChange={handleDepartamentoChange} value={ByDepartamento} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                
                                <option> </option>
                                {departamentos.map(departamento => (
                                    <option key={departamento.id} value={departamento.name}>{departamento.name}</option>
                                ))}

                            </select>
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Provincia</label>
                            <select onChange={handleProvinciaChange} value={ByProvincia} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                <option> </option>
                                {provincias.map(provincia => (
                                    <option key={provincia.id} value={provincia.name}>{provincia.name}</option>
                                ))}

                            </select>
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Distrito</label>
                            <select className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-400 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                
                                <option> </option>
                                {distritos.map(distrito => (
                                    <option key={distrito.id} value={distrito.name}>{distrito.name}</option>
                                ))}

                            </select>
                        </div>
                    </div>
                    <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">¿Ofreces algún servicio?</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="service-option-1" name="option-cliente" type="radio" value="prestador" className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600"  />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Si
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="country-option-2" name="option-cliente" type="radio" value="cliente" className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" defaultChecked />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    No
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="mb-5 flex justify-center my-16">
                        <button type="submit" className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Siguiente</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    )
}

export default Formulario;