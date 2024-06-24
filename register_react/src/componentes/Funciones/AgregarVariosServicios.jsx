import React, { useEffect, useState } from "react";
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
// PRIME REACT
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { useForm, Controller } from "react-hook-form";
// AXIOS
import servicioProveedorServiceInstance from "../../servicios/Miembro/ServicioProveedor";
import usuarioServiceInstance from "../../servicios/Miembro/UsuarioService";

const AgregarServicio = ({ onServiciosAgregados }) => {

    // PROPIEDADES DE REACT HOOK FORM
    const { handleSubmit, control, formState: { errors } } = useForm();

    //MANEJAR ESTADOS DE LAS VARIABLES
    const [proveedor, setProveedor] = useState([]);
    const [serviciosSinRegistrar, setServiciosSinRegistrar] = useState([]);
    const [serviciosAdicionales, setServiciosAdicionales] = useState([]);
    const [serviciosSeleccionados, setServiciosSeleccionados] = useState([]);

    const [botonAS, setBotonAs] = useState("");

    const [contador, setContador] = useState(0);

    //LLAMAR LA INFORMACIÓN DEL PROVEEDOR
    useEffect(() => {
        usuarioServiceInstance.getProveedorInfo()
            .then((proveedor) => {
                setProveedor(proveedor);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);
    
    useEffect(() => {
        if (proveedor) {

            servicioProveedorServiceInstance.getServiciosDisponibles(proveedor.idProveedor)
              .then((serviciosregistrados) => {
                  setContador(serviciosregistrados.length);
              })
              .catch((error) => {
                  console.error(error);
              });

            servicioProveedorServiceInstance.getServicioSinRegistrar(proveedor.idProveedor)
                .then((serviciossinregistrar) => {
                    setServiciosSinRegistrar(serviciossinregistrar);
                })
                .catch((error) => {
                    console.error(error);
            });

        }
    }, [proveedor]);

    //BOTON DE AGREGAR SERVICIOS
    useEffect(() => {
        if (serviciosSeleccionados.length > 0) {
            setBotonAs("Agregar servicios");
        } else {
            setBotonAs("");
        }
    }, [serviciosSeleccionados]);

    //OPCIONES PARA SABER SI ES NEGOCIABLE O NO
    const opciones = [
        {id:1, nombre:"Si",valor:true},
        {id:2, nombre:"No",valor:false}
    ];  

    //ALMACENAR LOS IDS DE LOS SERVICIOS SELECCIONADOS
    const handleSelectService = (selectedValue, index) => {
        const selectedService = serviciosSinRegistrar.find(servicio => servicio.idServicio === selectedValue);
        if (selectedService) {
            const updatedServicios = [...serviciosAdicionales];
            updatedServicios[index] = { idServicio: selectedService.idServicio };
            setServiciosAdicionales(updatedServicios);
    
            const updatedSeleccionados = [...serviciosSeleccionados];
            updatedSeleccionados[index] = selectedService.idServicio;
            setServiciosSeleccionados(updatedSeleccionados);
        }
    };

    //DESACTIVAR LOS VALORES SELECCIONADOS
    const isOptionDisabled = (option) => {
        return serviciosSeleccionados.some(servicio => servicio === option.idServicio);
    };

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
    
        const newServiciosSeleccionados = [...serviciosSeleccionados];
        newServiciosSeleccionados.splice(index, 1);
        setServiciosSeleccionados(newServiciosSeleccionados);
    
        setContador(prevContador => prevContador - 1);
    };

    // FUNCIÓN DEL BOTON PARA AGREGAR LOS SERVICIOS
    const agregarServicio = (data) => {
        const serviciosParaAgregar = data.serviciosAdicionales.filter(servicio => {
          return servicio.idServicio !== null && servicio.precio !== null && servicio.precio !== 0;
        }).map(servicio => ({
          idProveedor: { idProveedor: parseInt(proveedor.idProveedor) },
          idServicio: { idServicio: parseInt(servicio.idServicio) },
          precio: parseFloat(servicio.precio),
          negociable: servicio.negociable
        }));
        
        console.log(serviciosParaAgregar);

        servicioProveedorServiceInstance.postAddServicioProveedor(serviciosParaAgregar)
            .then(response => {
              console.log(response);
              console.log("Servicios agregados");
        })
            .catch(error => {
              console.log(error);
            });
            
      };

    return (
        <div>
            <form autoComplete="off" onSubmit={handleSubmit((data) => agregarServicio(data))}>
                {serviciosAdicionales.map((servicio, index) => (
                <div key={index} className="flex mt-4 w-full">
                    <div className="grid grid-cols-1 lg:grid-cols-3 md:gap-6 mt-4 mb-5 w-11/12">
                    <div className="relative z-0 w-full group">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">Servicio</label>
                        <Controller name={`serviciosAdicionales[${index}].idServicio`} control={control} defaultValue={servicio.idServicio} rules={{ required: true }} render={({ field }) => (
                        <Dropdown value={field.value} onChange={(e) => {handleSelectService(e.value, index);field.onChange(e.value);}} options={serviciosSinRegistrar} optionValue="idServicio" optionDisabled={isOptionDisabled} optionLabel="nombre" placeholder="Seleccione un servicio" panelClassName="custom-panel" pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                        )}/>
                        {errors.serviciosAdicionales?.[index]?.idServicio && <span className="text-red-500 text-sm">Agregue un servicio</span>}
                    </div>
                    <div className="relative z-0 w-full group">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">Precio</label>
                        <Controller name={`serviciosAdicionales[${index}].precio`} control={control} defaultValue={servicio.precio} rules={{ required: true }} render={({ field }) => (
                        <InputNumber value={field.value} onChange={(e) => field.onChange(e.value)} mode="currency" currency="PEN" className="block w-full" inputClassName="bg-gray-50 border border-gray-300 py-3 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                        )}/>
                        {errors.serviciosAdicionales?.[index]?.precio && <span className="text-red-500 text-sm">Ingrese el precio del servicio</span>}
                    </div>
                    <div className="relative z-0 w-full group">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">¿Es negociable?</label>
                        <Controller name={`serviciosAdicionales[${index}].negociable`} control={control} defaultValue={servicio.negociable} rules={{ required: true }} render={({ field }) => (
                        <Dropdown value={field.value} onChange={(e) => field.onChange(e.value)} options={opciones} optionValue="valor" optionLabel="nombre" placeholder="Seleccione una opción" panelClassName="custom-panel" pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                        )}/>
                        {errors.serviciosAdicionales?.[index]?.negociable && <span className="text-red-500 text-sm">Seleccione una opción</span>}
                    </div>
                    </div>
                    <div className="relative z-0 w-1/12 group flex items-center justify-end mt-6">
                    <button type="button" onClick={() => handleRemoveService(index)} className="bg-red-300 p-2 rounded-lg text-white text-sm font-bold"><FontAwesomeIcon icon={faMinus} /></button>
                    </div>
                </div>
                ))}
                {contador < 5 && (
                <div>
                    <button className="text-[#B4663F] font-bold text-sm mt-4" type="button" onClick={handleAddService}>Agregar más servicios</button>
                </div>
                )}
                {botonAS && 
                <div className="mb-5 flex justify-center my-16">
                    <button type="submit" className="w-full focus:outline-none text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">{botonAS}</button>
                </div>
                }
            </form>
        </div>
    );
};

export default AgregarServicio;