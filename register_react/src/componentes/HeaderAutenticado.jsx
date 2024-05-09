import React from 'react'
import logo_texto from "../imagenes/logo-color.png"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'

export default function HeaderAutenticado() {
  return (
    <div>
        <header>
            <nav className="bg-white border-gray-200 dark:bg-gray-900">
                <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                    <a href="/" className="flex items-center space-x-3 rtl:space-x-reverse">
                        <img src={logo_texto} className="h-auto w-16" alt="Logo de texto" />
                    </a>
                    <div className="flex md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                        <a href="/iniciarsesion" className="text-[#E8A477] bg-white hover:bg-[#BC7547] hover:text-white focus:ring-4 focus:outline-none focus:ring-[#FCE6D8] font-medium rounded-lg text-sm px-4 py-2 text-center">Iniciar sesión <span class="ps-1"><FontAwesomeIcon icon={faUser} /></span> </a>
                    </div>
                </div>
            </nav>
        </header>
    </div>
  )
}
