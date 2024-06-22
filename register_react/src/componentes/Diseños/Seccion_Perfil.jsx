import React from 'react';

const SeccionPerfil =({usuario}) => {

  const renderTipoUsuario = () => {
    if (usuario && usuario.idUsuario && usuario.idUsuario.idTipo) {
      switch (usuario.idUsuario.idTipo.idTipo) {
        case 1:
          return (
            <div className='my-3 w-5/6'>
              <label className='font-bold text-base text-[#B4B4B4]'>Descripción</label>
              <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.descripcion ? usuario.descripcion : "Sin descripción"}</p>
            </div>
          );
        case 2:
          return (
            <div className='my-3 w-5/6'>
              <label className='font-bold text-base text-[#B4B4B4]'>Descripción</label>
              <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.descripcion ? usuario.descripcion : "Sin descripción"}</p>
              <label className='font-bold text-base text-[#B4B4B4]'>Curriculum Vitae</label>
              <div className="flex items-center justify-center w-1/2 mt-1 mb-2">
                <label className="flex flex-col items-center justify-center w-full h-10 border-2 border-gray-300 border-dashed rounded-lg bg-gray-50 hover:bg-gray-100">
                  <p className='text-sm text-[#787171]'>{usuario.curriculumUrl ? usuario.curriculumUrl : "Sin cargar archivo"}</p>
                  <input accept="application/pdf" type="file" className="hidden" disabled />
                </label>
              </div>
            </div>
          );
        default:
          return null;
      }
    } else {
      return null; 
    }

  }

  return (
    <div>
      {renderTipoUsuario()}
    </div>
  );
}

export default SeccionPerfil;
