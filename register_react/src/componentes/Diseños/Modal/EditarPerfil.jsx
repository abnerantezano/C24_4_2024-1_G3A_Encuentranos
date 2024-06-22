import React, { useState } from 'react';
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
// COMPONENTES
import InformacionDeUsuario from '../../Informacion/InformacionDeUsuario';
import InformacionProveedor from '../../Informacion/InformacionProveedor';
import InformacionCliente from '../../Informacion/InformacionCliente';
//MODAL RELACIONADO
import ModalSPerfil from '../ModalS_Perfil';

function EditarPerfil() {

    //VARIABLE PARA ABRIR EL MODAL
    const [visible, setVisible] = useState(false);

    //FUNCION PARA ABRIR EL MODAL
    const Modal = () => {
        if (visible) {
        }
        setVisible(!visible);
    };

    return (
        <InformacionDeUsuario>
            {(usuario) => {
                return (
                    <div>
                        <button onClick={Modal} className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold'>
                            Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} />
                        </button>
                        {visible && (
                            <div>
                                {/*SI ES UN CLIENTE, MOSTRARA EL CONTENDIDO DEL COMPONENTE MODALSPERFIL DEL TIPO 1*/}
                                {usuario.idTipo.idTipo === 1 ? (
                                    <InformacionCliente>
                                        {(cliente) => {
                                            return (
                                                <ModalSPerfil usuario={cliente} Modal={Modal}/>
                                            )
                                        }}
                                    </InformacionCliente>
                                    
                                /*SI ES UN PROVEEDOR, MOSTRARA EL CONTENDIDO DEL COMPONENTE MODALSPERFIL DEL TIPO 2*/
                                ) : usuario.idTipo.idTipo === 2 ? (
                                    <InformacionProveedor>
                                        {(proveedor) => {
                                            return (
                                                <ModalSPerfil usuario={proveedor} Modal={Modal}/>
                                            );
                                        }}
                                    </InformacionProveedor>
                                ) : null}
                            </div>
                        )}
                    </div>
                );
            }}
        </InformacionDeUsuario>
    );
}

export default EditarPerfil;
