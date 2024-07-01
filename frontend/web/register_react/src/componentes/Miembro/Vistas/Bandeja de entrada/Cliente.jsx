import React, { useState, useEffect } from 'react';
import { Link, Route, Routes, useNavigate} from 'react-router-dom';
import Mensajes from '../../../../paginas/Miembro/Mensajes';
import { InputText } from 'primereact/inputtext';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import ChatServiceInstance from '../../../../servicios/Miembro/ChatService';
import usuarioServiceInstance from '../../../../servicios/Miembro/UsuarioService';

function BandejaDeEntradaCliente() {

    const [cliente, setCliente] = useState([]);
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
            ChatServiceInstance.getPorCliente(cliente.idCliente)
                .then((chats) => {
                    setChats(chats);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }, [cliente]);

    const handleChatSelect = (chat) => {
        setSelectedChat(chat);
        navigate(`/bandeja/${chat.idChat}`);
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
                        {chats.map(chat => (
                            <li key={chat.idChat}>
                                <Link to={`/bandeja/${chat.idChat}`} onClick={() => handleChatSelect(chat)} className='mb-8'>
                                    <div className='flex flex-wrap items-center justify-start'>
                                        <div className='pr-4 mb-8'>
                                            <img className="w-16 h-16 object-cover border-gray-300 rounded-full" src={chat.idProveedor.idUsuario.imagenUrl} />
                                        </div>
                                        <div className='flex flex-col'>
                                            <h2 className='font-semibold text-[#635F5F]'>{chat.idProveedor.nombre} {chat.idProveedor.apellidoPaterno} {chat.idProveedor.apellidoMaterno}</h2>
                                        </div>
                                    </div>
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
            <div className='w-8/12 bg-white h-screen'>
                <Routes>
                    <Route path="/:id/*" element={<Mensajes chat={selectedChat} />} />
                </Routes>
            </div>
        </div>
    );
}

export default BandejaDeEntradaCliente;
