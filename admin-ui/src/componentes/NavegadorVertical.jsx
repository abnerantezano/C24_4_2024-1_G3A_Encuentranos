import React, { useState } from 'react'
import { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons'

function NavegadorVertical() {
    const [desplegar,setDesplegar] = useState(true);

    return (

        <div className='flex'>
            <div className={`w-72 h-screen bg-white shadow-xl relative`}>
                <img  className='absolute cursor-pointer-right-3 top-9 w-7 border-2'/>
            </div>
            <div className='p-7 text-2xl font-semibold flex-1 h-screen'>
                <h1> Inicio </h1>
            </div>
        </div>
    )
}

export default NavegadorVertical
