import React, { useEffect, useState } from 'react';
//REACT ROUTER DOM
import { Link } from 'react-router-dom';
// PRIME REACT
import { InputText } from 'primereact/inputtext';
import { Slider } from 'primereact/slider';
import { Rating } from 'primereact/rating';
import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'
// AXIOS
import ServicioProveedorService from '../servicios/ServicioProveedor';
import ProveedorService from '../servicios/ProveedorService';
//REACT HOOK FORM
import { useForm} from "react-hook-form";

function Precios() {

  const { handleSubmit } = useForm();

  // VARIABLES
  const [listaServicioProveedor, setListaServicioProveedor] = useState([]);
  const [listaFiltrada, setListaFiltrada] = useState([]);
  const [proveedor, setProveedor] = useState([]);

  // RANGO DE PRECIOS
  const [mayor, setMayor] = useState(0);
  const [menor, setMenor] = useState(0);

  // VALORES INICIALES PARA EL FILTRO DEL SLIDER
  const [precioFiltro, setPrecioFiltro] = useState([0, 0]);

  // VARIABLE DE BÚSQUEDA
  const [busqueda, setBusqueda] = useState("");

  // TRAER EL VALOR DEL MAYOR PRECIO
  useEffect(() => {
    ServicioProveedorService.getPrecioMayor()
      .then(PrecioMayorResponse => {
        setMayor(PrecioMayorResponse.data.precio);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  // TRAER EL VALOR DEL MENOR PRECIO
  useEffect(() => {
    ServicioProveedorService.getPrecioMenor()
      .then(PrecioMenorResponse => {
        setMenor(PrecioMenorResponse.data.precio);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  // TRAER LA LISTA DE SERVICIOS DE PROVEEDORES
  useEffect(() => {
    ServicioProveedorService.getAll()
      .then(ServicioProveedorResponse => {
        setListaServicioProveedor(ServicioProveedorResponse);
        setListaFiltrada(ServicioProveedorResponse); // Inicializar la lista filtrada
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  // TRAER LA LISTA DE PROVEEDORES
  useEffect(() => {
    ProveedorService.getListaProveedor()
      .then(ProveedorResponse => {
        setProveedor(ProveedorResponse);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  // DAR EL VALOR DE MENOR Y MAYOR PRECIO AL FILTRO PRECIO
  useEffect(() => {
    setPrecioFiltro([menor, mayor]);
  }, [menor, mayor]);

  // FILTRO DE BÚSQUEDA POR NOMBRE Y RANGO DE PRECIO POR EL BOTON
  const busquedaServicios = () => {
    const serviciosFiltrados = listaServicioProveedor.filter((servicioProveedor) => {
      const nombreLowerCase = servicioProveedor.idServicio.nombre.toLowerCase();
      return (
        nombreLowerCase.includes(busqueda.toLowerCase()) &&
        servicioProveedor.precio >= precioFiltro[0] &&
        servicioProveedor.precio <= precioFiltro[1]
      );
    });
    setListaFiltrada(serviciosFiltrados);
  };

  //SI HAY CAMBIOS SE GENERA LA LISTA PRINCIPAL 
  useEffect(() => {
    setListaFiltrada(listaServicioProveedor);
  }, [busqueda, precioFiltro]);

  //FILTRAR SIN EL BOTON POR EL PRECIO
  const filterServicios = (servicios) => {
    return servicios.filter((servicioProveedor) => {
      return (
        servicioProveedor.precio >= precioFiltro[0] &&
        servicioProveedor.precio <= precioFiltro[1]
      );
    });
  };

  //VALOR PARA EL DATAVIEW CON EL FILTRO DEL PRECIO SIN BOTON
  const filteredData = filterServicios(listaFiltrada);

  //TEMPLATE PARA EL DATAVIEW
  const listTemplate = (Sproveedor, index) => (
    <div key={index} className='flex items-center bg-white shadow-lg py-1 rounded-lg mb-6'>
      <div className='xl:w-2/12'>
        <img className='w-auto h-24 rounded-full mx-8' src='https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8dXNlcnxlbnwwfHwwfHx8MA%3D%3D' alt="profile" />
      </div>
      <div className='p-4 xl:w-8/12'>
        <h4 className='text-[#B4663F] font-bold text-base mb-1'>{Sproveedor.idProveedor.nombre} {Sproveedor.idProveedor.apellidoPaterno} {Sproveedor.idProveedor.apellidoMaterno} </h4>
        <p className='text-sm mb-1'><Rating value={Sproveedor.calificacionPromedio} readOnly cancel={false} pt={{ root: 'focus:ring-0', onIcon: 'text-[#EBC351] focus:ring-0', offIcon: 'text-[#B7B7B7] focus:ring-0' }} /></p>
        <p className='text-base text-black mb-1 font-medium'>{Sproveedor.idServicio.nombre}</p>
        {Sproveedor.negociable ? (
          <p className='text-[#787171] text-sm mb-1 font-normal'>Precio negociable</p>
        ) : (
          <p className='text-[#787171] text-sm mb-1 font-normal'>Precio no negociable</p>
        )}
        <p className='text-base text-gray-500 font-medium mb-1'> S/ {parseFloat(Sproveedor.precio).toFixed(2)} </p>
        </div>
        <div className='xl:w-2/12'>
          <Link to="" className='bg-[#E8A477]  font-bold text-white text-center text-sm p-3 rounded-lg'>Ver perfil</Link>
        </div>
    </div>
  );

  return (
    <div>
      <div className='bg-gradient-to-r from-[#F3C7AC] to-[#E8A477] w-full p-6 text-center text-white'>
        <h1 className='font-bold'>Realiza la búsqueda de tu servicio</h1>
      </div>
      <div className='flex items-center justify-center p-6'>
        <form onSubmit={handleSubmit(busquedaServicios)}>
          <div className="my-4 mx-auto">
            <div className="p-inputgroup shadow-lg">
              <span className="p-inputgroup-addon bg-white pe-6">
                <FontAwesomeIcon icon={faMagnifyingGlass} className='px-4' /> Nombre del servicio
              </span>
              <img width="24" height="24" className="bg-white" src="https://img.icons8.com/material-rounded/24/CDCACA/vertical-line.png" alt="vertical-line" />
              <InputText className='px-4 xl:w-96 focus:ring focus:ring-orange-200' onChange={e => setBusqueda(e.target.value)} />
              <button type="submit" className="p-inputgroup-addon bg-[#E8A477] text-white font-bold p-2 px-4 text-sm">Buscar</button>
            </div>
          </div>
        </form>
      </div>
      <div className='flex justify-center p-6 xl:px-36 mx-auto'>
        <div className='w-2/12 py-12'>
          <h5 className='text-gray-500 font-bold my-2'>FILTRO DE PRECIO</h5>
          <div className='px-2'>
            <p className='mb-1'>S/ {precioFiltro[0]} - S/ {precioFiltro[1]}</p>
            <Slider className="w-full" value={precioFiltro} pt={{ root: "bg-gray-300", range: 'bg-[#BC7547]', handle: 'bg-[#BC7547]' }} min={menor} max={mayor} onChange={(e) => setPrecioFiltro(e.value)} range />
          </div>
        </div>
        <div className='px-4 py-12 w-10/12'>
          {/*SINDATAVIEW*/}
          {/*
            filterServicios(listaFiltrada).map((Sproveedor, index) => (
              <div key={index} className='flex items-center bg-white shadow-lg py-1 rounded-lg mb-6'>
                <div className='xl:w-2/12'>
                  <img className='w-auto h-24 rounded-full mx-8' src='https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8dXNlcnxlbnwwfHwwfHx8MA%3D%3D' alt="profile" />
                </div>
                <div className='p-4 xl:w-8/12'>
                  <h4 className='text-[#B4663F] font-bold text-lg mb-1'>{Sproveedor.idProveedor.nombre} {Sproveedor.idProveedor.apellidoPaterno} {Sproveedor.idProveedor.apellidoMaterno} </h4>
                  <p className='text-sm mb-1'><Rating value={Sproveedor.calificacionPromedio} readOnly cancel={false} pt={{ root: 'focus:ring-0', onIcon: 'text-[#EBC351] focus:ring-0', offIcon: 'text-[#B7B7B7] focus:ring-0' }} /></p>
                  <p className='text-base mb-1 font-medium'>{Sproveedor.idServicio.nombre}</p>
                  {Sproveedor.negociable ? (
                    <p className='text-[#787171] text-sm mb-1 font-normal'>Precio negociable</p>
                  ) : (
                    <p className='text-[#787171] text-sm mb-1 font-normal'>Precio no negociable</p>
                  )}
                  <p className='text-lg text-gray-500 font-medium mb-1'> S/ {parseFloat(Sproveedor.precio).toFixed(2)} </p>
                </div>
                <div className='xl:w-2/12'>
                  <a href='#' className='bg-[#E8A477]  font-bold text-white text-center text-sm p-3 rounded-lg'>Ver perfil</a>
                </div>
              </div>
            ))
          */}
          {/*CON DATAVIEW*/}
          <DataView value={filteredData} className='bg-transparent' filter={filterServicios} pt={{paginator:'bg-red-500', content:'bg-transparent text-sm'}} itemTemplate={listTemplate} paginator paginatorClassName='bg-transparent text-gray-500' rows={6} />
        </div>
      </div>
    </div>
  );
}

export default Precios;


