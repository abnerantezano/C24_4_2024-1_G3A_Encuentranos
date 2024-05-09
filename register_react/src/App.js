import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Header from './componentes/Header';
import Index from './componentes/Index';
import IniciarSesion from './componentes/IniciarSesion';
import CrearUsuario from './componentes/CrearUsuario';
import Registro from './componentes/Registro';
import Formulario from './componentes/Formulario';
import Footer from './componentes/Footer';
import Crud from './componentes/CRUD';
import AgregarServicio from './componentes/AgregarServicio';


function App() {
  
  const isLoggedIn = false;

  return (
    <div className="">
      <Router>
        <Header />
          <div className=''>
            <Routes>
              <Route exact path='/' element={<Index/>} />
              <Route exact path='/iniciarSesion' element={<IniciarSesion />} />
              <Route exact path='/crearUsuario' element={<CrearUsuario />} />
              <Route exact path='/registro' element={<Registro />} />
              <Route exact path='/crud' element={<Crud/>} />
              <Route exact path='/formulario' element={<Formulario />} />
              <Route exact path='/agregarServicio' element={<AgregarServicio />} />
            </Routes>
          </div>
        <Footer />
      </Router>

    </div>
  );
}

export default App;
