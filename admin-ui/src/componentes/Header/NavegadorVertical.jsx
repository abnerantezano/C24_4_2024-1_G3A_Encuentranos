import React, { useState } from 'react';
import logo_color from "../../imagenes/logo.png";
import logo_texto_color from "../../imagenes/logo-texto.png";
// FONT AWESOME
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft, faChartLine, faFileContract, faToolbox, faUsers } from '@fortawesome/free-solid-svg-icons';
// REACT ROUTER DOM
import { Link, useLocation } from 'react-router-dom';

function NavegadorVertical() {

    const location = useLocation();
    const [desplegar, setDesplegar] = useState(true);

    const Menus = [
        { title: "Panel", link: "/panel", src: faChartLine },
        { title: "Servicios", link: "/servicios", src: faToolbox },
        { title: "Usuarios", link: "/usuarios", src: faUsers },
        { title: "Contratos", link: "/contratos", src: faFileContract },
    ];

    return (
        <div className={` ${desplegar ? "w-72" : "w-28"} bg-[#BC7547] shadow-xl h-screen py-5 pt-8 px-8 relative duration-300`}>
            <button className={`absolute cursor-pointer -right-3 top-9 w-7 bg-white border-white text-[#E8A477] shadow-xl border-2 rounded-full ${!desplegar && "rotate-180"}`} onClick={() => setDesplegar(!desplegar)}>
                <FontAwesomeIcon icon={faAngleLeft} />
            </button>
            <div className="flex gap-x-4 items-center mb-10">
                <img src={logo_color} className={`cursor-pointer duration-500 w-10 ${desplegar && "rotate-[360deg]"}`} />
                <img className={`w-32 origin-left font-medium text-xl duration-200 ${!desplegar && "scale-0"}`} src={logo_texto_color} />
            </div>
            <ul className="pt-6">
                {Menus.map((Menu, index) => (
                    <li key={index} className={`flex rounded-md cursor-pointer text-white text-base items-center gap-x-4
                    ${Menu.gap ? "mt-9" : "mt-2"} ${location.pathname === Menu.link ? "bg-[#fff2ea] text-[#B4663F]" : "hover:bg-light-white"}`}>
                        <Link className="flex rounded-md p-3 items-center gap-x-4 w-full" to={Menu.link}>
                            <FontAwesomeIcon icon={Menu.src} />
                            <span className={`${!desplegar && "hidden"} origin-left duration-200`}>
                                {Menu.title}
                            </span>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default NavegadorVertical;
