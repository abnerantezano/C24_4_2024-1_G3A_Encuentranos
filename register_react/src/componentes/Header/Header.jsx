import React from 'react'
import logo_texto from "../../imagenes/logo-texto.png"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'

export const Header = () => {
  return (
    <div>
        <header>
            <nav className="bg-[#F3C7AC] dark:bg-orange-900 w-full">
                <div className="px-6 xl:px-36 flex flex-wrap items-center justify-between mx-auto py-4">
                    <a href="/" className="flex items-center space-x-3 rtl:space-x-reverse">
                        <img src={logo_texto} className="h-auto w-36" alt="Logo de texto" />
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

export default Header
