import React, { useEffect, useState } from 'react';
//PRIME REACT
import { InputText } from 'primereact/inputtext';
import { DataView } from 'primereact/dataview';
//FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
//SERVICIOS
import usuarioServiceInstance from '../../../../servicios/Miembro/UsuarioService';
import ContratoServiceInstance from '../../../../servicios/Miembro/ContratosService';
import DetalleContratoServiceInstance from '../../../../servicios/Miembro/DetalleContrato';

function MisContratosProveedor() {
    const [busqueda, setBusqueda] = useState('');
    const [proveedor, setProveedor] = useState([]); 
    const [contratos, setContratos] = useState([]);

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
            DetalleContratoServiceInstance.getClientes(proveedor.idProveedor)
                .then((contratos) => {
                    setContratos(contratos);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }, [proveedor]);

    const filtrarContratos = (contratos) => {
        if (!busqueda) {
            return contratos;
        }
        const busquedaMinuscula = busqueda.toLowerCase();
        return contratos.filter((contrato) => {
            const nombreProveedor = `${contrato.idContrato.idCliente.nombre} ${contrato.idContrato.idCliente.apellidoPaterno}`.toLowerCase();
            return nombreProveedor.includes(busquedaMinuscula);
        });
    };

    const filteredData = filtrarContratos(contratos);

    //FORMATEAR FECHA 
    const formatDate = (fecha) => {
        const date = new Date(fecha);
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0'); 
        const year = date.getFullYear().toString();
        return `${day}/${month}/${year}`;
    }

    //FORMATEAR HORA
    const formatDateTime = (time24) => {
        const [hours24, minutes] = time24.split(':');
        let hours = parseInt(hours24, 10);
        const ampm = hours >= 12 ? 'PM' : 'AM';
        
        hours = hours % 12;
        hours = hours ? hours : 12; // La medianoche debería ser 12 AM
        
        return `${hours}:${minutes} ${ampm}`;
    }

    const listTemplate = (contrato, index) => (
        <div key={index} className='my-5'>
            <div className='border rounded-lg w-full py-6 px-8 grid grid-cols-2 lg:grid-cols-3 gap-4'>
                <div className='flex flex-col px-4'>
                    <div className='flex flex-wrap justify-start'>
                        <img className='w-20 h-20 rounded-full object-cover border-gray-300 border mb-6' src={contrato.idContrato.idCliente.idUsuario.imagenUrl} alt='Foto del proveedor'/>
                        <div className='flex flex-col pl-6'>
                            <h1 className='text-[#787171] text-sm'>Cliente</h1>
                            <p className='text-base font-semibold'>{`${contrato.idContrato.idCliente.nombre.split(' ')[0]} ${contrato.idContrato.idCliente.apellidoPaterno}`}</p>
                        </div>
                    </div>
                    <div className='flex flex-wrap items-center justify-between'>
                        <p className='text-sm font-semibold text-[#635F5F]'>Precio</p>
                        <p className='font-bold text-lg text-[#B4663F]'>{`S/ ${parseFloat(contrato.precioActual).toFixed(2)}`}</p>
                    </div>
                </div>
                <div className='flex flex-col px-4'>
                    <div className='flex flex-col mb-4'>
                        <p className='text-sm font-semibold text-[#635F5F] mb-2'>Tipo de servicio</p>
                        <p className='text-sm text-[#787171] mb-2'>{contrato.idServicio.nombre}</p>
                    </div>
                    <div className='flex flex-col mb-4'>
                        <p className='text-sm font-semibold text-[#635F5F] mb-2'>Estado</p>
                        <p className='text-sm text-[#787171] mb-2'>{contrato.idContrato.estado}</p>
                    </div>
                </div>
                <div className='flex flex-col px-4'>
                    <div className='flex flex-col mb-4'>
                        <p className='text-sm font-semibold text-[#635F5F] mb-2'>Fecha del contrato</p>
                        <p className='text-sm text-[#787171] mb-2'>{formatDate(contrato.idContrato.fhCreacion)}</p>
                    </div>
                    <div className='flex flex-col mb-4'>
                        <p className='text-sm font-semibold text-[#635F5F] mb-2'>Fecha del servicio</p>
                        <div className='flex flex-wrap items-center justify-between mb-2'>
                            <p className='text-sm text-[#787171]'>Inicio</p>
                            <p className='text-sm text-[#787171]'>{formatDate(contrato.idContrato.fechaInicio)} - {formatDateTime(contrato.idContrato.hiServicio)} </p>
                        </div>
                        <div className='flex flex-wrap items-center justify-between mb-2'>
                            <p className='text-sm text-[#787171]'>Fin</p>
                            <p className='text-sm text-[#787171]'>{formatDate(contrato.idContrato.fechaFin)} - {formatDateTime(contrato.idContrato.hfServicio)}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

    return (
        <div>
            {contratos.length === 0 ? (
                <div>
                    <p>No tienes contratos registrados.</p>
                </div>
            ) : (
                <div>
                    <form>
                        <div className="relative">
                            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                <FontAwesomeIcon icon={faMagnifyingGlass} className="w-4 h-4 text-[#787171]" />
                            </div>
                            <InputText
                                type="text"
                                className="block w-1/2 p-2 pl-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200"
                                placeholder="Buscar cliente"
                                value={busqueda}
                                onChange={(e) => setBusqueda(e.target.value)}
                            />
                        </div>
                    </form>
                    <DataView
                        value={filteredData}
                        className='bg-transparent'
                        itemTemplate={listTemplate}
                        paginator
                        paginatorClassName='bg-transparent text-gray-500'
                        rows={5}
                    />
                </div>
            )}
        </div>
    );
}

export default MisContratosProveedor;
