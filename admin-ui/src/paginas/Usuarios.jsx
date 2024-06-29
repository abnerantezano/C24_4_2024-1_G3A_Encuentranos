import React, { useState, useEffect } from "react";
// PRIME REACT
import { InputText } from 'primereact/inputtext';
// FONT AWESOME
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
// AXIOS
import UsuariosService from "../servicios/Usuarios";
import EditarModal from "../componentes/Modal/Usuarios/Editar";

function Usuarios() {

  const [usuarios, setUsuarios] = useState([]);
  const [filteredUsuarios, setFilteredUsuarios] = useState([]);
  
  const [selectedUsuario, setSelectedUsuario] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [busqueda, setBusqueda] = useState("");

  // LLAMAR LA LISTA DE USUARIOS
  useEffect(() => {
    getUsuarios();
  }, []);

  const getUsuarios = () => {
    UsuariosService.getLista()
      .then((UsuarioResponse) => {
        setUsuarios(UsuarioResponse);
        setFilteredUsuarios(UsuarioResponse); 
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const eliminarUsuario = (idUsuario) => {
    UsuariosService.deleteUsuario(idUsuario)
      .then((response) => {
        console.log(response);
        setUsuarios(usuarios.filter(usuario => usuario.id_usuario !== idUsuario));
        setFilteredUsuarios(filteredUsuarios.filter(usuario => usuario.id_usuario !== idUsuario));
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const openModal = (usuario = null) => {
    setSelectedUsuario(usuario);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedUsuario(null);
    setIsModalOpen(false);
  };

  const handleSearchChange = (e) => {
    setBusqueda(e.target.value);
    if (e.target.value === "") {
      setFilteredUsuarios(usuarios);
    } else {
      setFilteredUsuarios(
        usuarios.filter((usuario) =>
          usuario.correo.toLowerCase().includes(e.target.value.toLowerCase())
        )
      );
    }
  };

  return (
    <div className='mx-10'>
      <h1 className="mb-10 text-3xl font-bold text-[#8f5132]">Usuarios</h1>
      <div className="flex flex-wrap items-center justify-between mb-10">
        <form className="w-1/2">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <FontAwesomeIcon icon={faMagnifyingGlass} className="w-4 h-4 text-[#787171]" />
            </div>
            <InputText type="text" className="block w-full p-2 pl-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200" placeholder="Correo del usuario" value={busqueda} onChange={handleSearchChange} />
          </div>
        </form>
      </div>
      <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
        <table className="w-full text-sm text-left rtl:text-right text-gray-500">
          <thead className="text-xs text-gray-700 uppercase bg-[#ffdbca]">
            <tr>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                ID
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                CORREO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                TIPO DE USUARIO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                FECHA DE REGISTRO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                ESTADO
              </th>
              {/*<th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Edit</span>
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Eliminar</span>
              </th>*/}
            </tr>
          </thead>
          <tbody>
            {filteredUsuarios.map((usuario) => (
              <tr
                key={usuario.id_usuario}
                className="bg-white border-b"
              >
                <th
                  scope="row"
                  className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap"
                >
                  {usuario.id_usuario}
                </th>
                <td className="px-6 py-4">{usuario.correo}</td>
                <td className="px-6 py-4">{usuario.tipo.nombre}</td>
                <td className="px-6 py-4">{new Date(usuario.fh_creacion).toLocaleDateString()}</td>
                <td className="px-6 py-4">{usuario.estado}</td>
                {/*<td className="px-6 py-4 text-right">
                  <button onClick={() => openModal(usuario)} className="font-semibold text-[#BC7547]">
                    Editar
                  </button>
                </td>
                <td className="px-6 py-4 text-right">
                  <button
                    onClick={() => eliminarUsuario(usuario.id_usuario)}
                    className="font-semibold text-[#BC7547]"
                  >
                    Eliminar
                  </button>
                </td>*/}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {isModalOpen && (
        <EditarModal usuario={selectedUsuario} closeModal={closeModal} funcion={getUsuarios} />
      )}
    </div>
  );
}

export default Usuarios;
