import React, { useEffect, useState } from "react";
import logo from "../../imagenes/Logo de la empresa/logo-color.png";
import { useNavigate } from "react-router-dom";
//PRIME REACT
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { useForm, Controller } from "react-hook-form";
//AXIOS
import ServicioProveedorService from "../../servicios/Miembro/ServicioProveedor";
import UsuarioService from "../../servicios/Miembro/UsuarioService";

const AgregarServicio = () => {

    //PROPIEDADES DE REACT HOOK FORM
    const { register, handleSubmit, control, formState: { errors } } = useForm();

    //VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

    //VARIABLE PARA LISTAR LOS SERVICIOS
    const [servicios, setServicios] = useState([]);

    //VARIABLE PARA ALMACENAR LOS DATOS DEL USUARIO
    const [usuario, setUsuario] = useState({});

    //LLAMAR LA INFORMACIÓN DEL USUARIO
    useEffect(() => {
        UsuarioService.getProveedorInfo()
            .then(UsuarioResponse => {
                setUsuario(UsuarioResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    console.log(usuario);
    console.log(servicios);
    //LLAMAR LA LISTA DE LOS SERVICIOS NO REGISTRADOS POR EL PROVEEDOR
    useEffect(() => {
        if (usuario && usuario.idProveedor) {
            ServicioProveedorService.getServicioSinRegistrar(usuario.idProveedor)
                .then(response => {
                    setServicios(response);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [usuario]);

    //FUNCIÓN DEL BOTON PARA AGREGAR LOS SERVICIOS
    const agregarServicio = (data) => {

        const servicioParaAgregar = [{
            idProveedor: { idProveedor: parseInt(usuario.idProveedor) },
            idServicio: { idServicio: parseInt(data.idServicio) },
            precio: parseFloat(data.precio)
        }]

        console.log(servicioParaAgregar);

        ServicioProveedorService.postAddServicioProveedor(servicioParaAgregar)
            .then(response => {
                console.log(response);
                window.location.href = '/inicio';
            })
            .catch(error => {
                console.log(error);
            });
    }
    
    return (
        <div>
            <form autoComplete="off" onSubmit={handleSubmit((data) => agregarServicio(data))}>
                <div className="bg-[#F0EEEC] w-full">
                    <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                        <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                            <div className="flex mb-8 justify-center">
                                <img src={logo} className="w-24" alt="Logo" />
                             </div>
                            <div className="grid md:grid-cols-2 md:gap-6 mb-5">
                                <div className="relative z-0 w-full group">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Servicio</label>
                                    <Controller name="idServicio" {...register("idServicio", { required: true })} control={control} render={({ field }) => (
                                        <Dropdown id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} options={servicios} optionValue="idServicio" optionLabel="nombre" placeholder="Seleccione un servicio" panelClassName="custom-panel" pt={{input:'text-sm',panel:'text-sm',root:'ring-0',select:'text-red-500'}} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full" />
                                    )} />
                                    {errors.idServicio && <span className="text-red-500 text-sm">Agregue un servicio</span>}
                                </div>
                                <div className="relative z-0 w-full group">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Precio</label>
                                    <Controller name="precio" {...register("precio", { required: true })} control={control} render={({ field }) => (
                                        <InputNumber inputId={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} mode="currency" currency="PEN" className="block w-full" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                    )} />
                                    {errors.precio && <span className="text-red-500 text-sm">Ingrese el precio del servicio</span>}
                                </div>
                            </div>
                            <div className="mb-5 flex justify-center my-16">
                                <button type="submit" className="w-full focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">Agregar servicios</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default AgregarServicio;
