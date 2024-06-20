import React, { useEffect, useRef } from 'react';
import InformacionDeUsuario from '../Informacion/InformacionDeUsuario';
import { Link } from 'react-router-dom';

const DropDownPerfil = ({ onClose }) => {
  const dropdownRef = useRef(null);

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      onClose();
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <InformacionDeUsuario>
      {(info) => {
        

        return (
          <div ref={dropdownRef} className="bg-white flex flex-wrap justify-end text-sm list-none rounded-lg shadow-lg p-4 mt-4 z-10 absolute right-16 md:right-4 lg:right-4 xl:right-40">
            <div>
              <p>dsaaaaaaaaaaaaa</p>
              <Link></Link>
            </div>
          </div>
        );
      }}
    </InformacionDeUsuario>
  );
};

export default DropDownPerfil;
