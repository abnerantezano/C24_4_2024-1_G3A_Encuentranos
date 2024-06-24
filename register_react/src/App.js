// App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
//COMPONENTES
import Header from './componentes/Header/Header';
import HeaderAutenticado from './componentes/Header/HeaderAutenticado';
import Footer from './componentes/Footer/Footer';
import FooterAutenticado from './componentes/Footer/FooterAutenticado';
import InformacionDeUsuario from './componentes/Informacion/InformacionDeUsuario';
//PAGINAS SIN LOGIN
import Index from './paginas/Miembro/Index';
import IniciarSesion from './paginas/Miembro/IniciarSesion';
import Registro from './paginas/Miembro/Registro';
import CrearUsuario from './paginas/Miembro/CrearUsuario';
import Formulario from './paginas/Miembro/Formulario';
//PAGINAS CON LOGIN
import AgregarServicio from './paginas/Miembro/AgregarServicio';
import Inicio from './paginas/Miembro/Inicio';
import Servicios from './paginas/Miembro/Servicios';
import Precios from './paginas/Miembro/Precios';
import Configuracion from './componentes/Diseños/Configuracion_perfil';
import PerfilProveedor from './paginas/Miembro/PerfilProveedor';

function App() {

  //SE DECLARA COMO NO INICIO SESION
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [tipoAdmin, setTipoAdmin] = useState(false);
  const [tipoProveedor, setTipoProveedor] = useState(false); 
  const [tipoCliente, setTipoCliente] = useState(false);
  return (
    <div className="">
      <Router>
        <InformacionDeUsuario>
          {(info) => {
            if (info.idUsuario) {
              setIsLoggedIn(true);
            } else {
              setIsLoggedIn(false);
            }

            if (info.idTipo && info.idTipo.idTipo) {
              if (info.idTipo.idTipo === 1) {
                setTipoCliente(true);
              } else if (info.idTipo.idTipo === 2) {
                setTipoProveedor(true);
              } else if (info.idTipo.idTipo === 3) {
                setTipoAdmin(true);
              } else {
                setIsLoggedIn(false);
                setTipoCliente(false);
                setTipoProveedor(false);
                setTipoAdmin(false);
              }
            }
            
          }}

        </InformacionDeUsuario>

        <div className=''>
        {isLoggedIn ? <HeaderAutenticado /> : <Header />}
          <Routes>
            <Route exact path='/' element={<Index />} />
            <Route exact path='/iniciarSesion' element={<IniciarSesion />} />
            <Route exact path='/crearUsuario' element={<CrearUsuario />} />
            <Route exact path='/registro' element={<Registro />} />
            <Route exact path='/formulario' element={<Formulario />} />
            {/*LOGUEADO*/}
            <Route exact path='/agregar-servicio' element={<AgregarServicio />} />
            <Route exact path='/inicio' element={<Inicio />} />
            <Route exact path='/servicios' element={<Servicios />} />
            <Route exact path='/precios' element={<Precios/>} />
            <Route path='/configuracion/*' element={<Configuracion />} />
            <Route exact path='/proveedor/perfil/:id' element={<PerfilProveedor />} />
          </Routes>
        </div>
        {isLoggedIn ? <FooterAutenticado /> : <Footer />}
      </Router>
    </div>
  );
}

export default App;

