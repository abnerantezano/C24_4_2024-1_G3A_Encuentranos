import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import ProveedorServiceInstance from '../../servicios/Miembro/ProveedorService';
import { Rating } from 'primereact/rating';
import Rol from '../../componentes/Rol';

function PerfilProveedor() {

    const {id} = useParams();
    const [proveedor, setProveedor] = useState([]);

    useEffect(() => {
        ProveedorServiceInstance.getProveedorDetalle(id)
            .then((proveedor) => {
                setProveedor(proveedor);
            })
            .catch((error) => {
                console.error(error);
            })
    },[id]);

    return (
        <Rol>
            {(rol) => { 
                return(
                    <div className='w-full'>
                        <div className='mx-48 bg-white py-10'>
                            <div className='px-6 xl:px-36'>
                                <div className='flex flex-wrap items-center justify-between'>
                                   <div className='flex flex-wrap mb-6'>
                                        {proveedor.idUsuario && proveedor.idUsuario.imagenUrl && (
                                            <img className="w-28 h-28 rounded-full object-cover border-gray-300 border p-4" src={proveedor.idUsuario.imagenUrl} alt='Foto del proveedor'/>
                                        )}
                                        <div className='flex flex-col px-8'>
                                            <p className='text-sm text[#635F5F] mb-1'>Proveedor</p>
                                            <h1 className='text-base font-semibold mb-2'>{proveedor.nombre} {proveedor.apellidoPaterno} {proveedor.apellidoMaterno}</h1>
                                            <Rating value={proveedor.calificacionPromedio} readOnly cancel={false} pt={{ root: 'focus:ring-0', onIcon: 'text-[#EBC351] focus:ring-0', offIcon: 'text-[#B7B7B7] focus:ring-0' }} />
                                        </div>
                                   </div>
                                    {rol.idTipo === 1 ? (
                                        <div className='flex flex-wrap items-center justify-end'>
                                            <button className='text-[#918c8c] bg-[#E0E0E0] py-2 px-4 rounded-lg font-semibold border border-[#d6d6d6] mr-3'>Ver CV</button>
                                            <button className='text-[#918c8c] bg-[#E0E0E0] py-2 px-4 rounded-lg font-semibold border border-[#d6d6d6] mr-3'>Enviar mensaje</button>
                                            <button className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-semibold hover:bg-[#] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4]'>Contratar</button>
                                        </div>
                                    ) : null}
                                </div>
                                <div className='w-1/2 mb-5'>
                                    <h1 className='text-[#635F5F] font-semibold mb-2'>Contacto</h1>
                                    <div className='flex flex-col mb-1'>
                                        <div className='flex flex-wrap items-center justify-between'>
                                            <p className='text-sm text-[#A9A9A9] font-semibold'>Correo electrónico</p>
                                            <p className='text-[#635F5F] text-sm '>{proveedor.idUsuario.correo}</p>
                                        </div>
                                        <div className='flex flex-wrap items-center justify-between'>
                                            <p className='text-sm text-[#A9A9A9] font-semibold'>Celular</p>
                                            <p className='text-[#635F5F] text-sm '>+51 {proveedor.celular}</p>
                                        </div>
                                    </div>
                                </div>
                                <div className='w-1/2 mb-5'>
                                    <h1 className='text-[#635F5F] font-semibold mb-2'>Acerca de mi</h1>
                                    <p className='text-sm text-[#635F5F]'>
                                        {proveedor.descripcion ? proveedor.descripcion : "Sin descripción"}
                                    </p>
                                </div>
                                <div className='w-1/2 mb-5'>
                                    <h1 className='text-[#635F5F] font-semibold mb-2'>Servicios negociables</h1>
                                    <p className='text-sm text-[#635F5F]'>
                                        {proveedor.descripcion ? proveedor.descripcion : "Sin descripción"}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                )
            }}
        </Rol>
    )
}

export default PerfilProveedor
