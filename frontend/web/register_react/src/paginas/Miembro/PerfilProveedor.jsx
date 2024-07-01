import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
//PRIME REACT
import { Rating } from 'primereact/rating';
//COMPONENTES
import Rol from '../../componentes/Miembro/Datos/Rol';
//SERVICIOS
import servicioProveedorServiceInstance from '../../servicios/Miembro/ServicioProveedor';
import ProveedorServiceInstance from '../../servicios/Miembro/ProveedorService';
import ContratarProveedor from '../../componentes/Miembro/Modal/Contratar/ContratarProveedor';
import usuarioServiceInstance from '../../servicios/Miembro/UsuarioService';
import ChatServiceInstance from '../../servicios/Miembro/ChatService';
import DetalleCalificacionServiceInstance from '../../servicios/Miembro/DetalleCalificacionService';

function PerfilProveedor() {

    const { id } = useParams();
    const [proveedor, setProveedor] = useState([]);
    const [cliente, setCliente] = useState([]);
    const [serviciosNegociables, setServiciosNegociables] = useState([]);
    const [serviciosNoNegociables, setServiciosNoNegociables] = useState([]);
    const [reseñas, setReseñas] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        usuarioServiceInstance.getClienteInfo()
            .then((cliente) => {
                setCliente(cliente);
            })
            .catch((error) => {
                console.error(error);
            })
    }, []);

    useEffect(() => {
        ProveedorServiceInstance.getProveedorDetalle(id)
            .then((proveedor) => {
                setProveedor(proveedor);
            })

        servicioProveedorServiceInstance.getServiciosNegociables(id)
            .then((serviciosNegociables) => {
                setServiciosNegociables(serviciosNegociables);
            })

        servicioProveedorServiceInstance.getServiciosNoNegociables(id)
            .then((serviciosNoNegociables) => {
                setServiciosNoNegociables(serviciosNoNegociables);
            })

        DetalleCalificacionServiceInstance.getCalificaciones(id)
            .then((calificaciones) => {
                setReseñas(calificaciones);
            })
    }, [id]);

    const crearChat = () => {

        const fechaActual = new Date().toISOString();

        const chat = {
            idProveedor : {idProveedor: parseInt(id)},
            idCliente: {idCliente: parseInt(cliente.idCliente)},
            estado: "Activo",
            fhCreacion: fechaActual
        }
        console.log(chat);
        ChatServiceInstance.postChat(chat)
            .then((chatData) => {
                console.log(chatData);
                navigate(`/bandeja/${chatData.idChat}`)
            })
            .catch((error) => {
                console.error(error);
            });

    }

    console.log(reseñas);

    return (
        <Rol>
            {(rol) => {
                return (
                    <div className='relative'>
                        <svg className='absolute top-0 left-0 z-[-10]' xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                            <path fill="#E8A477" fill-opacity="1" d="M0,224L24,213.3C48,203,96,181,144,186.7C192,192,240,224,288,213.3C336,203,384,149,432,144C480,139,528,181,576,208C624,235,672,245,720,250.7C768,256,816,256,864,261.3C912,267,960,277,1008,272C1056,267,1104,245,1152,213.3C1200,181,1248,139,1296,138.7C1344,139,1392,181,1416,202.7L1440,224L1440,0L1416,0C1392,0,1344,0,1296,0C1248,0,1200,0,1152,0C1104,0,1056,0,1008,0C960,0,912,0,864,0C816,0,768,0,720,0C672,0,624,0,576,0C528,0,480,0,432,0C384,0,336,0,288,0C240,0,192,0,144,0C96,0,48,0,24,0L0,0Z"></path>
                        </svg>
                        <div className='relative z-0 bg-white py-10 mx-auto top-10 w-3/4 rounded-lg shadow-xl '>
                            <div className='px-6 xl:px-36'>
                                <div className='flex flex-wrap items-center justify-between'>
                                    <div className='flex flex-wrap mb-6'>
                                        {proveedor.idUsuario && proveedor.idUsuario.imagenUrl && (
                                            <img
                                                className='w-28 h-28 rounded-full object-cover border-gray-300 border'
                                                src={proveedor.idUsuario.imagenUrl}
                                                alt='Foto del proveedor'
                                            />
                                        )}
                                        <div className='flex flex-col px-8'>
                                            <p className='text-sm text[#635F5F] mb-1'>Proveedor</p>
                                            <h1 className='text-base font-semibold mb-2'>
                                                {proveedor.nombre} {proveedor.apellidoPaterno}{' '}
                                                {proveedor.apellidoMaterno}
                                            </h1>
                                            <Rating
                                                value={proveedor.calificacionPromedio}
                                                readOnly
                                                cancel={false}
                                                pt={{
                                                    root: 'focus:ring-0',
                                                    onIcon: 'text-[#EBC351] focus:ring-0',
                                                    offIcon: 'text-[#B7B7B7] focus:ring-0',
                                                }}
                                            />
                                        </div>
                                    </div>
                                    {rol.idTipo === 1 ? (
                                        <div className='flex flex-wrap items-center justify-end'>
                                            <button className='text-sm text-[#918c8c] bg-[#E0E0E0] py-2 px-4 rounded-lg font-semibold border border-[#d6d6d6] mr-3 focus:ring-4 focus:ring-[#f3e4d7] hover:bg-[#b9b9b9] hover:text-white'>
                                                Ver CV
                                            </button>
                                            <button type="button" onClick={crearChat} className='text-sm text-[#918c8c] bg-[#E0E0E0] py-2 px-4 rounded-lg font-semibold border border-[#d6d6d6] mr-3 focus:ring-4 focus:ring-[#f3e4d7] hover:bg-[#b9b9b9] hover:text-white'>
                                                Enviar mensaje
                                            </button>
                                            <ContratarProveedor idProveedor={id} />
                                        </div>
                                    ) : null}
                                </div>
                                <div className='w-full mb-5'>
                                    <h1 className='text-[#635F5F] font-semibold mb-2'>Contacto</h1>
                                    <div className='flex flex-col mb-1'>
                                        <div className='flex flex-wrap items-center justify-between mb-2'>
                                            <p className='text-sm text-[#A9A9A9] font-semibold'>
                                                Correo electrónico
                                            </p>
                                            <p className='text-[#635F5F] text-sm '>
                                                {proveedor.idUsuario.correo}
                                            </p>
                                        </div>
                                        <div className='flex flex-wrap items-center justify-between'>
                                            <p className='text-sm text-[#A9A9A9] font-semibold mb-2'>
                                                Celular
                                            </p>
                                            <p className='text-[#635F5F] text-sm '>
                                                +51 {proveedor.celular}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div className='w-full mb-5'>
                                    <h1 className='text-[#635F5F] font-semibold mb-2'>Acerca de mi</h1>
                                    <p className='text-sm text-[#635F5F]'>
                                        {proveedor.descripcion ? proveedor.descripcion : 'Sin descripción'}
                                    </p>
                                </div>
                                <div className='grid grid-cols-2 gap-4'>
                                    <div className='w-full mb-5 pr-8'>
                                        <h1 className='text-[#635F5F] font-semibold mb-2 text-base'>
                                            Servicios negociables
                                        </h1>
                                        {serviciosNegociables.length > 0 ? (
                                            serviciosNegociables.map((servicio) => {
                                                return (
                                                    <div className='flex flex-wrap items-center justify-between mb-2'>
                                                        <p className='text-sm text-[#A9A9A9] font-semibold'>
                                                            {servicio.idServicio.nombre}
                                                        </p>
                                                        <p className='text-[#635F5F] text-sm '>
                                                            S/ {servicio.precio}
                                                        </p>
                                                    </div>
                                                );
                                            })
                                        ) : (
                                            <p className='text-[#635F5F] text-sm '>
                                                No hay servicios registrados en esta categoría.
                                            </p>
                                        )}
                                    </div>
                                    <div className='w-full mb-10 pl-8'>
                                        <h1 className='text-[#635F5F] font-semibold mb-2'>
                                            Servicios no negociables
                                        </h1>
                                        {serviciosNoNegociables.length > 0 ? (
                                            serviciosNoNegociables.map((servicio) => {
                                                return (
                                                    <div className='flex flex-wrap items-center justify-between mb-2'>
                                                        <p className='text-sm text-[#A9A9A9] font-semibold'>
                                                            {servicio.idServicio.nombre}
                                                        </p>
                                                        <p className='text-[#635F5F] text-sm '>
                                                            S/ {servicio.precio}
                                                        </p>
                                                    </div>
                                                );
                                            })
                                        ) : (
                                            <p className='text-[#635F5F] text-sm '>
                                                No hay servicios registrados en esta categoría.
                                            </p>
                                        )}
                                    </div>
                                </div>
                                <div>
                                    <h1 className='text-[#635F5F] font-semibold mb-5 text-base'>Reseñas</h1>
                                    {reseñas.length > 0 ? (
                                            reseñas.map((reseña) => {
                                                return (
                                                    <div className='flex flex-wrap items-center mb-2' key={reseña.id}>
                                                       <div className='border w-full p-4 rounded-xl'>
                                                            <div className='flex flex-row items-center justify-start mb-5'>
                                                                <img  className="w-16 h-16 border rounded-full object-cover" src={reseña.idCalificacion.cliente.idUsuario.imagenUrl} />
                                                                <div  className='pl-4'>
                                                                    <h1 className='font-semibold'>{reseña.idCalificacion.cliente.nombre} {reseña.idCalificacion.cliente.apellidoPaterno} {reseña.idCalificacion.cliente.apellidoMaterno}</h1>
                                                                    <p className='text-[#787171] text-sm'>{reseña.idServicio.nombre}</p>
                                                                </div>
                                                            </div>
                                                            <div className='mb-5'>
                                                                <p className='text-sm mb-1'><Rating value={reseña.idCalificacion.calificacion} readOnly cancel={false} pt={{ root: 'focus:ring-0', onIcon: 'text-[#EBC351] focus:ring-0', offIcon: 'text-[#B7B7B7] focus:ring-0' }} /></p>
                                                            </div>
                                                            <div className=''>
                                                                <p className='mb-1'>{reseña.idCalificacion.comentario}</p>
                                                            </div>
                                                       </div>
                                                    </div>
                                                );
                                            })
                                        ) : (
                                            <p className='text-[#635F5F] text-sm '>
                                                No hay reseñas.
                                            </p>
                                        )}
                                </div>
                            </div>
                        </div>
                    </div>
                );
            }}
        </Rol>
    );
}

export default PerfilProveedor;
