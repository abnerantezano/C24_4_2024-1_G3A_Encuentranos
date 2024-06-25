import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Mensajes from './Mensajes'; // Asumiendo que este componente existe para mostrar los mensajes
import { InputText } from 'primereact/inputtext';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

function BandejaDeEntrada() {

    //OBTENER ID DEL PARAMETRO
    const { id } = useParams();

    //MANEJAR ESTADOS DE VARIABLES
    const [busqueda, setBusqueda] = useState("");
    const [chats, setChats] = useState([]);
    const [selectedChat, setSelectedChat] = useState(null); 

    //VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate(); 

    useEffect(() => {
        // Aquí cargarías los chats, por ejemplo desde una API
        const fetchedChats = [
        { id: 1, nombre: 'Chat 1' },
        { id: 2, nombre: 'Chat 2' },
        { id: 3, nombre: 'Chat 3' },
        ];
        setChats(fetchedChats);
    }, []);

    // Manejar la selección del chat y la navegación
    useEffect(() => {
        // Buscar el chat seleccionado por el ID en la URL
        const chat = chats.find(chat => chat.id === parseInt(id));
        setSelectedChat(chat);
    }, [id, chats]);

    // Función para manejar el clic en un chat
    const handleChatClick = (id) => {
        navigate(`/bandeja/${id}`); // Navegar a la ruta del chat seleccionado
    };

    return (
        <div className='w-full flex flex-wrap'>
            <div className='w-1/4 h-screen bg-[#F5F5F5] overflow-auto p-10'>
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
                        <li key={chat.id}>
                        <button onClick={() => handleChatClick(chat.id)}>
                            {chat.nombre}
                        </button>
                        </li>
                    ))}
                    </ul>
                </div>
            </div>
            <div className='w-3/4 bg-white h-screen'>
                {selectedChat && <Mensajes id={selectedChat.id} />}
            </div>
        </div>
    );
}

export default BandejaDeEntrada;
