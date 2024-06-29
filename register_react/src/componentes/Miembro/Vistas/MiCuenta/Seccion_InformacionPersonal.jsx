import React from 'react'

const Seccion_InformacionPersonal = ({usuario}) => {
    
    const renderInfo = () => {
        return (
            <div>
                <div className='my-3 w-5/6'>
                    <div>
                        <label className='font-bold text-base text-[#B4B4B4]'>Nombre</label>
                        <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.nombre}</p>
                    </div>
                    <div className='grid grid-cols-2'>
                        <div>
                            <label className='font-bold text-base text-[#B4B4B4]'>Apellido paterno</label>
                            <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.apellidoPaterno}</p>
                        </div>
                        <div>
                            <label className='font-bold text-base text-[#B4B4B4]'>Apellido materno</label>
                            <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.apellidoMaterno}</p>
                        </div>
                    </div>
                    <div className='grid grid-cols-2'>
                        <div>
                            <label className='font-bold text-base text-[#B4B4B4]'>DNI</label>
                            <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.dni}</p>
                        </div>
                        <div>
                            <label className='font-bold text-base text-[#B4B4B4]'>Fecha de nacimiento</label>
                            <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.fechaNacimiento}</p>
                        </div>
                    </div>
                    <div className='grid grid-cols-2'>
                        <div>
                            <label className='font-bold text-base text-[#B4B4B4]'>Celular</label>
                            <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.celular}</p>
                        </div>
                        {usuario && usuario.idDistrito &&
                            <div>
                                <label className='font-bold text-base text-[#B4B4B4]'>Distrito</label>
                                <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.idDistrito.nombre}</p>
                            </div>
                        }
                    </div>
                </div>
            </div>
        )
    }
    return (
        <div>
            {renderInfo()}
        </div>
    )
}

export default Seccion_InformacionPersonal
