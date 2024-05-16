import React, { useEffect, useState } from "react";
import logo from "../imagenes/logo-color.png";
import { useNavigate } from "react-router-dom";
//AXIOS
import ServicioService from "../servicios/ServicioService";
import ServicioProveedorService from "../servicios/ServicioProveedor";
import UsuarioService from "../servicios/UsuarioService";

const AgregarServicio = () => {

    //VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

    //VARIABLE PARA LLAMAR LOS SERVICIOS
    const [servicios, setServicios] = useState([]);

    //VARIABLE PARA ALMACENAR EL PRECIO E IDSERVICIO Y CONTAR CUANTOS SE AGREGAN
    const [serviceData, setServiceData] = useState([{ idServicio: '', precio: '' }]);
    const [showSelect, setShowSelect] = useState(false);
    const [serviceCount, setServiceCount] = useState(1);

    //VARIABLE DONDE SE ALMACENA EL USUARIO Y SUS DATOS
    const [usuario,setUsuario]=useState([])

    const [servicioP,setServicioP] = useState([]);

    //LLAMAR LA LISTA DE LOS SERVICIOS
    useEffect(() => {
        ServicioService.getAll()
            .then(response => {
                setServicios(response);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    //TRAER LOS DATOS SEGUN EL TIPO DE USUARIO QUE SE AGREGO
    useEffect(() => {
        UsuarioService.getInfo()
            .then(UsuarioResponse => {
                setUsuario(UsuarioResponse);
            })
            .catch(error => {
                console.log(error);
            })
    })

    //AGREGAR SERVICIOS 
    const agregarServicio = (e) => {
        e.preventDefault();
        
        const serviciosData = serviceData.filter(service => service.idServicio && service.precio);
        
        const serviciosPrestadores = serviciosData.map(service => ({
            idProveedor: {id:parseInt(usuario.id)}, 
            idServicio: service.idServicio,
            precio: service.precio
        }));

        console.log(serviciosPrestadores);

        ServicioProveedorService.postAddServicioProveedor(serviciosPrestadores)
            .then((ServicioProveedorResponse) => {
                console.log(ServicioProveedorResponse);
                navigate('/inicio');
            })
            .catch(error => {
                console.error(error);
            });
    }

    //REALIZAR EL CONTADOR Y ALMACENARLOS EN EL ARRAY
    const handleAddService = () => {
        if (serviceCount < 5) {
            setShowSelect(true);
            setServiceCount(prevCount => prevCount + 1);
            setServiceData(prevData => [...prevData, { idServicio: '', precio: '' }]); 
        }
    }

    // ACTUALIZA LA VISTA DE SERVICIOS Y MANEJA LOS CAMBIOS DEL SERVICIO
    const handleChange = (show, e) => {
        const { name, value } = e.target;
        const newData = [...serviceData];
        if (name === "idServicio") {
            newData[show][name] = { id: parseInt(value)}; 
        } else {
            newData[show][name] = parseFloat(value);
        }
        setServiceData(newData);
    }

    return (
        <form>
            <div className="bg-[#F0EEEC] w-full">
                <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                    <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                        <div className="flex mb-8 justify-center">
                            <img src={logo} className="w-24" alt="Logo" />
                        </div>
                        {serviceData.map((service, show) => (
                            <div key={show} className="grid md:grid-cols-2 md:gap-6">
                                <div className="relative z-0 w-full group">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                                    <select  name="idServicio"  value={service.idServicio.id} onChange={(e) => handleChange(show, e)} className="mb-5 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                        <option value="">Seleccionar servicio</option>
                                        {servicios.map(servicio => (
                                            <option key={servicio.id} value={servicio.id}>{servicio.nombre}</option>
                                        ))}
                                    </select>
                                </div>
                                <div className="relative z-0 w-full mb-5 group">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Precio</label>
                                    <input type="number"  name="precio" step="0.01" min="0" value={service.precio} onChange={(e) => handleChange(show, e)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required
                                    />
                                </div>
                            </div>
                        ))}
                        {serviceCount < 5 && (
                            <div>
                                <button className="text-[#B4663F] font-bold text-sm" type="button" onClick={handleAddService}>Agregar más servicios</button>
                            </div>
                        )}
                        <div className="mb-5 flex justify-center my-16">
                            <button type="submit" onClick={(e) => agregarServicio(e)} className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Siguiente</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    )
}

export default AgregarServicio;
