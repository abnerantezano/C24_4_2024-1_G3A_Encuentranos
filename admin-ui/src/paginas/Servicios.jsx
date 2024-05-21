import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
//REACT HOOK FORM
import { useForm } from "react-hook-form";
// FONT AWESOME
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMinus } from "@fortawesome/free-solid-svg-icons";
//PRIME REACT
import { Dialog } from "primereact/dialog";
import { Button } from "primereact/button";
//AXIOS
import ServiciosService from "../servicios/Servicios";

function Servicios() {
  //PROPIEDADES DE REACT HOOK FORM
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  //DECLARAR UNA VARIABLE PARA LA NAVEGACIÓN
  const navigate = useNavigate();

  const [servicios, setServicios] = useState([]);
  const [id_servicio, setIdServicio] = useState();
  const [data, setData] = useState([]);

  //LLAMAR LA LISTA DE SERVICIOS
  useEffect(() => {
    ServiciosService.getLista()
      .then((ServicioResponse) => {
        setServicios(ServicioResponse);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const eliminarServicio = (idServicio) => {
    ServiciosService.deleteServicio(idServicio)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const editarServicio = (data, idServicio) => {
    const datos = {
      nombre: data.nombre,
      descripcion: data.descripcion,
    };

    ServiciosService.putServicio(datos, idServicio)
      .then((response) => {
        console.log(response);
        console.log("Servicio actualizado");
      })
      .catch((error) => {
        console.log(error);
      });

    navigate("/servicios");
  };

  const agregarServicio = (data) => {
    const servicio = {
      nombre: data.nombre,
      descripcion: data.descripcion,
    };

    console.log(servicio);

    ServiciosService.postServicio(servicio)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });

    navigate("/servicios");
  };

  return (
    <div>
      <h1 className="mb-5">Servicios</h1>
      {/*<form className="flex items-center mb-5 ">
        <div className="mx-4 ">
          <input
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
            placeholder="Nombre del servicio"
            required
          />
        </div>
        <button className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto p-2 text-center">
          Buscar
        </button>
      </form>*/}
      <form
        onSubmit={handleSubmit((data) => editarServicio(data, id_servicio))}
      >
        <div>
          <label>Editar servicio</label>
          <select
            value={parseInt(id_servicio)}
            onChange={(e) => setIdServicio(e.target.value)}
            id="countries"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          >
            <option value="">Choose a service</option>{" "}
            {servicios.map((servicio) => (
              <option key={servicio.id_servicio} value={servicio.id_servicio}>
                {servicio.id_servicio}
              </option>
            ))}
          </select>
          <input
            name="nombre"
            {...register("nombre", { required: true })}
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
            placeholder="Nombre del servicio"
          />
          {errors.nombre && (
            <span className="text-red-500 text-sm">
              Ingrese el nombre del servicio
            </span>
          )}
          {errors.nombre && (
            <span className="text-red-500 text-sm">
              Ingrese el nombre del servicio
            </span>
          )}
          <textarea
            rows="4"
            name="descripcion"
            placeholder="Descripción del servicio"
            {...register("descripcion", { required: true })}
            className="w-full px-0 text-sm text-gray-900 bg-white p-2.5 px-2.5 rounded-lg bg-gray-50 border border-gray-300 dark:bg-gray-800 focus:ring-0 mb-50"
          ></textarea>
          {errors.descripcion && (
            <span className="text-red-500 text-sm">
              Llene la descripción del servicio
            </span>
          )}
        </div>
        <button
          type="submit"
          className="bg-gray-500 p-2 text-white px-2 rounded-lg px-0 text-sm text-gray-900 bg-white border-1"
        >
          Editar
        </button>
      </form>

      {/*</form>
      <form onSubmit={handleSubmit(agregarServicio)}>
        <div>
          <label>Agregar un servicio</label>
          <input
            name="nombre"
            {...register("nombre", { required: true })}
            type="text"
            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 mb-5"
            placeholder="Nombre del servicio"
          />
          {errors.nombre && (
            <span className="text-red-500 text-sm">
              Ingrese el nombre del servicio
            </span>
          )}
          <textarea
            rows="4"
            name="descripcion"
            placeholder="Descripción del servicio"
            {...register("descripcion", { required: true })}
            className="w-full px-0 text-sm text-gray-900 bg-white p-2.5 px-2.5 rounded-lg bg-gray-50 border border-gray-300 dark:bg-gray-800 focus:ring-0 mb-50"
          ></textarea>
          {errors.descripcion && (
            <span className="text-red-500 text-sm">
              Llene la descripción del servicio
            </span>
          )}
        </div>
        <button
          type="submit"
          className="bg-gray-500 p-2 text-white px-2 rounded-lg px-0 text-sm text-gray-900 bg-white border-1"
        >
          Agregar
        </button>
        </form>*/}
      {/*<div className="flex justify-end mb-5">
        <button class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">
          Agregar
        </button>
        <Button
          label="Show"
          icon="pi pi-external-link"
          onClick={() => {
            setVisible(true);
            agregarServicio();
          }}
        />
        <Dialog
          header="Header"
          visible={visible}
          style={{ width: "50vw" }}
          onHide={() => setVisible(false)}
        >
          <p className="m-0">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
            ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
            aliquip ex ea commodo consequat. Duis aute irure dolor in
            reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
            pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
            culpa qui officia deserunt mollit anim id est laborum.
          </p>
        </Dialog>
        </div>*/}
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
              <tr
                key={servicio.id_servicio}
                class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
              >
                <th
                  scope="row"
                  class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  {servicio.id_servicio}
                </th>
                <td class="px-6 py-4">{servicio.nombre}</td>
                <td class="px-6 py-4">{servicio.descripcion}</td>
                <td class="px-6 py-4 text-right">
                  <button class="font-medium text-blue-600 dark:text-blue-500 hover:underline">
                    Edit
                  </button>
                </td>
                <td class="px-6 py-4 text-right">
                  <button
                    onClick={() => eliminarServicio(servicio.id_servicio)}
                    class="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Servicios;
