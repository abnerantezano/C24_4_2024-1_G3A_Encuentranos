import React, { useEffect, useState } from "react";
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
// PRIME REACT
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
// AXIOS
import servicioProveedorServiceInstance from "../../servicios/Miembro/ServicioProveedor";
import usuarioServiceInstance from "../../servicios/Miembro/UsuarioService";

const AgregarServicio = ({ onServiciosAgregados }) => {

    //MANEJAR ESTADOS DE LAS VARIABLES
    const [proveedor, setProveedor] = useState({});
    const [serviciosSinRegistrar, setServiciosSinRegistrar] = useState([]);
    const [serviciosAdicionales, setServiciosAdicionales] = useState([]);
    const [botonAS, setBotonAs] = useState("");
    const [contador, setContador] = useState(0);
    const [errores, setErrores] = useState([]);

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
        if (proveedor.idProveedor) {
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
        if (serviciosAdicionales.length > 0) {
            setBotonAs("Agregar servicios");
        } else {
            setBotonAs("");
        }
    }, [serviciosAdicionales]);

    //OPCIONES PARA SABER SI ES NEGOCIABLE O NO
    const opciones = [
        { id: 1, nombre: "Si", valor: true },
        { id: 2, nombre: "No", valor: false }
    ];

    // ALMACENAR LOS IDS DE LOS SERVICIOS SELECCIONADOS
    const handleSelectService = (selectedValue, index) => {
        const selectedService = serviciosSinRegistrar.find(servicio => servicio.idServicio === selectedValue);
        if (selectedService) {
            const updatedServicios = [...serviciosAdicionales];
            updatedServicios[index] = { ...updatedServicios[index], idServicio: selectedService.idServicio };
            setServiciosAdicionales(updatedServicios);
        }
    };

    const handleChangePrecio = (value, index) => {
        const updatedServicios = [...serviciosAdicionales];
        updatedServicios[index] = { ...updatedServicios[index], precio: value };
        setServiciosAdicionales(updatedServicios);
    };

    const handleChangeNegociable = (value, index) => {
        const updatedServicios = [...serviciosAdicionales];
        updatedServicios[index] = { ...updatedServicios[index], negociable: value };
        setServiciosAdicionales(updatedServicios);
    };

    //DESACTIVAR LOS VALORES SELECCIONADOS
    const isOptionDisabled = (option) => {
        return serviciosAdicionales.some(servicio => servicio.idServicio === option.idServicio);
    };

    // AGREGAR OTRO CONTENIDO PARA SERVICIOS Y PRECIO
    const handleAddService = () => {
        if (contador < 6) {
            setServiciosAdicionales([...serviciosAdicionales, { idServicio: null, precio: null, negociable: null }]);
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

    // FUNCIÓN DE VALIDACIÓN
    const validarServicios = () => {
        const errores = [];
        serviciosAdicionales.forEach((servicio, index) => {
            if (!servicio.idServicio) {
                errores[index] = { ...errores[index], idServicio: "Agregue un servicio" };
            }
            if (servicio.precio == null) {
                errores[index] = { ...errores[index], precio: "Ingrese el precio del servicio" };
            }
            if (servicio.negociable == null) {
                errores[index] = { ...errores[index], negociable: "Seleccione una opción" };
            }
        });
        setErrores(errores);
        return errores.length === 0;
    };

    // FUNCIÓN DEL BOTON PARA AGREGAR LOS SERVICIOS
    const agregarServicio = () => {
        if (!validarServicios()) {
            return;
        }

        const serviciosParaAgregar = serviciosAdicionales.filter(servicio => {
            return servicio.idServicio !== null && servicio.precio !== null && servicio.precio !== 0 && servicio.negociable !== null;
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
                window.location.reload();
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <div className="w-10/12">
            <form autoComplete="off" onSubmit={(e) => { e.preventDefault(); agregarServicio(); }}>
                {serviciosAdicionales.map((servicio, index) => (
                    <div key={index} className="flex mt-4 w-full">
                        <div className="grid grid-cols-1 lg:grid-cols-3 md:gap-6 mt-4 mb-5 w-11/12">
                            <div className="relative z-0 w-full group">
                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">Servicio</label>
                                <Dropdown
                                    value={servicio.idServicio}
                                    onChange={(e) => handleSelectService(e.value, index)}
                                    options={serviciosSinRegistrar}
                                    optionValue="idServicio"
                                    optionDisabled={isOptionDisabled}
                                    optionLabel="nombre"
                                    placeholder="Seleccione un servicio"
                                    panelClassName="custom-panel"
                                    pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }}
                                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                />
                                {errores[index]?.idServicio && <span className="text-red-500 text-sm">{errores[index].idServicio}</span>}
                            </div>
                            <div className="relative z-0 w-full group">
                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">Precio</label>
                                <InputNumber
                                    value={servicio.precio}
                                    onValueChange={(e) => handleChangePrecio(e.value, index)}
                                    mode="currency"
                                    currency="PEN"
                                    className="block w-full"
                                    inputClassName="bg-gray-50 border border-gray-300 py-3 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5"
                                />
                                {errores[index]?.precio && <span className="text-red-500 text-sm">{errores[index].precio}</span>}
                            </div>
                            <div className="relative z-0 w-full group">
                                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mt-2">¿Es negociable?</label>
                                <Dropdown
                                    value={servicio.negociable}
                                    onChange={(e) => handleChangeNegociable(e.value, index)}
                                    options={opciones}
                                    optionValue="valor"
                                    optionLabel="nombre"
                                    placeholder="Seleccione una opción"
                                    panelClassName="custom-panel"
                                    pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }}
                                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                />
                                {errores[index]?.negociable && <span className="text-red-500 text-sm">{errores[index].negociable}</span>}
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
