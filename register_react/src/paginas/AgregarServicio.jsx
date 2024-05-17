import React, { useEffect, useState } from "react";
import logo from "../imagenes/logo-color.png";
import { useNavigate } from "react-router-dom";
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { useForm, Controller } from "react-hook-form";
import ServicioProveedorService from "../servicios/ServicioProveedor";
import UsuarioService from "../servicios/UsuarioService";
import InformacionDeUsuario from "../componentes/InformacionDeUsuario";

const AgregarServicio = () => {

    //PROPIEDADES DE REACT HOOK FORM
    const { register, handleSubmit, control, watch, formState: { errors } } = useForm();

    //VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

    //VARIABLE PARA LISTAR LOS SERVICIOS
    const [servicios, setServicios] = useState([]);

    //VARIABLE PARA ALMACENAR LOS DATOS DEL USUARIO
    const [usuario, setUsuario] = useState({});

    //VARIABLE PARA ALMACENAR LOS SERVICIOS QUE SE AGREGARAN
    const [serviciosAdicionales, setServiciosAdicionales] = useState([]);

    //VARIABLE DEL CONTADOR
    const [contador, setContador] = useState(0);

    //LLAMAR LA INFORMACIÓN DEL USUARIO
    useEffect(() => {
        UsuarioService.getInfo()
            .then(UsuarioResponse => {
                setUsuario(UsuarioResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);
    
    //LLAMAR LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
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

    //LLAMAR LA LISTA DE LOS SERVICIOS REGISTRADOS POR EL PROVEEDOR
    useEffect(() => {
        if (usuario && usuario.id) {
            ServicioProveedorService.getServicioRegistrados(usuario.id)
                .then(response => {
                    setContador(response.data.length);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [usuario]);

    //AGREGAR OTRO CONTENIDO PARA SERVICIOS Y PRECIO
    const handleAddService = () => {
        setContador(prevContador => prevContador + 1);
        setServiciosAdicionales([...serviciosAdicionales, { idServicio: null, precio: null }]);
    };

    //VERIFICAR LOS SERVICIOS AGREGADOS EN EL FORMULARIO
    const handleServiceChange = (index, field, value) => {
        const newServiciosAdicionales = [...serviciosAdicionales];
        newServiciosAdicionales[index][field] = value;
        setServiciosAdicionales(newServiciosAdicionales);
    };

    //FUNCIÓN DEL BOTON PARA AGREGAR LOS SERVICIOS
    const agregarServicio = (data, idProveedor) => {

        if (contador>5){
            alert("Cumples con el limite de servicios")
        }
        const serviciosParaAgregar = serviciosAdicionales.map(servicio => ({
            idProveedor: { id: parseInt(idProveedor) },
            idServicio: { id: parseInt(servicio.idServicio) },
            precio: parseFloat(servicio.precio)
        }));

        serviciosParaAgregar.push({
            idProveedor: { id: parseInt(idProveedor) },
            idServicio: { id: parseInt(data.idServicio) },
            precio: parseFloat(data.precio)
        });

        console.log(serviciosParaAgregar);

        ServicioProveedorService.postAddServicioProveedor(serviciosParaAgregar)
            .then(response => {
                console.log(response)
                navigate('/inicio')
            })
            .catch(error => {
                console.log(error);
            });
    }

    return (
        <InformacionDeUsuario>
            {(info) => (
                <form autoComplete="off" onSubmit={handleSubmit((data) => agregarServicio(data, info.id))}>
                    <div className="bg-[#F0EEEC] w-full">
                        <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                            <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                                <div className="flex mb-8 justify-center">
                                    <img src={logo} className="w-24" alt="Logo" />
                                </div>
                                <div className="grid md:grid-cols-2 md:gap-6 mb-5">
                                    <div className="relative z-0 w-full group">
                                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                                        <Controller name="idServicio" {...register("idServicio", { required: true })} control={control} render={({ field }) => (
                                            <Dropdown id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} options={servicios} optionValue="id" optionLabel="nombre" placeholder="Seleccione un servicio" panelClassName="custom-panel" pt={{input:'text-sm',panel:'text-sm',root:'ring-0'}} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                        )} />
                                        {errors.idServicio && <span className="text-red-500 text-sm">Agregue un servicio</span>}
                                    </div>
                                    <div className="relative z-0 w-full group">
                                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Precio</label>
                                        <Controller name="precio" {...register("precio", { required: true })} control={control} render={({ field }) => (
                                            <InputNumber inputId={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} mode="currency" currency="PEN" className="block w-full" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                        )} />
                                        {errors.precio && <span className="text-red-500 text-sm">Ingrese el precio del servicio</span>}
                                    </div>
                                </div>
                                {serviciosAdicionales.map((servicio, index) => (
                                    <div key={index} className="grid md:grid-cols-2 md:gap-6 mt-4 mb-5">
                                        <div className="relative z-0 w-full group">
                                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Servicio</label>
                                            <Dropdown value={servicio.idServicio} onChange={(e) => handleServiceChange(index, 'idServicio', e.value)} options={servicios} optionValue="id" optionLabel="nombre" placeholder="Seleccione un servicio" panelClassName="custom-panel" pt={{input:'text-sm',panel:'text-sm',root:'ring-0'}} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                        </div>
                                        <div className="relative z-0 w-full group">
                                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Precio</label>
                                            <InputNumber value={servicio.precio} onChange={(e) => handleServiceChange(index, 'precio', e.value)} mode="currency" currency="PEN" className="block w-full" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                        </div>
                                    </div>
                                ))}
                                {contador < 4 && (
                                    <div>
                                        <button className="text-[#B4663F] font-bold text-sm" type="button" onClick={handleAddService}>Agregar más servicios</button>
                                    </div>
                                )}
                                <div className="mb-5 flex justify-center my-16">
                                    <button type="submit" className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">Agregar servicios</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            )}
        </InformacionDeUsuario>
    );
}

export default AgregarServicio;
