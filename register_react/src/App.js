// App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Header from './componentes/Header';
import HeaderAutenticado from './componentes/HeaderAutenticado';
import Index from './paginas/Index';
import IniciarSesion from './paginas/IniciarSesion';
import CrearUsuario from './paginas/CrearUsuario';
import Registro from './paginas/Registro';
import Formulario from './paginas/Formulario';
import Footer from './componentes/Footer';
import FooterAutenticado from './componentes/FooterAutenticado';
import Crud from './componentes/CRUD';
import AgregarServicio from './paginas/AgregarServicio';
import InformacionDeUsuario from './componentes/InformacionDeUsuario';

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

