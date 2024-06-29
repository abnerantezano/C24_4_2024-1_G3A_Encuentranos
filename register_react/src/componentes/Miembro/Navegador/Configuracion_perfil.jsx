import React from 'react';
import { Link, Route, Routes, useLocation } from 'react-router-dom';
import MiCuenta from '../../../paginas/Miembro/MiCuenta';
import MisServicios from '../../../paginas/Miembro/MisServicios';
import MisContratos from '../../../paginas/Miembro/MisContratos';
import Rol from '../Datos/Rol';

function Configuracion() {
    const location = useLocation();

    return (
        <Rol>
            {(rol) => (
                <div className='h-screen'>
                    <div className="bg-gradient-to-r from-[#F3C7AC] to-[#E8A477] w-full p-6 text-center text-white h-1/4">
                        {/* Your header content here */}
                    </div>
                    <div className="mx-auto bg-[#F5F5F5] h-3/4 w-full">
                        <div className='h-5/6 p-6 xl:px-36 absolute z-0 top-[16%] right-0 left-0'>
                            <div className='mx-auto bg-[#FAFAFA] w-full rounded-lg shadow-xl flex flex-wrap justify-start items-start h-full'>
                                <div className='w-1/4 p-16'>
                                    <ul>
                                        {rol.idTipo === 1 && (
                                            <>
                                                <li className={`mb-6 ${location.pathname === '/configuracion/mi-cuenta' ? 'text-[#B4663F] font-bold' : 'text-[#7D7D7D]'}`}>
                                                    <Link to="/configuracion/mi-cuenta">Mi cuenta</Link>
                                                </li>
                                                <li className={`mb-6 ${location.pathname === '/configuracion/mis-contratos' ? 'text-[#B4663F] font-bold' : 'text-[#7D7D7D]'}`}>
                                                    <Link to="/configuracion/mis-contratos">Mis contratos</Link>
                                                </li>
                                            </>
                                        )}
                                        {rol.idTipo === 2 && (
                                            <>
                                                <li className={`mb-6 ${location.pathname === '/configuracion/mi-cuenta' ? 'text-[#B4663F] font-bold' : 'text-[#7D7D7D]'}`}>
                                                    <Link to="/configuracion/mi-cuenta">Mi cuenta</Link>
                                                </li>
                                                <li className={`mb-6 ${location.pathname === '/configuracion/mis-servicios' ? 'text-[#B4663F] font-bold' : 'text-[#7D7D7D]'}`}>
                                                    <Link to="/configuracion/mis-servicios">Mis servicios</Link>
                                                </li>
                                                <li className={`mb-6 ${location.pathname === '/configuracion/mis-contratos' ? 'text-[#B4663F] font-bold' : 'text-[#7D7D7D]'}`}>
                                                    <Link to="/configuracion/mis-contratos">Mis contratos</Link>
                                                </li>
                                            </>
                                        )}
                                    </ul>
                                </div>
                                <div className='w-3/4 bg-white h-full rounded-r-lg'>
                                    <div className='p-16 h-full overflow-auto custom-scrollbar'>
                                        <Routes>
                                            {rol.idTipo === 1 && (
                                                <>
                                                    <Route path="mi-cuenta" element={<MiCuenta />} />
                                                    <Route path="mis-contratos" element={<MisContratos />} />
                                                </>
                                            )}
                                            {rol.idTipo === 2 && (
                                                <>
                                                    <Route path="mi-cuenta" element={<MiCuenta />} />
                                                    <Route path="mis-servicios" element={<MisServicios />} />
                                                    <Route path="mis-contratos" element={<MisContratos />} />
                                                </>
                                            )}
                                        </Routes>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </Rol>
    );
}

export default Configuracion;
