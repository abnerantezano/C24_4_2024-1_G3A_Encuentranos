import React, { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import logo from "../images/logo-color.png";
import { useNavigate } from "react-router-dom";
import DepartamentosService from "../services/DepartamentosService";
import RegistroService from "../services/RegistroService";

const Formulario = () => {

    //Variables para manejar los valores del formulario

    const [departamentos, setDepartamentos] = useState([]);
    const [provincias, setProvincias] = useState([]);
    const [distritos, setDistritos] = useState([]);

    const [idUsuario,setIdUsuario] = useState("");
    const [correo, setCorreo] = useState("");
    const [contrasena, setContraseña] = useState("");
    const [idTipo, setIdTipo] = useState("");
    const [nombre, setNombre] = useState("");
    const [apellidoPaterno, setApellidoPaterno] = useState("");
    const [apellidoMaterno, setApellidoMaterno] = useState("");
    const [sexo,setSexo] = useState("");
    const [dni,setDni] = useState("");
    const [fechaNacimiento,setFechaN] = useState("");
    const [celular,setCel] = useState("");
    const [imagenUrl,setImagenUrl] = useState("");

    const [departamento, setByDepartamento] = useState("");
    const [provincia, setByProvincia] = useState("");
    const [distrito, setDistrito] = useState("");

    const navigate = useNavigate();

    //TRAER LA LISTA DE DEPARTAMENTOS
    useEffect(() => {
        DepartamentosService.getDepartamentos()
            .then(DepartamentoResponse => {
                setDepartamentos(DepartamentoResponse);
            })
            .catch(error => {

            });
    }, []); 

    //Tener el cambio de departamento y traer las provincias de acuerdo al departamento seleccionado
    const handleDepartamentoChange = (e) => {
        const selectedDepartamento = e.target.value;
        setByDepartamento(selectedDepartamento);
        setByProvincia(""); 
        DepartamentosService.getProvincias(selectedDepartamento)
            .then(data => setProvincias(data))
            .catch(error => console.error(`Error al obtener provincias para ${selectedDepartamento}:`, error));
    };

    //Tener el cambio de provincia y traer los distritos de acuerdo a la provincia seleccionada
    const handleProvinciaChange = (e) => {
        const selectedProvincia = e.target.value;
        setByProvincia(selectedProvincia);
        DepartamentosService.getDistritos(departamento, selectedProvincia)
            .then(data => setDistritos(data))
            .catch(error => console.error(`Error al obtener distritos para ${departamento} - ${selectedProvincia}:`, error));
    };

    //Función del boton para pasar al usuario segun su tipo

    const agregarSegunTipoUsuario = (e) => {
        e.preventDefault();
        const datos = {
            correo,contrasena,idUsuario,idTipo, nombre, apellidoPaterno, apellidoMaterno, sexo, dni, fechaNacimiento, 
            celular, imagenUrl, departamento, provincia, distrito
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
    }

    return (
        
    <form>
        <div className="bg-[#FFF0E7] w-full">
            <div className="flex items-center justify-center py-4 lg:pt-6 lg:pb-12">
                <div className="md:mb-0 md:w-8/12 lg:w-5/12 bg-white m-6 py-12 px-16 rounded-lg shadow-xl">
                    <div className="flex mb-8 justify-center">
                        <img src={logo} className="w-24" alt="Logo" />
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">imagenurl</label>
                        <input type="text" value={imagenUrl} onChange={ (e) => setImagenUrl(e.target.value)}  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">IDUSUARIO</label>
                        <input type="text" value={idUsuario} onChange={ (e) => setIdUsuario(parseInt(e.target.value))}  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Correo</label>
                        <input type="text" value={correo} onChange={ (e) => setCorreo(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Contraseña</label>
                        <input type="password" value={contrasena} onChange={ (e) => setContraseña(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Nombre</label>
                        <input type="text" value={nombre} onChange={ (e) => setNombre(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="grid md:grid-cols-2 md:gap-6">
                        <div className="relative z-0 w-full mb-5 group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido paterno</label>
                            <input type="text" value={apellidoPaterno} onChange={ (e) => setApellidoPaterno(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div className="relative z-0 w-full mb-5 group mb-5">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Apellido materno</label>
                            <input type="text" value={apellidoMaterno} onChange={ (e) => setApellidoMaterno(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                    </div>
                    <div className="relative z-0 w-full mb-5 group mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">DNI</label>
                        <input type="text" value={dni} onChange={ (e) => setDni(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-600 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                    </div>
                    <div date-rangepicker className="relative z-0 w-full mb-5 group mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Fecha de nacimiento</label>
                        <div className="">
                            <DatePicker
                                selected={fechaNacimiento}
                                onChange={date => setFechaN(date)}
                                dateFormat="yyyy-MM-dd"
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            />
                        </div>
                    </div>
                    <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">Sexo</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="sexo-f" name="sexo" type="radio" value="Femenino" onChange={(e) => setSexo(e.target.value)} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Femenino
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="sexo-m" name="sexo" type="radio" value="Masculino" onChange={(e) => setSexo(e.target.value)} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Masculino
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="mb-5">
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Celular</label>
                        <input type="text" value={celular} onChange={ (e) => setCel(e.target.value)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required/>
                    </div>
                    <div className="mb-5 grid md:grid-cols-3 md:gap-6">
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Departamento</label>
                            <select id="custom-select" onChange={handleDepartamentoChange} value={departamento} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                
                                <option> </option>
                                {departamentos.map(departamento => (
                                    <option key={departamento.id} value={departamento.name}>{departamento.name}</option>
                                ))}

                            </select >
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Provincia</label>
                            <select onChange={handleProvinciaChange} value={provincia} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                <option> </option>
                                {provincias.map(provincia => (
                                    <option key={provincia.id} value={provincia.name}>{provincia.name}</option>
                                ))}

                            </select>
                        </div>
                        <div className="relative z-0 w-full group">
                            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white mb-2 mt-2">Distrito</label>
                            <select value={distrito} onChange={ (e) => setDistrito(e.target.value)} className="bg-gray-50 scroll-auto border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-yellow-400 focus:border-yellow-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required>
                                
                                <option> </option>
                                {distritos.map(distrito => (
                                    <option key={distrito.id} value={distrito.name}>{distrito.name}</option>
                                ))}

                            </select >
                        </div>
                    </div>
                    <div className="mb-5"> 
                        <label className="block text-sm font-medium text-gray-900 dark:text-white mb-2">¿Ofreces algún servicio?</label>
                        <div className=" grid md:grid-cols-3 md:gap-6">
                            <div className="flex items-center mt-2">
                                <input id="user-option-1" name="ofrece-servicio" type="radio" value="2" onChange={(e) => setIdTipo(parseInt(e.target.value))} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600"  />
                                <label className="block ms-2  text-sm font-medium text-gray-900 dark:text-gray-300">
                                    Si
                                </label>
                            </div>
                            <div className="flex items-center mt-2">
                                <input id="user-option-2" name="ofrece-servicio" type="radio" value="1" onChange={(e) => setIdTipo(parseInt(e.target.value))} className="w-4 h-4 border-gray-300 focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-600 dark:focus:bg-blue-600 dark:bg-gray-700 dark:border-gray-600" />
                                <label className="block ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                    No
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="mb-5 flex justify-center my-16">
                        <button onClick={(e) => agregarSegunTipoUsuario(e)} className="focus:outline-none w-1/2 text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#BC7547] font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:focus:ring-[#B4663F]">Siguiente</button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    )
}

export default Formulario;