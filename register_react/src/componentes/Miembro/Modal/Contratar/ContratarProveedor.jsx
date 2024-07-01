import React, { useState } from 'react'
import { useForm } from 'react-hook-form';

function ContratarProveedor() {

  //VARIABLE PARA ABRIR EL MODAL
  const [visible, setVisible] = useState(false);

  //PARAMETROS DE REACT HOOK FORM
  const { handleSubmit, watch, control,reset, formState: { errors } } = useForm();

  //FUNCION PARA ABRIR EL MODAL Y RESETEAR CUANDO SE CIERRE
  const Modal = () => {
      if (visible) {
          reset();
      }
      setVisible(!visible);
  };

  const contratar = () => {

  }

  return (
    <div>
      <button onClick={Modal} className='text-sm text-white bg-[#E8A477] py-2 px-4 rounded-lg font-semibold hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4]'>
        Contratar
      </button>
      {visible && (
        <div id="editarCuenta_modal" className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-30">
          <div className="relative p-10 w-1/2">
            <div className="relative bg-white rounded-lg shadow">
              <div className="flex items-centerjustify-between p-4 md:px-8 md:py-5 border-b rounded-t">
                <h3 className="text-xl font-semibold text-gray-900 ">
                  Contratar proveedor
                </h3>
                <button onClick={Modal} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-700 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center">
                  <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                  </svg>
                  <span className="sr-only">Cerrar</span>
                </button>
              </div>
              <form onSubmit={handleSubmit((data) => contratar(data))}>
                <div className="p-4 overflow-auto max-h-[70vh] custom-scrollbar md:px-8">
                  <div className='w-3/4 mx-auto'>
                          
                  </div>
                </div>
                <div className="flex items-center p-4 md:px-8 md:py-5 border-t border-gray-200 rounded-b justify-end">
                  <button type="submit" className="text-white bg-[#E8A477] hover:bg-[#BC7547] focus:ring-4 focus:ring-[#fcdac4] focus:outline-none font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                    Contratar
                  </button>
                  <button onClick={Modal} type="button" className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-600 focus:outline-none bg-white rounded-lg border border-gray-20 focus:z-10">
                    Cerrar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default ContratarProveedor
