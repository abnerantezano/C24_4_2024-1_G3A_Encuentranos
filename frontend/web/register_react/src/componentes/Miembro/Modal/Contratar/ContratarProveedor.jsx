import React, { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
// COMPONENTES
import InformacionCliente from '../../Datos/InformacionCliente';
// PRIME REACT
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { Calendar } from 'primereact/calendar';
import { addLocale } from 'primereact/api';
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClock } from '@fortawesome/free-solid-svg-icons';
// SERVICIOS
import servicioProveedorServiceInstance from '../../../../servicios/Miembro/ServicioProveedor';
import ContratoServiceInstance from '../../../../servicios/Miembro/ContratosService';
import DetalleContratoServiceInstance from '../../../../servicios/Miembro/DetalleContrato';
import NotificacionServiceInstance from '../../../../servicios/Miembro/NotificacionService';

function ContratarProveedor({ idProveedor }) {
  // VARIABLE PARA ABRIR EL MODAL
  const [visible, setVisible] = useState(false);

  // ESTADO DE VARIABLES
  const [servicios, setServicios] = useState([]);
  const [selectedServicio, setSelectedServicio] = useState(null);

  // PARAMETROS DE REACT HOOK FORM
  const { register, handleSubmit, control, reset, setValue, formState: { errors } } = useForm();

  // FUNCION PARA ABRIR EL MODAL Y RESETEAR CUANDO SE CIERRE
  const Modal = () => {
    if (visible) {
      reset();
      setSelectedServicio(null);
    }
    setVisible(!visible);
  };

  //PONER EL LOCALE ESPAÑOL
  addLocale('es', {
    firstDayOfWeek: 1,
    dayNames: ['domingo', 'lunes', 'martes', 'miércoles', 'jueves', 'viernes', 'sábado'],
    dayNamesShort: ['dom', 'lun', 'mar', 'mié', 'jue', 'vie', 'sáb'],
    dayNamesMin: ['Dom', 'Lun', 'Mar', 'Mier', 'Jue', 'Vie', 'Sab'],
    monthNames: ['Enero ', 'Febrero ', 'Marzo ', 'Abril ', 'Mayo ', 'Junio ', 'Julio ', 'Agosto ', 'Septiembre ', 'Octubre ', 'Noviembre ', 'Diciembre '],
    monthNamesShort: ['ene', 'feb', 'mar', 'abr', 'may', 'jun', 'jul', 'ago', 'sep', 'oct', 'nov', 'dic'],
    today: 'Hoy',
    clear: 'Claro'
  });

  useEffect(() => {
    servicioProveedorServiceInstance.getServiciosDisponibles(idProveedor)
      .then((servicios) => {
        setServicios(servicios);
      });
  }, [idProveedor]);

  const convertirHoraUTC = (horaLocal) => {
    if (!(horaLocal instanceof Date)) {
      throw new Error("El parámetro debe ser un objeto Date");
    }
    
    const date = new Date(Date.UTC(1970, 0, 1, horaLocal.getHours(), horaLocal.getMinutes()));
    const time = date.toISOString().split('T')[1];
    return time.split('.')[0];
  };

  const contratar = (data, idCliente) => {
    const fechaActual = new Date().toISOString();
    console.log(data.hiServicio);
    console.log(data.hfServicio);
    const hiServicio = convertirHoraUTC(data.hiServicio);
    const hfServicio = convertirHoraUTC(data.hfServicio);
    
    const contrato = {
      idCliente: { idCliente: parseInt(idCliente) },
      fechaInicio: data.fechaInicio.toISOString().split('T')[0],
      fechaFin: data.fechaFin.toISOString().split('T')[0],
      hiServicio: hiServicio,
      hfServicio: hfServicio,
      fhCreacion: fechaActual,
      estado: "Pendiente",
      precioFinal: parseFloat(data.precio).toFixed(2),
    };
  
    console.log(contrato);
  
    ContratoServiceInstance.postContrato(contrato)
      .then((contrato) => {
        const idContrato = contrato.idContrato;
        const detalleContrato = {
          id: {
            idProveedor: parseInt(idProveedor),
            idServicio: parseInt(data.idServicio),
            idContrato: parseInt(idContrato),
          },
          precioActual: parseFloat(data.precio).toFixed(2),
        };
        const notificacion = {
          idContrato: { idContrato: parseInt(idContrato) },
          idProveedor: { idProveedor: parseInt(idProveedor) },
          idCliente: { idCliente: parseInt(idCliente) },
          titulo: "¡Nuevo contrato!",
          mensaje: "Estamos a espera de tu respuesta...",
          estado: "No visto",
          fhCreacion: fechaActual,
        };
        DetalleContratoServiceInstance.postDetalleContrato(detalleContrato);
        NotificacionServiceInstance.postNotificacion(notificacion);
        Modal();
      });
  };

  const handleServicioChange = (idServicio) => {
    const servicio = servicios.find(s => s.idServicio.idServicio === idServicio);
    setSelectedServicio(servicio);
    setValue('precio', servicio.precio); 
  };

  return (
    <InformacionCliente>
      {(cliente) => (
        <div>
          <button onClick={Modal} className='text-sm text-white bg-[#E8A477] py-2 px-4 rounded-lg font-semibold hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4]'>
            Contratar
          </button>
          {visible && (
            <div id="editarCuenta_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
              <div className="relative p-10 w-1/2">
                <div className="relative bg-white rounded-lg shadow">
                  <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                    <h3 className="text-xl font-semibold text-gray-900 ">
                      Contratar proveedor
                    </h3>
                    <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                      <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                      </svg>
                      <span className="sr-only">Cerrar</span>
                    </button>
                  </div>
                  <form onSubmit={handleSubmit((data) => contratar(data, cliente.idCliente))}>
                    <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                      <div className='w-3/4 mx-auto'>
                        <div className="grid md:grid-cols-2 md:gap-6 mb-5">
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Servicio</label>
                            <Controller name="idServicio" {...register("idServicio", { required: true })} control={control} render={({ field }) => (
                              <Dropdown
                                id={field.name}
                                value={field.value}
                                onChange={(e) => {
                                  field.onChange(e.value);
                                  handleServicioChange(e.value);
                                }}
                                options={servicios}
                                optionValue="idServicio.idServicio"
                                optionLabel="idServicio.nombre"
                                placeholder="Seleccione un servicio"
                                panelClassName="custom-panel"
                                pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0', select: 'text-red-500' }}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full"
                              />
                            )} />
                            {errors.idServicio && <span className="text-red-500 text-sm">Agregue un servicio</span>}
                          </div>
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Precio</label>
                            <Controller name="precio" {...register("precio", { required: true })} control={control} render={({ field }) => (
                              <InputNumber
                                inputId={field.name}
                                value={field.value}
                                onChange={(e) => field.onChange(e.value)}
                                mode="currency"
                                currency="PEN"
                                className="block w-full"
                                inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5"
                                disabled={!selectedServicio?.negociable}
                              />
                            )} />
                            {errors.precio && <span className="text-red-500 text-sm">Agregue un precio</span>}
                          </div>
                        </div>
                        <div className="grid md:grid-cols-2 md:gap-6 mb-5">
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Fecha de inicio</label>
                            <Controller name="fechaInicio" {...register("fechaInicio", { required: true })} control={control} render={({ field }) => (
                              <Calendar id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} showIcon dateFormat="yy-mm-dd" locale="es" pt={{root: "rounded-lg bg-[#F3C7AC] text-white border focus:ring focus:ring-orange-200 "}} inputClassName="bg-gray-50 text-gray-900 text-sm rounded-l-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5"/>
                            )} />
                            {errors.fechaInicio && <span className="text-red-500 text-sm">Ingrese la fecha de inicio del servicio</span>}
                          </div>
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Hora de inicio</label>
                            <Controller name="hiServicio" {...register("hiServicio", { required: true })} control={control} render={({ field }) => (
                              <Calendar id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} showIcon timeOnly hourFormat='12' icon={() => <FontAwesomeIcon icon={faClock} />} pt={{root: "rounded-lg bg-[#F3C7AC] text-white border focus:ring focus:ring-orange-200 "}} inputClassName="bg-gray-50 text-gray-900 text-sm rounded-l-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5"/>
                            )} />
                            {errors.hiServicio && <span className="text-red-500 text-sm">Ingrese la hora de inicio del servicio</span>}
                          </div>
                        </div>
                        <div className="grid md:grid-cols-2 md:gap-6 mb-5">
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Fecha de fin</label>
                            <Controller name="fechaFin" {...register("fechaFin", { required: true })} control={control} render={({ field }) => (
                              <Calendar id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} showIcon dateFormat="yy-mm-dd" locale="es" pt={{root: "rounded-lg bg-[#F3C7AC] text-white border focus:ring focus:ring-orange-200 "}} inputClassName="bg-gray-50 text-gray-900 text-sm rounded-l-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5" />
                            )} />
                            {errors.fechaFin && <span className="text-red-500 text-sm">Ingrese la fecha de fin del servicio</span>}
                          </div>
                          <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 mt-2">Hora de fin</label>
                            <Controller name="hfServicio" {...register("hfServicio", { required: true })} control={control} render={({ field }) => (
                              <Calendar id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} showIcon timeOnly hourFormat='12' icon={() => <FontAwesomeIcon icon={faClock} />} pt={{root: "rounded-lg bg-[#F3C7AC] text-white border focus:ring focus:ring-orange-200 "}} inputClassName="bg-gray-50 text-gray-900 text-sm rounded-l-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5"/>
                            )} />
                            {errors.hfServicio && <span className="text-red-500 text-sm">Ingrese la hora de fin del servicio</span>}
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                      <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                        Contratar
                      </button>
                      <button onClick={Modal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10">
                        Cerrar
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </InformacionCliente>
  );
}

export default ContratarProveedor;
