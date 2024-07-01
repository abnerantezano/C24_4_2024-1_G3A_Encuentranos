import React from 'react';
//COMPONENTES
import Rol from '../Datos/Rol';
import InformacionCliente from '../Datos/InformacionCliente';
import NotificacionesCliente from '../Vistas/Notificaciones/Cliente';
import NotificacionesProveedor from '../Vistas/Notificaciones/Proveedor';
import InformacionProveedor from '../Datos/InformacionProveedor';

const DropdownNotificaciones = () => {
  return (
    <Rol>
      {(rol) => (
        <div>
          {rol.idTipo === 1 ? (
            <InformacionCliente>
              {(cliente) => (
                <NotificacionesCliente usuario={cliente} />
              )}
            </InformacionCliente>
          ) : rol.idTipo === 2 ? (
            <InformacionProveedor>
              {(proveedor) => (
                <NotificacionesProveedor usuario={proveedor} />
              )}
            </InformacionProveedor>
          ) : null}
        </div>
      )}
    </Rol>
  );
};

export default DropdownNotificaciones;
