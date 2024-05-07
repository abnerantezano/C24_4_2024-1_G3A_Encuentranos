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
              <Route exact path='/' element={<Index/>} />
              <Route exact path='/iniciarSesion' element={<IniciarSesion />} />
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
