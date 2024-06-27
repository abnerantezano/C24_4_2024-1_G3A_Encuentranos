import React, { useEffect, useState } from 'react'
import Reportes from '../../servicios/Reportes';

function ClientesContratos() {

    const [clientes, setClientes] = useState([]);

    useEffect(() => {
        Reportes.getClientesContratos()
            .then((clientes) => {
                setClientes(clientes);
            })
            .catch((error) => {
                console.error(error);
            })
    });

    return (
        <div className='bg-white rounded-lg shadow-xl w-full p-10 mr-4 mb-10'>
            <h2 className="mb-5 font-bold text-gray-700 text-lg">Clientes principales</h2>
            <div>
                {clientes.map((cliente) => {

                    return (
                        <div key={cliente.id_cliente} className='flex flex-wrap items-center justify-start'>
                            <div className='p-4 w-1/4'>
                                <img src={cliente.usuario.imagen_url} alt="Foto de perfil" className='border w-20 h-20 rounded-full'/>
                            </div>
                            <div className='flex flex-wrap items-center justify-between w-3/4'>
                                <div className='p-4 w-full'>
                                    <h2 className='text-base text-gray-800 font-semibold mb-2'>{cliente.nombre} {cliente.apellido_paterno} {cliente.apellido_materno}</h2>
                                    <p className='text-sm font-normal text-gray-600'>{cliente.usuario.correo}</p>
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    )
}

export default ClientesContratos
