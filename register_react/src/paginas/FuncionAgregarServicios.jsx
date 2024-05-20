import React, { useEffect, useState } from "react";
import logo from "../imagenes/logo-color.png";
import { useNavigate } from "react-router-dom";
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
// PRIME REACT
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { useForm, Controller } from "react-hook-form";
// AXIOS
import ServicioProveedorService from "../servicios/ServicioProveedor";
import UsuarioService from "../servicios/UsuarioService";
import InformacionDeUsuario from "../componentes/InformacionDeUsuario";

const AgregarServicio = () => {

    // PROPIEDADES DE REACT HOOK FORM
    const { handleSubmit, control, formState: { errors } } = useForm();

    // VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

    // VARIABLES DE ESTADO
    const [servicios, setServicios] = useState([]);
    const [usuario, setUsuario] = useState({});
    const [serviciosAdicionales, setServiciosAdicionales] = useState([]);

    const [contador, setContador] = useState(0);

    // LLAMAR LA INFORMACIÓN DEL USUARIO
    useEffect(() => {
        UsuarioService.getInfo()
            .then(UsuarioResponse => {
                setUsuario(UsuarioResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    // LLAMAR LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
    useEffect(() => {
        if (usuario && usuario.id) {
            ServicioProveedorService.getServicioSinRegistrar(usuario.id)
                .then(response => {
                    setServicios(response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [usuario]);

    // LLAMAR LA LISTA DE LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
    useEffect(() => {
        if (usuario && usuario.id) {
            ServicioProveedorService.getServicioRegistrados(usuario.id)
                .then(response => {
                    setContador(response.data.length);
                    console.log(contador);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [usuario]);

    // AGREGAR OTRO CONTENIDO PARA SERVICIOS Y PRECIO
    const handleAddService = () => {
        if (contador < 6) {
            setServiciosAdicionales([...serviciosAdicionales, { idServicio: null, precio: null }]);
            setContador(prevContador => prevContador + 1);
        } else {
            alert("Cumples con el límite de servicios");
        }
    };

    // ELIMINAR UN CONTENIDO PARA SERVICIOS Y PRECIO
    const handleRemoveService = (index) => {
        const newServiciosAdicionales = [...serviciosAdicionales];
        newServiciosAdicionales.splice(index, 1);
        setServiciosAdicionales(newServiciosAdicionales);
        setContador(prevContador => prevContador - 1);
    };

    const isOptionDisabled = (option) => {
        return serviciosAdicionales.some(servicio => servicio.idServicio === option.id);
    };

    // FUNCIÓN DEL BOTON PARA AGREGAR LOS SERVICIOS
    const agregarServicio = (data,idProveedor) => {
        
        const serviciosParaAgregar = data.serviciosAdicionales.map(servicio => ({
            idProveedor: { id: parseInt(idProveedor) },
            idServicio: { id: parseInt(servicio.idServicio) },
            precio: parseFloat(servicio.precio)
        }));

        ServicioProveedorService.postAddServicioProveedor(serviciosParaAgregar)
            .then(response => {
                console.log(response);
                navigate('/inicio');
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <InformacionDeUsuario>
            {(info) => (
                <form autoComplete="off" onSubmit={handleSubmit((data) => agregarServicio(data,info.id))}>
                    <div className="bg-[#F0EEEC] w-full">
                        <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                            <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                                <div className="flex mb-8 justify-center">
                                    <img src={logo} className="w-24" alt="Logo" />
                                </div>
                                {serviciosAdicionales.map((servicio, index) => (
                                    <div key={index} className="flex mt-4 mb-5 w-full">
                                        <div className="grid md:grid-cols-2 md:gap-6 mt-4 mb-5 w-11/12">
                                            <div className="relative z-0 w-full group">
                                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                                                <Controller name={`serviciosAdicionales[${index}].idServicio`} control={control} defaultValue={servicio.idServicio} rules={{ required: true }} render={({ field }) => (
                                                    <Dropdown value={field.value} onChange={(e) => field.onChange(e.value)} options={servicios} optionDisabled={isOptionDisabled(field.value)} optionValue="id" optionLabel="nombre" placeholder="Seleccione un servicio" panelClassName="custom-panel" pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                                )}/>
                                                {errors.serviciosAdicionales?.[index]?.idServicio && <span className="text-red-500 text-sm">Agregue un servicio</span>}
                                            </div>
                                            <div className="relative z-0 w-full group">
                                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Precio</label>
                                                <Controller name={`serviciosAdicionales[${index}].precio`} control={control} defaultValue={servicio.precio} rules={{ required: true }} render={({ field }) => (
                                                    <InputNumber value={field.value} onChange={(e) => field.onChange(e.value)} mode="currency" currency="PEN" className="block w-full" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                                )}/>
                                                {errors.serviciosAdicionales?.[index]?.precio && <span className="text-red-500 text-sm">Ingrese el precio del servicio</span>}
                                            </div>
                                        </div>
                                        <div className="relative z-0 w-1/12 group flex items-center justify-end mt-6">
                                            <button type="button" onClick={() => handleRemoveService(index)} className="bg-red-300 p-2 rounded-lg text-white text-sm font-bold"><FontAwesomeIcon icon={faMinus} /></button>
                                        </div>
                                    </div>
                                ))}
                                {contador < 5 && (
                                    <div>
                                        <button className="text-[#B4663F] font-bold text-sm" type="button" onClick={handleAddService}>Agregar más servicios</button>
                                    </div>
                                )}
                                <div className="mb-5 flex justify-center my-16">
                                    <button type="submit" className="w-full focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">Agregar servicios</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            )}
        </InformacionDeUsuario>
    );
};

export default AgregarServicio;