// Mensajes.js

import React, { useState, useEffect } from 'react';

function Mensajes({ id }) {
  const [mensajes, setMensajes] = useState([]);

  // Simulación de carga de mensajes (puedes cargarlos desde una API)
  useEffect(() => {
    // Aquí cargarías los mensajes del chat con el ID id
    const fetchedMensajes = [
      { id: 1, contenido: 'Mensaje 1' },
      { id: 2, contenido: 'Mensaje 2' },
      { id: 3, contenido: 'Mensaje 3' },
    ];
    setMensajes(fetchedMensajes);
  }, [id]);

  return (
    <div>
      {/* Mostrar los mensajes */}
      <ul>
        {mensajes.map(mensaje => (
          <li key={mensaje.id}>{mensaje.contenido}</li>
        ))}
      </ul>
    </div>
  );
}

export default Mensajes;
