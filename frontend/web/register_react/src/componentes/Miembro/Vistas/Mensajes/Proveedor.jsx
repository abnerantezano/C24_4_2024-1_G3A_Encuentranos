import React, { useState, useEffect } from 'react';
import seleccionarChat from '../../../../imagenes/Mensajes/seleccionar_chat.png';
import MensajeServiceInstance from '../../../../servicios/Miembro/MensajeService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import InformacionDeUsuario from '../../Datos/InformacionDeUsuario';
import { InputText } from 'primereact/inputtext';

function MensajesProveedor({ chat }) {

    const [mensajes, setMensajes] = useState([]);
    const [nuevoMensaje, setNuevoMensaje] = useState('');

    //CARGAR MENSAJES AL INICIO Y EN LOS INTERVALOS DE TIEMPO
    useEffect(() => {
        cargarMensajes(); 

        const interval = setInterval(() => {
            cargarMensajes();
        }, 3000); 

        return () => clearInterval(interval);
    }, [chat]); 

    //FUNCION PARA CARGAR MENSAJES
    const cargarMensajes = () => {
        if (chat) {
            MensajeServiceInstance.getMensajes(chat.idChat)
                .then((mensajes) => {
                    setMensajes(mensajes);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    };

    //FUNCION ENVIAR MENSAJE
    const EnviarMensaje = (info) => {
        const mensaje = {
            idEmisor: { idUsuario: info.idUsuario },
            idReceptor: { idUsuario: chat.idCliente.idUsuario.idUsuario },
            mensaje: nuevoMensaje,
        };

        MensajeServiceInstance.postMensaje(chat.idChat, mensaje)
            .then((msg) => {
                setMensajes([...mensajes, msg]);
                setNuevoMensaje('');
            })
            .catch((error) => {
                console.error(error);
            });
    };

    //ENVIAR AL DAR ENTER
    const handleKeyDown = (event, info) => {
        if (event.key === 'Enter') {
            EnviarMensaje(info);
        }
    };

    //SI NO HAY UN ID DEL CHAT
    if (!chat) return (
        <div>
            <div className='w-full h-screen flex flex-col items-center justify-center'>
                <img className="w-1/2 mb-4" src={seleccionarChat} alt="Seleccione un chat" />
                <p className='text-lg text-[#787171]'>Seleccione un chat para visualizarla</p>
            </div>
        </div>
    );

    //FORMATEAR FECHA 
    const formatDate = (fecha) => {
        const date = new Date(fecha);
        const day = date.getUTCDate().toString().padStart(2, '0');
        const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
        const year = date.getUTCFullYear().toString();
        return `${day}/${month}/${year}`;
    }

    return (
        <InformacionDeUsuario>
            {(info) => (
                <div className='h-screen flex flex-col'>
                    <div className='flex items-center py-4 bg-[#F3C7AC] px-8'>
                        <img className='w-10 h-10 object-cover border-gray-300 rounded-full' src={chat.idCliente.idUsuario.imagenUrl} alt='Proveedor' />
                        <div className='ml-4'>
                            <h2 className='font-semibold text-white'>{chat.idCliente.nombre} {chat.idCliente.apellidoPaterno} {chat.idCliente.apellidoMaterno}</h2>
                            <p className='text-white text-sm'>{chat.idCliente.idUsuario.estado}</p>
                        </div>
                    </div>
                    <div className='flex-1 overflow-auto py-4 px-8 h-full custom-scrollbar'>
                        <ul>
                            {mensajes.map((mensaje, index) => (
                                <li key={mensaje.idMensaje} className={`mb-4 flex ${mensaje.idEmisor.idUsuario === info.idUsuario ? 'justify-end' : 'justify-start'}`}>
                                    <div className='flex flex-col' style={{ maxWidth: '75%' }}>
                                        <div className={`py-2 px-4 rounded-lg blocked ${mensaje.idEmisor.idUsuario === info.idUsuario ? 'bg-gradient-to-r from-[#E8A477] to-[#EFAF50] text-white text-right' : 'bg-[#EFEFEF] text-[#797979] text-left'}`} style={{ wordWrap: 'break-word' }}>
                                            {mensaje.mensaje}
                                        </div>
                                        <div className={`text-[#9D9C9C] text-sm blocked mt-1 ${mensaje.idEmisor.idUsuario === info.idUsuario ? 'text-end pr-1' : 'text-start pl-1'}`}>
                                            {formatDate(mensaje.fechaCreacion)}
                                        </div>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className='py-4 px-8'>
                        <div className="relative w-full">
                            <InputText
                                type="text"
                                value={nuevoMensaje}
                                onChange={(e) => setNuevoMensaje(e.target.value)}
                                onKeyDown={(e) => handleKeyDown(e, info)}
                                className="block w-full p-2 pr-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200"
                                placeholder='Escribe un mensaje...'
                            />
                            <div className="absolute inset-y-0 right-0 flex items-center pr-3">
                                <button type='button' onClick={() => EnviarMensaje(info)} className="bg-transparent border-none cursor-pointer outline-none focus:outline-none">
                                    <FontAwesomeIcon icon={faPaperPlane} className="w-4 h-4 text-[#787171]" />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </InformacionDeUsuario>
    );
}

export default MensajesProveedor;
