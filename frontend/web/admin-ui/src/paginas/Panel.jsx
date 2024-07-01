import React from 'react'
//COMPONENTES
import TopProveedores from '../componentes/Panel/TopProveedores';
import ServiciosProveedores from '../componentes/Panel/ServiciosProveedores';
import ClientesContratos from '../componentes/Panel/ClientesContratos';
import UsuariosRecientes from '../componentes/Panel/UsuariosRecientes';
import ContratosRecientes from '../componentes/Panel/ContratosRecientes';

function Panel() {

    return (
        <div className='mx-10'>
        <h1 className="mb-10 text-3xl font-bold text-[#8f5132]">Panel</h1>
        <div>
            <div className='grid grid-cols-2 gap-4'>
                <ServiciosProveedores/>
                <ClientesContratos/>
            </div>
            <div className='grid grid-cols-2 gap-4'>
                <UsuariosRecientes/>
                <ContratosRecientes/>
            </div>
            <TopProveedores/>
        </div>
        </div>
    )
}

export default Panel
