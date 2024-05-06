import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import logo from "../images/logo-color.png";
import ServicioService from "../services/ServicioService";

const AgregarServicio = () => {
    const [servicios, setServicios] = useState([]);
    const [selectedServices, setSelectedServices] = useState([]);
    const [showSelect, setShowSelect] = useState(false);

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

    const handleAddService = () => {
        if (selectedServices.length < 5) {
            setShowSelect(true);
        }
    }

    const handleServiceChange = (e) => {
        const selectedServiceId = parseInt(e.target.value);
        const selectedService = servicios.find(service => service.id_servicio === selectedServiceId);
        setSelectedServices([...selectedServices, selectedService]);
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className="bg-[#FFF0E7] w-full">
                <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                    <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                        <div className="flex mb-8 justify-center">
                            <img src={logo} className="w-24" alt="Logo" />
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                            <select id="custom-select" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={handleServiceChange} required>
                                <option>Seleccionar servicio</option>
                                {servicios.map(servicio => (
                                    <option key={servicio.id_servicio} value={servicio.id_servicio}>{servicio.nombre}</option>
                                ))}
                            </select>
                        </div>
                        {showSelect && (
                            <div className="relative z-0 w-full group">
                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                                <select id="custom-select" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={handleServiceChange} required>
                                    <option>Seleccionar servicio</option>
                                    {servicios.map(servicio => (
                                        <option key={servicio.id_servicio} value={servicio.id_servicio}>{servicio.nombre}</option>
                                    ))}
                                </select>
                            </div>
                        )}
                        {selectedServices.length < 5 && (
                            <div>
                                <button type="button" onClick={handleAddService}>Agregar más servicios</button>
                            </div>
                        )}
                        <div className="mb-5 flex justify-center my-16">
                            <button type="submit" className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Siguiente</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    )
}

export default AgregarServicio;
