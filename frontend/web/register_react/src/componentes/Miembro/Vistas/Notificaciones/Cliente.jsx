import React, { useEffect, useState } from 'react';
import notificacionesVacias from '../../../../imagenes/Notificaciones/notificaciones_vacias.png';
import { Sidebar } from 'primereact/sidebar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import NotificacionServiceInstance from '../../../../servicios/Miembro/NotificacionService';
import ContratoFinalizado from '../../Modal/Notificaciones/ContratoFinalizado';

const NotificacionesCliente = ({ usuario }) => {
    const [visibleRight, setVisibleRight] = useState(false);
    const [notificaciones, setNotificaciones] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showContratoFinalizadoModal, setShowContratoFinalizadoModal] = useState(false);
    const [idContrato, setIdContrato] = useState(null);

    useEffect(() => {
        const interval = setInterval(() => {
            NotificacionServiceInstance.getNotificacionesCliente(usuario.idCliente)
                .then((notificaciones) => {
                    // Filtrar notificaciones por títulos específicos
                    const filteredNotificaciones = notificaciones.filter(notif =>
                        notif.titulo === '¡A hora buena!' ||
                        notif.titulo === 'Contrato finalizado' ||
                        notif.titulo === 'Lo sentimos...'
                    );
                    setNotificaciones(filteredNotificaciones);
                    setLoading(false);
                })
        }, 1000);

        return () => clearInterval(interval);
    }, [usuario.idCliente]);

    const handleContratoFinalizado = (idContrato) => {
      setIdContrato(idContrato);
      setShowContratoFinalizadoModal(true);
      setVisibleRight(false);
    };

    const eliminarNotificacion = (idNotificacion) => {
      NotificacionServiceInstance.deleteNotificacion(idNotificacion);
    };

    

    return (
        <div>
            <button type="button" onClick={() => setVisibleRight(!visibleRight)} className="flex text-lg md:me-0 text-gray-400 pe-4">
                <span className="sr-only">Notificaciones</span>
                <FontAwesomeIcon icon={faBell} />
            </button>
            <Sidebar visible={visibleRight} position="right" onHide={() => setVisibleRight(false)}>
                {loading ? (
                    <p>Cargando notificaciones...</p>
                ) : notificaciones.length === 0 ? (
                    <div className='flex flex-col items-center justify-center h-full'>
                        <p className='mb-8'>No tienes notificaciones</p>
                        <img src={notificacionesVacias} alt="Notificaciones vacías" />
                    </div>
                ) : (
                    <ul>
                        {notificaciones.map(notif => (
                            <li key={notif.id}>
                                <button type='button' onClick={() => {
                                    if (notif.titulo === 'Contrato finalizado') {
                                        handleContratoFinalizado(notif.idContrato);
                                    }
                                }}>
                                    <div className='flex flex-row justify-between items-start'>
                                        <div className='flex flex-row items-center justify-start'>
                                            <img className="w-16 h-16 object-cover rounded-full" src={notif.idProveedor.idUsuario.imagenUrl} alt={`Imagen de ${notif.idProveedor.nombre}`} />
                                            <div className='pl-4 text-start'>
                                                <label className='text-xs font-bold text-[#717171]'>{notif.idProveedor.nombre.split(' ')[0]} {notif.idProveedor.apellidoPaterno}</label>
                                                <h1 className='font-semibold text-sm'>{notif.titulo}</h1>
                                                <p className='text-[#717171] text-xs'>{notif.mensaje}</p>
                                            </div>
                                        </div>
                                        <button className='text-red-700' type="button" onClick={() => eliminarNotificacion(notif.idNotificacion)}>
                                            <FontAwesomeIcon icon={faDeleteLeft} />
                                        </button>
                                    </div>
                                </button>
                            </li>
                        ))}
                    </ul>
                )}
            </Sidebar>

            {showContratoFinalizadoModal && (
                <ContratoFinalizado
                    idContrato={idContrato}
                    onClose={() => {
                        setShowContratoFinalizadoModal(false);
                        setVisibleRight(true);
                    }}
                />
            )}
        </div>
    );
};

export default NotificacionesCliente;
