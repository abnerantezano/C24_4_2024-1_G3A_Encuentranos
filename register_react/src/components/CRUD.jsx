import React, { useEffect, useState } from "react";
import UsuarioService from "../services/UsuarioService";
import { useNavigate } from "react-router-dom";
import TipoUsuarioService from "../services/TipoUsuarioService";

export const Crud = () => {
    const [usuarios, setUsuarios] = useState([]);
    const [tipoUsuarios, setTipoUsuarios] = useState([]);

    const [correo,setCorreo] = useState('');
    const [contraseña,setContraseña] = useState('');
    const [idTipo,setIdTipo] = useState('');

    const navigate = useNavigate();

    const agregarUsuario = (e) => {
        e.preventDefault();
        const usuario = {idTipo,correo,contraseña};
        UsuarioService.addUser(usuario).then((response) => {
            console.log(response.data);
            navigate('/formulario');
        }).catch(error => {
            console.error(error);
        })
    }

    
    //useEffect(() => {
        //UsuarioService.getAll()
            //.then(response => {
               // setUsuarios(response);
            //})
            //.catch(error => {
                //console.log(error);
            //});
    //}, []); 

    
    //useEffect(() => {
        //TipoUsuarioService.getAll()
            //.then(response => {
                //setTipoUsuarios(response);
            //})
            //.catch(error => {
                //console.log(error);
            //});
    //}, []); 

    //ORDENADO

    useEffect(() => {
        Promise.all([UsuarioService.getAll(), TipoUsuarioService.getAll()])
            .then(([usuariosResponse, tiposUsuarioResponse]) => {
                setUsuarios(usuariosResponse);
                setTipoUsuarios(tiposUsuarioResponse);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <div className="p-5 relative overflow-x-auto">
            <div className="container">
                <div className="row">
                    <div className="card">
                        <form action="">
                            <div>
                            <label > Tipo de usuario (Codigo) </label>
                                <select value={idTipo.id} onChange={(e) => setIdTipo({ ...idTipo, id: e.target.value })}>
                                    <option value="">Seleccionar tipo de usuario</option>
                                    
                                    {
                                        tipoUsuarios.map(tipoUsuario => (
                                            <option key={tipoUsuario.id} value={tipoUsuario.id}>{tipoUsuario.nombre}</option>
                                    ))
                                    }

                                </select>
                            </div>
                            <div className="">
                                <label> Correo </label>
                                <input type="email" name="correo" value={correo} onChange={ (e) => setCorreo(e.target.value)} />
                            </div>
                            <div>
                                <label> Contraseña</label>
                                <input type="password" name="contraseña" value={contraseña} onChange={ (e) => setContraseña(e.target.value)}  />
                            </div>
                            <button onClick={(e) => agregarUsuario(e)}>Agregar</button>
                        </form>
                    </div>
                </div>
            </div>
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="px-6 py-3">
                            Codigo
                        </th>
                        <th scope="col" className="px-6 py-3">
                            Tipo de usuario
                        </th>
                        <th scope="col" className="px-6 py-3">
                            Correo
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {// si un campo es nulo, se puede usar este formato "(usuario.idTipo ? usuario.idTipo.nombre : '')"
                    usuarios.map(usuario => (
                        <tr key={usuario.id} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                {usuario.id}
                            </td>
                            <td className="px-6 py-4">
                                {usuario.idTipo.nombre}  
                            </td>
                            <td className="px-6 py-4">
                                {usuario.correo}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Crud;
