import React, { useEffect, useState } from 'react';
import { DataView } from 'primereact/dataview';
import ServicioService from '../servicios/ServicioService';

function Servicios() {
  const [servicio, setServicio] = useState([]);

  useEffect(() => {
    ServicioService.getAll()
      .then((ServicioResponse) => {
        setServicio(ServicioResponse);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const listTemplate = (servicio, index) => (
    <div key={index} className="w-full bg-white border border-gray-200 rounded-lg shadow m-4">
      <img className="rounded-t-lg" src="https://www.questionpro.com/blog/wp-content/uploads/2022/10/Portada-calidad-del-servicio.jpg" alt="Imagen del servicio" />
      <div className="p-5">
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">{servicio.nombre}</h5>
        <p className="mb-3 font-normal text-gray-700">{servicio.descripcion}</p>
      </div>
    </div>
  );

  return (
    <div>
      <div className="bg-gradient-to-r from-[#F3C7AC] to-[#E8A477] w-full p-6 text-center text-white">
        <h1 className="font-bold">Servicios Disponibles</h1>
      </div>
      <div className="p-6 xl:px-36 mx-auto">
        <DataView value={servicio} className="bg-transparent" pt={{ paginator: 'bg-red-500', content: 'bg-transparent text-sm', grid: 'grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 bg-transparent' }} itemTemplate={listTemplate} paginator paginatorClassName="bg-transparent text-gray-500" rows={6} />
      </div>
    </div>
  );
}

export default Servicios;
