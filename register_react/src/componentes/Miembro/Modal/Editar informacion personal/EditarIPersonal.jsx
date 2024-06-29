import React, { useState } from 'react'
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons';
//COMPONENTES
import InformacionProveedor from '../../Datos/InformacionProveedor';
import InformacionCliente from '../../Datos/InformacionCliente';
//MODAL RELACIONADO
import ModalSIPersonal from './ModalS_IPersonal';
import Rol from '../../Datos/Rol';

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
        <Rol>
            {(rol) => {
                return (
                    <div>
                        <button onClick={Modal} className='text-white bg-[#E8A477] py-2 px-4 rounded-lg font-bold'>
                            Editar<FontAwesomeIcon className='ml-2' icon={faPenToSquare} />
                        </button>
                        {visible && (
                            <div>
                                {rol.idTipo === 1 ? (
                                    <InformacionCliente>
                                        {(cliente) => {
                                            return (
                                                <ModalSIPersonal usuario={cliente} Modal={Modal}/>
                                            )
                                        }}
                                    </InformacionCliente>
                                ) : rol.idTipo === 2 ? (
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
        </Rol>
    )
}

export default EditarIPersonal
