import React from 'react'
//COMPONENTES
import InformacionDeUsuario from '../componentes/Informacion/InformacionDeUsuario';
import InformacionCliente from '../componentes/Informacion/InformacionCliente';
import EditarCuenta from '../componentes/Diseños/Modal/EditarCuenta';
import SeccionPerfil from '../componentes/Diseños/Seccion_Perfil';
import InformacionProveedor from '../componentes/Informacion/InformacionProveedor';
import SeccionInformacionPersonal from '../componentes/Diseños/Seccion_InformacionPersonal';
import EditarPerfil from '../componentes/Diseños/Modal/EditarPerfil';
import EditarIPersonal from '../componentes/Diseños/Modal/EditarIPersonal';

function MiCuenta() {

  const maskPassword = (password) => {
    return '*'.repeat(password.length);
  };

  return (
    <InformacionDeUsuario>
      {(usuario)=> {return (
        <div>
          {usuario.idUsuario && (
            <div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-1/6 rounded-lg my-4 '>
                  <img className="w-28 h-28 rounded-full object-cover border-gray-300 border" src={usuario.imagenUrl} alt="Foto de perfil" />
                </div>
                <div className='w-4/6 my-4 px-8'>
                  <label className='font-bold text-base text-[#B4B4B4]'>Correo electrónico</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.correo}</p>
                  <label className='font-bold text-base text-[#B4B4B4]'>Contraseña</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{maskPassword(usuario.contrasena)}</p>
                </div>
                <div className='1/6 my-4'>
                  <EditarCuenta/>
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Perfil</h1>
                  {usuario.idTipo.idTipo === 1 ? (
                    <InformacionCliente>
                      {(cliente) => { 
                        return <SeccionPerfil usuario={cliente} />;
                      }}
                    </InformacionCliente>
                  ) : usuario.idTipo.idTipo === 2 ? (
                    <InformacionProveedor>
                      {(proveedor) => {
                        return <SeccionPerfil usuario={proveedor} />;
                      }}
                    </InformacionProveedor>
                  ) : null}
                </div>
                <div className='1/6 my-4'>
                  <EditarPerfil />
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Información personal</h1>
                  {usuario.idTipo.idTipo === 1 ? (
                    <InformacionCliente>
                      {(cliente) => { 
                        return <SeccionInformacionPersonal usuario={cliente} />;
                      }}
                    </InformacionCliente>
                  ) : usuario.idTipo.idTipo === 2 ? (
                    <InformacionProveedor>
                      {(proveedor) => {
                        return <SeccionInformacionPersonal usuario={proveedor} />;
                      }}
                    </InformacionProveedor>
                  ) : null}
                </div>
                <div className='1/6 my-4'>
                  <EditarIPersonal/>
                </div>
              </div>
          </div>
          )}
        </div>
      )}}
    </InformacionDeUsuario>
  )
}

export default MiCuenta;
