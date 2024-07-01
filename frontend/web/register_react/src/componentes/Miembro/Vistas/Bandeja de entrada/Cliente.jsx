import React, { useState, useEffect } from 'react';
import seleccionarChat from '../../../../imagenes/Mensajes/seleccionar_chat.png';
import bandejaVacia from '../../../../imagenes/Mensajes/bandeja_vacia.png';
//REACT ROUTER DOM
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
//PRIME REACT
import { InputText } from 'primereact/inputtext';
//FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
//SERVICIOS
import ChatServiceInstance from '../../../../servicios/Miembro/ChatService';
import usuarioServiceInstance from '../../../../servicios/Miembro/UsuarioService';
import MensajeServiceInstance from '../../../../servicios/Miembro/MensajeService';
//COMPONENTES
import MensajesCliente from '../Mensajes/Cliente';

function BandejaDeEntradaCliente() {
    const [cliente, setCliente] = useState({});
    const [busqueda, setBusqueda] = useState("");
    const [chats, setChats] = useState([]);
    const [selectedChat, setSelectedChat] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        usuarioServiceInstance.getClienteInfo()
            .then((cliente) => {
                setCliente(cliente);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    useEffect(() => {
        if (cliente.idCliente) {
            const obtenerChatsConMensajes = async () => {
                const chats = await ChatServiceInstance.getPorCliente(cliente.idCliente);
                const chatsConMensajes = await Promise.all(chats.map(async (chat) => {
                    const mensajes = await MensajeServiceInstance.getMensajes(chat.idChat);
                    return { ...chat, mensajes };
                }));
                setChats(chatsConMensajes);
            };

            obtenerChatsConMensajes();

            const intervalId = setInterval(obtenerChatsConMensajes, 1000); 

            return () => clearInterval(intervalId); 
        }
    }, [cliente]);

    const handleChatSelect = (chat) => {
        setSelectedChat(chat);
        navigate(`/bandeja/${chat.idChat}`);
    };

    const obtenerUltimoMensaje = (chat) => {
        const mensajes = chat.mensajes;
        if (mensajes && mensajes.length > 0) {
            const ultimoMensaje = mensajes[mensajes.length - 1];
            return {
                texto: ultimoMensaje.mensaje.length > 50 ? `${ultimoMensaje.mensaje.substring(0, 47)}...` : ultimoMensaje.mensaje,
                fecha: new Date(ultimoMensaje.fechaCreacion).toLocaleString('es-ES', { timeZone: 'UTC' })
            };
        }
        return { texto: '', fecha: '' };
    };

    const filteredChats = chats.filter(chat => {
        const nombreCompleto = `${chat.idProveedor.nombre} ${chat.idProveedor.apellidoPaterno}`;
        return nombreCompleto.toLowerCase().includes(busqueda.toLowerCase());
    });

    // FORMATEAR FECHA 
    const formatearFecha = (fechaISO) => {
        const fecha = new Date(fechaISO);
        return fecha.toLocaleString('es-ES', {
          day: '2-digit', 
          month: '2-digit', 
          year: 'numeric', 
          hour: 'numeric', 
          minute: 'numeric', 
          hour12: true 
        });
    };
    
    return (
        <div className='w-full flex flex-wrap'>
            <div className='w-4/12 h-screen bg-[#F5F5F5] overflow-auto p-10'>
                <form>
                    <div className="relative w-full">
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <FontAwesomeIcon icon={faMagnifyingGlass} className="w-4 h-4 text-[#787171]" />
                        </div>
                        <InputText type="text" className="block w-full p-2 pl-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200" placeholder="Buscar" value={busqueda} onChange={(e) => setBusqueda(e.target.value)} />
                    </div>
                </form>
                <div className='w-full overflow-auto my-10'>
                    <ul>
                        {filteredChats.map(chat => {
                            const ultimoMensaje = obtenerUltimoMensaje(chat);
                            return (
                                <li key={chat.idChat}>
                                    <Link to={`/bandeja/${chat.idChat}`} onClick={() => handleChatSelect(chat)} className='mb-8'>
                                        <div className='flex flex-row items-start justify-start'>
                                            <div className='pr-4 mb-8'>
                                                <img className="w-16 h-16 object-cover border-gray-300 rounded-full" src={chat.idProveedor.idUsuario.imagenUrl} alt={`Imagen de ${chat.idProveedor.nombre}`} />
                                            </div>
                                            <div className='flex flex-col'>
                                                <h2 className='font-semibold text-[#635F5F]'>{chat.idProveedor.nombre} {chat.idProveedor.apellidoPaterno} {chat.idProveedor.apellidoMaterno}</h2>
                                                <p className='text-[#787171] text-sm'>{ultimoMensaje.texto}</p>
                                                <p className='text-[#787171] text-xs'>{formatearFecha(ultimoMensaje.fecha)}</p>
                                            </div>
                                        </div>
                                    </Link>
                                </li>
                            );
                        })}
                    </ul>
                </div>
            </div>
            <div className='w-8/12 bg-white h-screen'>
                {selectedChat ? (
                    <Routes>
                        <Route path="/:id/*" element={<MensajesCliente chat={selectedChat} />} />
                    </Routes>
                ) : filteredChats.length === 0 ? (
                    <div className='w-full h-screen flex flex-col items-center justify-center'>
                            <img className='w-1/2 mb-4' src={bandejaVacia} alt='Bandeja vacia' />
                            <p className='text-lg text-[#787171]'>La bandeja está vacía.</p>
                        </div>
                    
                ) : (
                    <div className='w-full h-screen flex flex-col items-center justify-center'>
                        <img className='w-1/2 mb-4' src={seleccionarChat} alt='Seleccione un chat' />
                        <p className='text-lg text-[#787171]'>Seleccione un chat para visualizarlo</p>
                    </div>
                ) }
            </div>
        </div>
    );
}

export default BandejaDeEntradaCliente;
