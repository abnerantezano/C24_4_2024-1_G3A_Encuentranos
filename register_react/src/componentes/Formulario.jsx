import React, { useEffect, useState } from "react";
import logo from "../imagenes/logo-color.png";
//PRIME REACT
import { Calendar } from 'primereact/calendar';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';

//REACT HOOKFORM
import { useForm,Controller} from "react-hook-form";
import { useNavigate } from "react-router-dom";
//AXIOS
import DistritoService from "../servicios/DistritoService";
import RegistroService from "../servicios/RegistroService";
//COMPONENTE
import Token from "./Token";

const Formulario = () => {

    const { register, handleSubmit,control, formState: { errors } } = useForm();

    //DECLARAR UNA VARIABLE PARA LA NAVEGACIÓN
    const navigate = useNavigate();

    //VARIABLE DONDE SE ALMACENAN LOS DISTRITOS
    const [distritos,setDistritos] = useState([])

    //VARIABLE DONDE SE ALMACENA EL USUARIO Y SUS DATOS
    const [usuario,setUsuario]=useState([])
    const [distrito,setDistrito]=useState('')

    //TRAER LA LISTA DE DISTRITOS
    useEffect(() => {
        DistritoService.getAll()
            .then(DistritoResponse => {
                setDistritos(DistritoResponse);
            })
            .catch(error => {
                console.error(error);
            });
    }, []); 

    const handleDistritoChange = (e) => {
        setDistrito(e.target.value);
    };

    //BOTON PARA ENVIAR EL FORMULARIO A SPRINGBOOT
    const onSubmit = (data) => {

        const datos = {
            idUsuario: parseInt(data.idUsuario),
            nombre: data.nombre,
            apellidoPaterno: data.apellidoPaterno,
            apellidoMaterno: data.apellidoMaterno,
            sexo: data.sexo,
            dni: data.dni,
            fechaNacimiento: data.fechaNacimiento,
            celular: data.celular,
            idDistrito: parseInt(data.idDistrito),
        };

        console.log(datos);
        RegistroService.postRegistrar(datos).then((response) => {
            console.log(response.data);
            if(idTipo===1){
                navigate('/inicio');
            }else if (idTipo===2){
                navigate('/agregarServicio');
            }
        }).catch(error => {
            console.error(error);
        })
    };

    return (
        
    <form onSubmit={handleSubmit(onSubmit)}>
        <div className="bg-[#F0EEEC] w-full">
            <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                    <div className="flex mb-8 justify-center">
                        <img src={logo} className="w-24" alt="Logo" />
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">IDUSUARIO</label>
                        <InputText type="text" id="idUsuario" value="1" {...register("idUsuario", { required: true })} readOnly className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
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
                            <Calendar id="fechaNacimiento" {...register("fechaNacimiento", { required: true })} dateFormat="yy-dd-mm" locale="en" inputClassName="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
                            {errors.fechaNacimiento && <span className="text-red-500 text-sm">Ingresar la fecha</span>}
                        </div>
                        <div className="mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Celular</label>
                            <InputText type="text" id="celular" {...register("celular", { required: true })} panelClassName="text-sm focus:ring focus:ring-orange-200" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-orange-200 focus:border-dark block w-full p-2.5 dark:bg-[#] dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
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
                                <input id="sexo-f" name="sexo" type="radio" value="Femenino" {...register("sexo", { required: true })} className="w-4 h-4 border-gray-300 focus:ring focus:ring-orange-200 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-[#] dark:border-gray-600" />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Femenino
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="sexo-m" name="sexo" type="radio" value="Masculino" {...register("sexo", { required: true })} className="w-4 h-4 border-gray-300 focus:ring focus:ring-orange-200 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-[#] dark:border-gray-600" />
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