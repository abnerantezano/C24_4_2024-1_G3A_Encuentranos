// App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
//COMPONENTES
import Header from './componentes/Header';
import HeaderAutenticado from './componentes/HeaderAutenticado';
import Footer from './componentes/Footer';
import FooterAutenticado from './componentes/FooterAutenticado';
import InformacionDeUsuario from './componentes/InformacionDeUsuario';
//PAGINAS SIN LOGIN
import Index from './paginas/Index/Index';
import IniciarSesion from './paginas/Index/IniciarSesion';
import Registro from './paginas/Index/Registro';
import CrearUsuario from './paginas/Index/CrearUsuario';
import Formulario from './paginas/Index/Formulario';
//PAGINAS CON LOGIN
import AgregarServicio from './paginas/AgregarServicio';
import ServicioMultiple from './paginas/FuncionAgregarServicios'
import Inicio from './paginas/Inicio';
import Servicios from './paginas/Servicios';
import Precios from './paginas/Precios';
import Perfil from './paginas/Perfil';
import MisServicios from './paginas/MisServicios';
import MisContratos from './paginas/MisContratos';

function App() {

  //SE DECLARA COMO NO INICIO SESION
  const [isLoggedIn, setIsLoggedIn] = useState(false); 

  return (
    <div className="">
      <Router>
        <InformacionDeUsuario>
          {(info) => {
            //VERIFICAR SI HAY INFORMACION DEL USUARIO
            setIsLoggedIn(info !== '');
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
            <Route exact path='/agregarServicio' element={<AgregarServicio />} />
            <Route exact path='/inicio' element={<Inicio />} />
            <Route exact path='/servicios' element={<Servicios />} />
            <Route exact path='/precios' element={<Precios/>} />
            <Route exact path='/perfil' element={<Perfil />} />
            <Route exact path='/misServicios' element={<MisServicios />} />
            <Route exact path='/misContratos' element={<MisContratos/>} />
            <Route exact path='/servicioMultipe' element={<ServicioMultiple />} />
          </Routes>
        </div>
        {isLoggedIn ? <FooterAutenticado /> : <Footer />}
      </Router>
    </div>
  );
}

export default App;

