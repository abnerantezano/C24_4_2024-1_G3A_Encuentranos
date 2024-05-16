// App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Header from './componentes/Header';
import HeaderAutenticado from './componentes/HeaderAutenticado';
import Index from './componentes/Index';
import IniciarSesion from './componentes/IniciarSesion';
import CrearUsuario from './componentes/CrearUsuario';
import Registro from './componentes/Registro';
import Formulario from './componentes/Formulario';
import Footer from './componentes/Footer';
import FooterAutenticado from './componentes/FooterAutenticado';
import Crud from './componentes/CRUD';
import AgregarServicio from './componentes/AgregarServicio';
import Token from './componentes/Token';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Inicialmente no está autenticado

  return (
    <div className="">
      <Router>
        <Token>
          {(token) => {
            // Verificar si hay un token válido para determinar la autenticación
            setIsLoggedIn(token !== '');
          }}
        </Token>
        
        <div className=''>
        {isLoggedIn ? <HeaderAutenticado /> : <Header />}
          <Routes>
            <Route exact path='/' element={<Index />} />
            <Route exact path='/iniciarSesion' element={<IniciarSesion />} />
            <Route exact path='/crearUsuario' element={<CrearUsuario />} />
            <Route exact path='/registro' element={<Registro />} />
            <Route exact path='/crud' element={<Crud />} />
            <Route exact path='/formulario' element={<Formulario />} />
            <Route exact path='/agregarServicio' element={<AgregarServicio />} />
          </Routes>
        </div>
        {isLoggedIn ? <FooterAutenticado /> : <Footer />}
      </Router>
    </div>
  );
}

export default App;

