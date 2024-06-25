import React, { useState, useEffect } from 'react';
import InformacionDeUsuario from '../../componentes/Informacion/InformacionDeUsuario';
import InformacionCliente from '../../componentes/Informacion/InformacionCliente';
import EditarCuenta from '../../componentes/Diseños/Modal/EditarCuenta';
import SeccionPerfil from '../../componentes/Diseños/Seccion_Perfil';
import InformacionProveedor from '../../componentes/Informacion/InformacionProveedor';
import SeccionInformacionPersonal from '../../componentes/Diseños/Seccion_InformacionPersonal';
import EditarPerfil from '../../componentes/Diseños/Modal/EditarPerfil';
import EditarIPersonal from '../../componentes/Diseños/Modal/EditarIPersonal';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import ConfirmarModal from '../../componentes/Diseños/Modal/ConfirmarAccion';
import usuarioServiceInstance from '../../servicios/Miembro/UsuarioService';

function MiCuenta() {

  //ESTADOS DE LOS VALORES
  const [selectedImage, setSelectedImage] = useState("");
  const [nombreImagen, setNombreImagen] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const [pendingImage, setPendingImage] = useState(null);

  useEffect(() => {
    if (currentUser) {
      setSelectedImage(currentUser.imagenUrl);
    }
  }, [currentUser]);

  //MODAL DE CONFIRMACIÓN
  const handleImageChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      setPendingImage(file);
      setShowModal(true);
    }
  };

  //LIMPIAR SI SE CANCELA EL CAMBIO
  const cancelImageChange = () => {
    setPendingImage(null);
    setShowModal(false);
  };

  const confirmImageChange = (idUsuario) => {
    if (pendingImage) {
      const reader = new FileReader();
      reader.onload = (event) => {
        const imagen = event.target.result;
  
        usuarioServiceInstance.putImagen(idUsuario, imagen)
          .then((response) => {
            console.log(response);
            setSelectedImage(imagen);
            setNombreImagen(pendingImage.name);
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            setShowModal(false);
            setPendingImage(null);
          });
      };
      reader.readAsDataURL(pendingImage);
    }
  };

  //REEMPLAZAR * EN LA CONTRASEÑA
  const maskPassword = (password) => {
    return '*'.repeat(password.length);
  };

  return (
    <InformacionDeUsuario>
      {(usuario) => {
        return (
          <div>
            {usuario.idUsuario && (
              <div>
                <div className='flex flex-wrap items-start justify-start w-full rounded-3xl border px-10 mb-4'>
                  <div className='w-1/6 rounded-lg my-4'>
                    <div className="relative">
                      <input type="file" id="imageUpload" onChange={handleImageChange} style={{ display: 'none' }} />
                      <label htmlFor="imageUpload" className="cursor-pointer">
                        <div className="w-full rounded-full flex items-center justify-center overflow-hidden relative">
                          <img src={selectedImage} alt="Foto de perfil" className="w-28 h-28 rounded-full object-cover border-gray-300 border" />
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
                  onConfirm={() => confirmImageChange(usuario.idUsuario)}
                  onCancel={cancelImageChange}
                />
              </div>
            )}
          </div>
        );
      }}
    </InformacionDeUsuario>
  );
}

export default MiCuenta;
