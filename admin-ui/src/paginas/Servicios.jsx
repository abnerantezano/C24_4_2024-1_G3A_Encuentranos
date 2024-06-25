import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
// PRIME REACT
import { InputText } from 'primereact/inputtext';
// FONT AWESOME
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
// AXIOS
import ServiciosService from "../servicios/Servicios";
import EditarModal from "../componentes/Modal/Servicios/Editar";
import CrearModal from "../componentes/Modal/Servicios/Crear"; // Importa el modal de creación

function Servicios() {

  // DECLARAR UNA VARIABLE PARA LA NAVEGACIÓN
  const navigate = useNavigate();

  const [servicios, setServicios] = useState([]);
  const [filteredServicios, setFilteredServicios] = useState([]);
  
  const [selectedServicio, setSelectedServicio] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEdit, setIsEdit] = useState(false); 

  const [busqueda, setBusqueda] = useState("");

  // LLAMAR LA LISTA DE SERVICIOS
  useEffect(() => {
    getServicios();
  }, []);

  const getServicios = () => {
    ServiciosService.getLista()
      .then((ServicioResponse) => {
        setServicios(ServicioResponse);
        setFilteredServicios(ServicioResponse);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const eliminarServicio = (idServicio) => {
    ServiciosService.deleteServicio(idServicio)
      .then((response) => {
        console.log(response);
        setServicios(servicios.filter(servicio => servicio.id_servicio !== idServicio));
        setFilteredServicios(filteredServicios.filter(servicio => servicio.id_servicio !== idServicio));
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const openModal = (servicio = null) => {
    setSelectedServicio(servicio);
    setIsEdit(!!servicio); // Establece true si hay un servicio (editar), false si no (crear)
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedServicio(null);
    setIsModalOpen(false);
  };

  const handleSearchChange = (e) => {
    setBusqueda(e.target.value);
    if (e.target.value === "") {
      setFilteredServicios(servicios); // Si el campo de búsqueda está vacío, muestra todos los servicios
    } else {
      setFilteredServicios(
        servicios.filter((servicio) =>
          servicio.nombre.toLowerCase().includes(e.target.value.toLowerCase())
        )
      );
    }
  };

  return (
    <div className='mx-10'>
      <h1 className="mb-10 text-3xl font-bold text-[#8f5132]">Servicios</h1>
      <div className="flex flex-wrap items-center justify-between mb-10">
        <form className="w-1/2">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <FontAwesomeIcon icon={faMagnifyingGlass} className="w-4 h-4 text-[#787171]" />
            </div>
            <InputText type="text" className="block w-full p-2 pl-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200" placeholder="Nombre del servicio" value={busqueda} onChange={handleSearchChange} />
          </div>
        </form>
        <div>
          <button onClick={() => openModal()} className="bg-[#E8A477] hover:bg-[#BC7547] p-2 px-4 rounded-lg text-sm text-white font-semibold focus:ring-4 focus:ring-orange-200">
            Agregar servicio
          </button>
        </div>
      </div>
      <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
        <table className="w-full text-sm text-left rtl:text-right text-gray-500">
          <thead className="text-xs text-gray-700 uppercase bg-[#ffdbca]">
            <tr>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                ID
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                NOMBRE
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                DESCRIPCIÓN
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Edit</span>
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Eliminar</span>
              </th>
            </tr>
          </thead>
          <tbody>
            {filteredServicios.map((servicio) => (
              <tr
                key={servicio.id_servicio}
                className="bg-white border-b"
              >
                <th
                  scope="row"
                  className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  {servicio.id_servicio}
                </th>
                <td className="px-6 py-4">{servicio.nombre}</td>
                <td className="px-6 py-4">{servicio.descripcion}</td>
                <td className="px-6 py-4 text-right">
                  <button onClick={() => openModal(servicio)} className="font-semibold text-[#BC7547]">
                    Editar
                  </button>
                </td>
                <td className="px-6 py-4 text-right">
                  <button
                    onClick={() => eliminarServicio(servicio.id_servicio)}
                    className="font-semibold text-[#BC7547]"
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {isModalOpen && (
        isEdit ? (
          <EditarModal servicio={selectedServicio} closeModal={closeModal} funcion={getServicios} />
        ) : (
          <CrearModal closeModal={closeModal} funcion={getServicios} />
        )
      )}
    </div>
  );
}

export default Servicios;
