import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import IniciarSesion from './components/IniciarSesion';
import Formulario from './components/Formulario';
import Header from './components/Header';
import Index from './components/Index';
import Registro from './components/Registro';
import Footer from './components/Footer';
import Crud from './components/CRUD';
import AgregarServicio from './components/AgregarServicio';


function App() {
  
  const isLoggedIn = false;

  return (
    <div className="">
      <Router>
        <Header />
          <div className=''>
            <Routes>
              <Route exact path='/' element={<Crud />} />
              <Route exact path='/Crud' element={<Crud />} />
              <Route exact path='/IniciarSesion' element={<IniciarSesion />} />
              <Route exact path='/Registro' element={<Registro />} />
              <Route exact path='/Formulario' element={<Formulario />} />
              <Route exact path='/AgregarServicio' element={<AgregarServicio />} />
            </Routes>
          </div>
        <Footer />
      </Router>

    </div>
  );
}

export default App;
