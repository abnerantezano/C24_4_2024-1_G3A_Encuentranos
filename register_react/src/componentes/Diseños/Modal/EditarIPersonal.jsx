import React, { useState } from 'react'
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
//COMPONENTES
import InformacionDeUsuario from '../../Informacion/InformacionDeUsuario';
import InformacionProveedor from '../../Informacion/InformacionProveedor';
import InformacionCliente from '../../Informacion/InformacionCliente';
//MODAL RELACIONADO
import ModalSIPersonal from '../ModalS_IPersonal';

function EditarIPersonal() {

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
                                {usuario.idTipo.idTipo === 1 ? (
                                    <InformacionCliente>
                                        {(cliente) => {
                                            return (
                                                <ModalSIPersonal usuario={cliente} Modal={Modal}/>
                                            )
                                        }}
                                    </InformacionCliente>
                                ) : usuario.idTipo.idTipo === 2 ? (
                                    <InformacionProveedor>
                                        {(proveedor) => {
                                            return (
                                                <ModalSIPersonal usuario={proveedor} Modal={Modal}/>
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
    )
}

export default EditarIPersonal
