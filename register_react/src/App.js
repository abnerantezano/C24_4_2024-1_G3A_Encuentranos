import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import IniciarSesion from './components/IniciarSesion';
import Formulario from './components/Formulario';
import Header from './components/Header';
import Index from './components/Index';
import Registro from './components/Registro';
import Footer from './components/Footer';
import Crud from './components/CRUD';


function App() {
  
  const isLoggedIn = false;

  return (
    <div className="">
      <Router>
        <Header />
          <div className=''>
            <Routes>
              <Route exact path='/' element={<IniciarSesion />} />
              <Route exact path='/crud' element={<Crud />} />
              <Route exact path='/IniciarSesion' element={<IniciarSesion />} />
              <Route exact path='/Registro' element={<Registro />} />
              <Route exact path='/Crud' element={<Crud/>} />
              <Route exact path='/Formulario' element={<Formulario />} />
            </Routes>
          </div>
        <Footer />
      </Router>

    </div>
  );
}

export default App;
