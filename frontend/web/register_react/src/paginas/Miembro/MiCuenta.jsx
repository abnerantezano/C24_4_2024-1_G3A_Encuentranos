import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import InformacionDeUsuario from '../../componentes/Miembro/Datos/InformacionDeUsuario';
import InformacionCliente from '../../componentes/Miembro/Datos/InformacionCliente';
import InformacionProveedor from '../../componentes/Miembro/Datos/InformacionProveedor';
import SeccionPerfil from '../../componentes/Miembro/Vistas/MiCuenta/Seccion_Perfil';
import SeccionInformacionPersonal from '../../componentes/Miembro/Vistas/MiCuenta/Seccion_InformacionPersonal';
import EditarCuenta from '../../componentes/Miembro/Modal/Editar cuenta/EditarCuenta';
import EditarPerfil from '../../componentes/Miembro/Modal/Editar perfil/EditarPerfil';
import EditarIPersonal from '../../componentes/Miembro/Modal/Editar informacion personal/EditarIPersonal';
import ConfirmarModal from '../../componentes/Miembro/Modal/General/ConfirmarAccion';
import usuarioServiceInstance from '../../servicios/Miembro/UsuarioService';

function MiCuenta() {
  //ESTADOS DE LOS VALORES
  const [selectedImage, setSelectedImage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [pendingImage, setPendingImage] = useState(null);
  const [file, setFile] = useState(null);

  // Manejar el cambio de imagen
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setPendingImage(file); // Imagen para actualizar
      setFile(file); // El archivo a enviar
      setShowModal(true);
    } else {
      setSelectedImage(null);
      setFile(null);
    }
  };

  // Limpiar si se cancela el cambio
  const cancelImageChange = () => {
    setPendingImage(null);
    setShowModal(false);
  };

  // Confirmar actualización de imagen
  const confirmImageChange = (user) => {
    if (pendingImage) {
      const formData = new FormData();
      formData.append('usuario', JSON.stringify(user));
      formData.append('archivo', file);

      usuarioServiceInstance.putImagen(user.idUsuario, formData)
        .then((response) => {
          console.log(response);
          setSelectedImage(); //ACTUALIZAR LA IMAGEN
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          setShowModal(false);
          setPendingImage(null);
        });
    
    }
  };

  // Reemplazar * en la contraseña
  const maskPassword = (password) => {
    return '*'.repeat(password.length);
  };

  return (
    <InformacionDeUsuario>
      {(usuario) => (
        <div>
          {usuario.idUsuario && (
            <div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-1/6 rounded-lg my-4'>
                  <div className="relative">
                    <input type="file" accept="image/*" id="imageUpload" onChange={handleImageChange} style={{ display: 'none' }} />
                    <label htmlFor="imageUpload" className="cursor-pointer">
                      <div className="w-full rounded-full flex items-center justify-center overflow-hidden relative">
                        <img src={usuario.imagenUrl} alt="Foto de perfil" className="w-28 h-28 rounded-full object-cover border-gray-300 border" />
                      </div>
                    </label>
                    <button type="button" className="absolute w-8 h-8 bottom-0 right-0 bg-[#E8A477] text-white rounded-full flex items-center justify-center" onClick={() => document.getElementById('imageUpload').click()}>
                      <FontAwesomeIcon icon={faPlus} />
                    </button>
                  </div>
                </div>
                <div className='w-4/6 my-4 px-8'>
                  <label className='font-bold text-base text-[#B4B4B4]'>Correo electrónico</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{usuario.correo}</p>
                  <label className='font-bold text-base text-[#B4B4B4]'>Contraseña</label>
                  <p className='text-sm text-[#787171] mt-1 mb-2'>{maskPassword(usuario.contrasena)}</p>
                </div>
                <div className='1/6 my-4'>
                  <EditarCuenta />
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Perfil</h1>
                  {usuario.idTipo.idTipo === 1 ? (
                    <InformacionCliente>
                      {(cliente) => (
                        <SeccionPerfil usuario={cliente} />
                      )}
                    </InformacionCliente>
                  ) : usuario.idTipo.idTipo === 2 ? (
                    <InformacionProveedor>
                      {(proveedor) => (
                        <SeccionPerfil usuario={proveedor} />
                      )}
                    </InformacionProveedor>
                  ) : null}
                </div>
                <div className='1/6 my-4'>
                  <EditarPerfil />
                </div>
              </div>
              <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                <div className='w-5/6 rounded-lg my-4'>
                  <h1 className='font-bold text-lg'>Información personal</h1>
                  {usuario.idTipo.idTipo === 1 ? (
                    <InformacionCliente>
                      {(cliente) => (
                        <SeccionInformacionPersonal usuario={cliente} />
                      )}
                    </InformacionCliente>
                  ) : usuario.idTipo.idTipo === 2 ? (
                    <InformacionProveedor>
                      {(proveedor) => (
                        <SeccionInformacionPersonal usuario={proveedor} />
                      )}
                    </InformacionProveedor>
                  ) : null}
                </div>
                <div className='my-4'>
                  <EditarIPersonal />
                </div>
              </div>
              <ConfirmarModal
                isOpen={showModal}
                message="¿Estás seguro de cambiar la imagen?"
                onConfirm={() => confirmImageChange(usuario)}
                onCancel={cancelImageChange}
              />
            </div>
          )}
        </div>
      )}
    </InformacionDeUsuario>
  );
}

export default MiCuenta;
