import React, { useEffect, useState } from 'react'
import Reportes from '../../servicios/Reportes';
//SERVICIOS


function TopProveedores() {

    const [topProveedores, setTopProveedores] =useState([]);

    useEffect(() => {
        Reportes.getTopProveedores()
            .then((proveedores) => {
                setTopProveedores(proveedores);
            })
            .catch((error) => {
                console.error(error);
            });
    });

    return (
        <div className='bg-white w-full p-10 mb-10 rounded-lg shadow-xl'> 
            <div class="relative">
                <h2 className="mb-5 font-bold text-gray-700 text-lg">Top de proveedores</h2>
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 border border-[#ffdbca]">
                    <thead class="text-xs uppercase bg-[#ffdbca]">
                        <tr>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547] ">
                                Top
                            </th>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547]">
                                Nombres
                            </th>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547]">
                                Apellido paterno
                            </th>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547]">
                                Apellido materno
                            </th>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547]">
                                Correo electrónico
                            </th>
                            <th scope="col" class="px-6 py-3 font-bold text-[#BC7547]">
                                Celular
                            </th>
                        </tr>
                    </thead>
                    <tbody className='bg-white'>
                        {topProveedores.map((proveedor, index) => {
                            return (
                                <tr key={index} className="bg-white border border-[#ffdbca]">
                                    <td scope="row" className="px-6 py-4 font-medium text-gray-900 wditespace-nowrap">
                                        {index + 1 } 
                                    </td>
                                    <td scope="row" className="px-6 py-4">
                                        {proveedor.nombre} 
                                    </td>
                                    <td className="px-6 py-4">
                                        {proveedor.apellido_paterno} 
                                    </td>
                                    <td className="px-6 py-4">
                                        {proveedor.apellido_materno} 
                                    </td>
                                    <td className="px-6 py-4">
                                        {proveedor.usuario.correo} 
                                    </td>
                                    <td className="px-6 py-4">
                                        +51 {proveedor.celular} 
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default TopProveedores
