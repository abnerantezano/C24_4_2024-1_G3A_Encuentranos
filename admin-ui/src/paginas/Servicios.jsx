import React,{useState,useEffect} from 'react'
//REACT HOOK FORM
import { useForm} from "react-hook-form";
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
//PRIME REACT
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
//AXIOS 
import ServiciosService from '../servicios/Servicios';

function Servicios() {

    //PROPIEDADES DE REACT HOOK FORM
    const { register, handleSubmit, formState: { errors } } = useForm();

    const [servicios, setServicios] = useState([]);
    const [visible, setVisible] = useState();

    //LLAMAR LA LISTA DE SERVICIOS
    useEffect(() => {
        ServiciosService.getLista()
            .then(ServicioResponse => {
                setServicios(ServicioResponse);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    const agregarServicio = (data) => {
        const servicio = {
            nombre: nombre,
            descripcion: descripción,
        }

        console.log(servicio);

        ServiciosService.postServicio(servicio)
            .then (response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            });
    }

    return (
        <div>
            <h1 className='mb-5'>Servicios</h1>
            <form className='flex items-center mb-5 '>
                <div class="mx-4 ">
                    <input type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Nombre del servicio" required />
                </div>
                <button class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto p-2 text-center">Buscar</button>
            </form>
            <div className='flex justify-end mb-5'>
                <button class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Agregar</button>
                <Button label="Show" icon="pi pi-external-link" onClick={() => {setVisible(true); agregarServicio();}} />
                <Dialog header="Header" visible={visible} style={{ width: '50vw' }} onHide={() => setVisible(false)}>
                    <p className="m-0">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </p>
                </Dialog>
            </div>
            <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" class="px-6 py-3">
                                ID
                            </th>
                            <th scope="col" class="px-6 py-3">
                                NOMBRE
                            </th>
                            <th scope="col" class="px-6 py-3">
                                DESCRIPCIÓN
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <span class="sr-only">Edit</span>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <span class="sr-only">Eliminar</span>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {servicios.map((servicio) => (
                            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    {servicio.id}
                                </th>
                                <td class="px-6 py-4">
                                    {servicio.nombre}
                                </td>
                                <td class="px-6 py-4">
                                    {servicio.descripcion}
                                </td>
                                <td class="px-6 py-4 text-right">
                                    <button href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</button>
                                </td>
                                <td class="px-6 py-4 text-right">
                                    <button href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Eliminar</button>
                                </td>
                            </tr>
                        ))}
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                Apple MacBook Pro 17"
                            </th>
                            <td class="px-6 py-4">
                                Silver
                            </td>
                            <td class="px-6 py-4">
                                Laptop
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
                            </td>
                            <td class="px-6 py-4 text-right">
                                <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Eliminar</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Servicios
