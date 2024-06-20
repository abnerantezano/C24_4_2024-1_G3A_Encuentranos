import React from 'react'
import InformacionProveedor from '../componentes/Informacion/InformacionProveedor';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
//COMPONENTES


function MiCuenta() {

  const maskPassword = (password) => {
    return '*'.repeat(password.length);
  };


  return (
    <InformacionProveedor>
      {(info)=> {return (
        <div>
          {info.idUsuario && (
            <div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-1/6 rounded-lg my-4'>
                  <img className="w-28 h-28 rounded-full object-cover" src={info.idUsuario.imagenUrl} alt="Foto de perfil" />
                </div>
                <div className='w-4/6  my-4'>
                  <label className='font-bold text-base text-[#B4B4B4]'>Correo</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{info.idUsuario.correo}</p>
                  <label className='font-bold text-base text-[#B4B4B4]'>Contraseña</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{maskPassword(info.idUsuario.contrasena)}</p>
                </div>
                <div className='1/6 my-4'>
                  <button className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold'>Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} /></button>
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Perfil</h1>
                  <div className='my-3 w-5/6'>
                    <label className='font-bold text-base text-[#B4B4B4]'>Descripción</label>
                    <p className='text-sm text-[#787171] mt-1 mb-2'>{info.descripcion ? info.descripcion : "Sin descripción"}</p>
                    <label className='font-bold text-base text-[#B4B4B4]'>Curriculum Vitae</label>
                    <div class="flex items-center justify-center w-1/2 mt-1 mb-2">
                      <label  class="flex flex-col items-center justify-center w-full h-10 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50  hover:bg-gray-100">
                          <p className='text-sm text-[#787171] '>{info.curriculumUrl ? info.descripcion : "Sin cargar archivo"}</p>
                          <input accept="application/pdf" type="file" class="hidden" />
                      </label>
                    </div> 
                  </div>
                </div>
                <div className='1/6 my-4'>
                  <button className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold'>Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} /></button>
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Información personal</h1>
                  <div className='my-3 w-5/6'>
                    <label className='font-bold text-base text-[#B4B4B4]'>Nombre</label>
                    <p className='text-sm text-[#787171] mt-1 mb-2'>{info.nombre}</p>
                    <div className='grid grid-cols-2'>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Apellido paterno</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.apellidoPaterno}</p>
                      </div>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Apellido materno</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.apellidoMaterno}</p>
                      </div>
                    </div>
                    <div className='grid grid-cols-2'>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>DNI</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.dni}</p>
                      </div>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Fecha de nacimiento</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.fechaNacimiento}</p>
                      </div>
                    </div>
                    <div className='grid grid-cols-2'>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Celular</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.celular}</p>
                      </div>
                      <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Distrito</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{info.idDistrito.nombre}</p>
                      </div>
                    </div>
                  </div>
                </div>
                <div className='1/6 my-4'>
                  <button className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold'>Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} /></button>
                </div>
              </div>
            </div>
          )}
        </div>
      )}}
    </InformacionProveedor>
  )
}

export default MiCuenta;
