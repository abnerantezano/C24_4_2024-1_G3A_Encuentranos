import React, { useState, useEffect } from "react";
// PRIME REACT
import { InputText } from 'primereact/inputtext';
// FONT AWESOME
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
// AXIOS
import ContratosService from "../servicios/Contratos";
import EditarModal from "../componentes/Modal/Contratos/Editar";

function Contratos() {

  const [contratos, setContratos] = useState([]);
  const [filteredContratos, setFilteredContratos] = useState([]);
  
  const [selectedContrato, setSelectedContrato] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [busqueda, setBusqueda] = useState("");

  // LLAMAR LA LISTA DE CONTRATOS
  useEffect(() => {
    getContratos();
  }, []);

  const getContratos = () => {
    ContratosService.getLista()
      .then((ContratoResponse) => {
        setContratos(ContratoResponse);
        setFilteredContratos(ContratoResponse); 
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const eliminarContrato = (idContrato) => {
    ContratosService.deleteContrato(idContrato)
      .then((response) => {
        console.log(response);
        setContratos(contratos.filter(contrato => contrato.id_contrato !== idContrato));
        setFilteredContratos(filteredContratos.filter(contrato => contrato.id_contrato !== idContrato));
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const openModal = (contrato = null) => {
    setSelectedContrato(contrato);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedContrato(null);
    setIsModalOpen(false);
  };

  const handleSearchChange = (e) => {
    setBusqueda(e.target.value);
    if (e.target.value === "") {
      setFilteredContratos(contratos); 
    } else {
      setFilteredContratos(
        contratos.filter((contrato) =>
          contrato.estado.toLowerCase().includes(e.target.value.toLowerCase())
        )
      );
    }
  };

  function formatTime(time24) {
    const [hours, minutes] = time24.split(':');
    let period = 'AM';
    let hours12 = parseInt(hours, 10);
    
    if (hours12 >= 12) {
      period = 'PM';
      if (hours12 > 12) {
        hours12 -= 12;
      }
    }
    
    return `${hours12}:${minutes} ${period}`;
  }

  return (
    <div className='mx-10'>
      <h1 className="mb-10 text-3xl font-bold text-[#8f5132]">Contratos</h1>
      <div className="flex flex-wrap items-center justify-between mb-10">
        <form className="w-1/2">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <FontAwesomeIcon icon={faMagnifyingGlass} className="w-4 h-4 text-[#787171]" />
            </div>
            <InputText type="text" className="block w-full p-2 pl-10 text-sm text-[#787171] border rounded-lg bg-[#fcfcfc] focus:ring-2 focus:ring-orange-200" placeholder="Estado del contrato" value={busqueda} onChange={handleSearchChange} />
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
                ESTADO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                PRECIO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                DURACIÓN
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                HORARIO
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Editar</span>
              </th>
              <th scope="col" className="px-6 py-3 font-bold text-[#BC7547]">
                <span className="sr-only">Eliminar</span>
              </th>
            </tr>
          </thead>
          <tbody>
            {filteredContratos.map((contrato) => (
              <tr
                key={contrato.id_contrato}
                className="bg-white border-b"
              >
                <th
                  scope="row"
                  className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  {contrato.id_contrato}
                </th>
                <td className="px-6 py-4">{contrato.estado}</td>
                <td className="px-6 py-4">S/ {parseFloat(contrato.precio_final).toFixed(2)}</td>
                <td className="px-6 py-4">{contrato.fecha_inicio} — {contrato.fecha_fin}</td>
                <td className="px-6 py-4">{contrato.hi_servicio && formatTime(contrato.hi_servicio)} — {contrato.hf_servicio && formatTime(contrato.hf_servicio)}</td>
                <td className="px-6 py-4 text-right">
                  <button onClick={() => openModal(contrato)} className="font-semibold text-[#BC7547]">
                    Editar
                  </button>
                </td>
                <td className="px-6 py-4 text-right">
                  <button
                    onClick={() => eliminarContrato(contrato.id_contrato)}
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
        <EditarModal contrato={selectedContrato} closeModal={closeModal} funcion={getContratos} />
      )}
    </div>
  );
}

export default Contratos;
