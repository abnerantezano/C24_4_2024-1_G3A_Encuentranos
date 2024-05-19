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
//PAGINAS
import Index from './paginas/Index';
import IniciarSesion from './paginas/IniciarSesion';
import Registro from './paginas/Registro';
import CrearUsuario from './paginas/CrearUsuario';
import Formulario from './paginas/Formulario';
import AgregarServicio from './paginas/AgregarServicio';
import Inicio from './paginas/Inicio';
import Servicios from './paginas/Servicios';
import Precios from './paginas/Precios';

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
            <Route exact path='/agregarServicio' element={<AgregarServicio />} />
            <Route exact path='/inicio' element={<Inicio />} />
            <Route exact path='/servicios' element={<Servicios />} />
            <Route exact path='/precios' element={<Precios/>} />
          </Routes>
        </div>
        {isLoggedIn ? <FooterAutenticado /> : <Footer />}
      </Router>
    </div>
  );
}

export default App;

