import React from 'react';

const ConfirmarModal = ({ isOpen, message, onConfirm, onCancel }) => {
  return (
    <div className={`fixed top-0 left-0 w-full h-full bg-gray-800 bg-opacity-50 flex items-center justify-center z-50 ${isOpen ? '' : 'hidden'}`}>
      <div className="bg-white rounded-lg p-10">
        <p className="text-base mb-4 text-center">{message}</p>
        <div className="flex justify-center">
          <button className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center" type="button" onClick={onConfirm}>Aceptar</button>
          <button className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20" type="button" onClick={onCancel}>Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmarModal;
