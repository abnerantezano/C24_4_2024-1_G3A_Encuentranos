import React, { useEffect, useState } from "react";
import logo from "../imagenes/logo-color.png";
//PRIME REACT
import { Calendar } from 'primereact/calendar';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { InputMask } from 'primereact/inputmask';
import { addLocale } from 'primereact/api';

//REACT HOOKFORM
import { useForm,Controller} from "react-hook-form";
import { useNavigate } from "react-router-dom";
//AXIOS
import DistritoService from "../servicios/DistritoService";
import UsuarioService from "../servicios/UsuarioService";
import ClienteService from "../servicios/ClienteService";
import ProveedorService from "../servicios/ProveedorService";
//COMPONENTE


const Formulario = () => {

    const { register, handleSubmit,control, formState: { errors } } = useForm();

    //DECLARAR UNA VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

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

    //VARIABLE DONDE SE ALMACENAN LOS DISTRITOS
    const [distritos,setDistritos] = useState([])

    //VARIABLE DONDE SE ALMACENA EL USUARIO Y SUS DATOS
    const [usuario,setUsuario]=useState([])

    //TRAER LOS DATOS DEL USUARIO QUE INICIO SESION
    useEffect(() => {
        UsuarioService.getUser()
            .then(UsuarioResponse => {
                setUsuario(UsuarioResponse);
            })
            .catch(error => {
                console.log(error);
            })
    })

    //TRAER LA LISTA DE DISTRITOS
    useEffect(() => {
        DistritoService.getAll()
            .then(DistritoResponse => {
                setDistritos(DistritoResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []); 

    //BOTON PARA ENVIAR EL FORMULARIO A SPRINGBOOT
    const EnviarDatos = (data) => {

        const fechaNac = new Date(data.fechaNacimiento).toISOString().split('T')[0];

        const datos = {
            idUsuario: {id:parseInt(usuario.id)},
            nombre: data.nombre,
            apellidoPaterno: data.apellidoPaterno,
            apellidoMaterno: data.apellidoMaterno,
            sexo: data.sexo,
            dni: data.dni,
            fechaNacimiento: fechaNac,
            celular: data.celular,
            idDistrito: {id:parseInt(data.idDistrito)},
        };

        //AGREGA SEGUN EL TIPO DE USUARIO 
        if (usuario && usuario.idTipo) {
            const idTipo = usuario.idTipo.id;
            if(idTipo===1){
                //AGREGAR A CLIENTE
                ClienteService.addCliente(datos)
                    .then((ClienteResponse) => {
                        console.log(ClienteResponse);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                navigate('/inicio');
            }else if (idTipo===2){
                //AGREGAR A PROVEEDOR
                ProveedorService.addProveedor(datos)
                    .then((ProveedorResponse) => {
                        console.log(ProveedorResponse);
                    })
                    .catch((error) => {
                        console.log(error);
                    })
                navigate('/agregarServicio');
            }
            } else {
            console.error('Error al tener el idtipo');
        }
    };

    return (
        
    <form onSubmit={handleSubmit(EnviarDatos)}>
        <div className="bg-[#F0EEEC] w-full">
            <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                    <div className="flex mb-8 justify-center">
                        <img src={logo} className="w-24" alt="Logo" />
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Nombre</label>
                        <InputText type="text" id="nombre" {...register("nombre", { required: true })} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        {errors.nombre && <span className="text-red-500 text-sm">Ingresar su nombre</span>}
                    </div>
                    <div className="grid md:grid-cols-2 md:gap-6">
                        <div className="relative z-0 w-full mb-5 group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido paterno</label>
                            <InputText type="text" id="apellidoPaterno" {...register("apellidoPaterno", { required: true })} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                            {errors.apellidoPaterno && <span className="text-red-500 text-sm">Ingresar su apellido paterno</span>}
                        </div>
                        <div className="relative z-0 w-full mb-5 group mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido materno</label>
                            <InputText type="text" {...register("apellidoMaterno", { required: true })} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                            {errors.apellidoMaterno && <span className="text-red-500 text-sm">Ingresar su apellido materno</span>}
                        </div>
                    </div>
                    <div className="grid md:grid-cols-3 md:gap-6">
                        <div className="relative z-0 w-full group mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">DNI</label>
                            <InputText type="text" id="dni" {...register("dni", { required: true })} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            {errors.dni && <span className="text-red-500 text-sm">Ingresar su DNI</span>}
                        </div>
                        <div className=" w-full group mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Fecha de nacimiento</label>
                            <Controller name="fechaNacimiento" {...register("fechaNacimiento", { required: true })} control={control} render={({ field }) => (
                                <Calendar id={field.name} value={field.value} onChange={field.onChange} dateFormat="yy-mm-dd" locale="es" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            )} />
                            {errors.fechaNacimiento && <span className="text-red-500 text-sm">Ingresar la fecha</span>}
                        </div>
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Celular</label>
                            <InputMask id="celular" mask="999999999" {...register("celular", { required: true })} panelClassName="text-sm focus:ring focus:ring-orange-200" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            {errors.celular && <span className="text-red-500 text-sm">Ingresar su celular</span>}
                        </div>
                    </div>
                    <div className="relative z-0 w-full group mb-5 text-sm">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Distrito</label>
                        <Controller name="idDistrito" {...register("idDistrito", { required: true })} control={control} render={({ field }) => (
                            <Dropdown id={field.name} value={field.value} onChange={(e) => field.onChange(e.value)} options={distritos} optionValue="id" optionLabel="nombre" placeholder="Seleccione un distrito" panelClassName="custom-panel" pt={{input:'text-sm',panel:'text-sm',root:'ring-0'}} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:border-dark w-full dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                        )} />
                        {errors.idDistrito && <span className="text-red-500 text-sm">Ingresar su distrito</span>}
                    </div>
                    <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">Sexo</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="sexo-f" name="sexo" type="radio" value="F" {...register("sexo", { required: true })} className="w-4 h-4 border-gray-300 focus:ring focus:ring-orange-200 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-[#] dark:border-gray-600" />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Femenino
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="sexo-m" name="sexo" type="radio" value="M" {...register("sexo", { required: true })} className="w-4 h-4 border-gray-300 focus:ring focus:ring-orange-200 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-[#] dark:border-gray-600" />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Masculino
                                </label>
                            </div>
                        </div>
                        {errors.sexo && <span className="text-red-500 text-sm">Elija una opción</span>}
                    </div>
                    <div className="mb-5 flex justify-center my-16">
                        <button type="submit" className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    )
}

export default Formulario;