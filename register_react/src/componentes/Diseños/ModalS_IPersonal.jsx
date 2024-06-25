import React, { useEffect, useState } from 'react';
import { useForm, Controller } from 'react-hook-form';
//PRIME REACT
import { Calendar } from 'primereact/calendar';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { InputMask } from 'primereact/inputmask';
import { addLocale } from 'primereact/api';
import DistritoServiceInstance from '../../servicios/Miembro/DistritoService';

const ModalS_IPersonal = ({ usuario, Modal }) => {

    const { register, handleSubmit, control, formState: { errors } } = useForm();

    //VARIABLE DONDE SE ALMACENAN LOS DISTRITOS
    const [distritos, setDistritos] = useState([]);

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

    //TRAER LA LISTA DE DISTRITOS
    useEffect(() => {
        DistritoServiceInstance.getAll()
            .then(DistritoResponse => {
                setDistritos(DistritoResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const actualizar = (data, usuario) => {
       
    };

    return (
        <div>
            <div id="editarIPersonal_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
                <div className="relative p-10 w-1/2">
                    <div className="relative bg-white rounded-lg shadow">
                        <div className="flex items-center justify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                            <h3 className="text-xl font-semibold text-gray-900 ">
                                Editar información personal
                            </h3>
                            <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-[#787171] rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                                <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                                </svg>
                                <span className="sr-only">Cerrar</span>
                            </button>
                        </div>
                        <form onSubmit={handleSubmit((data) => actualizar(data, usuario))}>
                            <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                                <div className='w-3/4 mx-auto'>
                                    <div className="my-5">
                                        <label className="block mb-2 text-sm font-medium text-gray-700">Nombre</label>
                                        <InputText type="text" id="nombre" {...register("nombre", { required: true })} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                        {errors.nombre && <span className="text-red-500 text-sm">Ingresar su nombre</span>}
                                    </div>
                                    <div className="grid md:grid-cols-2 md:gap-6">
                                        <div className="relative z-0 w-full mb-5 group">
                                            <label className="block mb-2 text-sm font-medium text-gray-700">Apellido paterno</label>
                                            <InputText type="text" id="apellidoPaterno" {...register("apellidoPaterno", { required: true })} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                            {errors.apellidoPaterno && <span className="text-red-500 text-sm">Ingresar su apellido paterno</span>}
                                        </div>
                                        <div className="relative z-0 w-full mb-5 group mb-5">
                                            <label className="block mb-2 text-sm font-medium text-gray-700">Apellido materno</label>
                                            <InputText type="text" {...register("apellidoMaterno", { required: true })} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                            {errors.apellidoMaterno && <span className="text-red-500 text-sm">Ingresar su apellido materno</span>}
                                        </div>
                                    </div>
                                    <div className="grid md:grid-cols-2 md:gap-6">
                                        <div className=" w-full group mb-5">
                                            <label className="block mb-2 text-sm font-medium text-gray-700">Fecha de nacimiento</label>
                                            <Controller name="fechaNacimiento" {...register("fechaNacimiento", { required: true })} control={control} render={({ field }) => (
                                                <Calendar id={field.name} value={field.value} onChange={field.onChange} dateFormat="yy-mm-dd" locale="es" inputClassName="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                            )} />
                                            {errors.fechaNacimiento && <span className="text-red-500 text-sm">Ingresar la fecha</span>}
                                        </div>
                                        <div className="mb-5">
                                            <label className="block mb-2 text-sm font-medium text-gray-700">Celular</label>
                                            <InputMask id="celular" mask="999999999" {...register("celular", { required: true })} panelClassName="text-sm focus:ring focus:ring-orange-200" className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5" />
                                            {errors.celular && <span className="text-red-500 text-sm">Ingresar su celular</span>}
                                        </div>
                                    </div>
                                    <div className="mb-5">
                                        <label className="block mb-2 text-sm font-medium text-gray-700">Distrito</label>
                                        <Controller name="idDistrito" {...register("idDistrito", { required: true })} control={control} render={({ field }) => (
                                            <Dropdown id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} options={distritos} optionValue="idDistrito" optionLabel="nombre" placeholder="Seleccione un distrito" panelClassName="custom-panel text-sm focus:ring focus:ring-orange-200" pt={{ input: 'text-sm', panel: 'text-sm', root: 'ring-0' }} className="bg-gray-50 border border-gray-300 text-[#787171] text-sm rounded-lg focus:border-dark w-full" />
                                        )} />
                                        {errors.idDistrito && <span className="text-red-500 text-sm">Ingresar su distrito</span>}
                                    </div>

                                </div>
                            </div>
                            <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                                <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                                    Actualizar
                                </button>
                                <button onClick={Modal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10">
                                    Cerrar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalS_IPersonal;
