import React, { useEffect, useState } from "react";
import UsuarioService from "../services/UsuarioService";
import { useNavigate } from "react-router-dom";

export const Crud = () => {
    const [usuarios, setUsuarios] = useState([]);

    const [correo,setCorreo] = useState('');
    const [contraseña,setContraseña] = useState('');
    const [idTipo,setIdTipo] = useState('');

    const navigate = useNavigate();

    const agregarUsuario = (e) => {
        e.preventDefault();
        const usuario = {idTipo,correo,contraseña};
        UsuarioService.addUser(usuario).then((response) => {
            console.log(response.data);
            navigate('/');
        }).catch(error => {
            console.error(error);
        })
    }

    const tiposDeUsuario = [
        { id: 1, nombre: 'Cliente' },
    ];

    useEffect(() => {
        UsuarioService.getAll()
            .then(response => {
                setUsuarios(response);
            })
            .catch(error => {
                console.log(error);
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
                                <select value={idTipo.id} onChange={(e) => setIdTipo(e.target.value )}>
                                    <option value="">Seleccionar tipo de usuario</option>

                                    {tiposDeUsuario.map((tipo) => (
                                        <option key={tipo.id} value={tipo.id}>{tipo.nombre}</option>
                                    ))}

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
