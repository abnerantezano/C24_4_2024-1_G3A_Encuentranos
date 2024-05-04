import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import IniciarSesion from './components/IniciarSesion';
import Formulario from './components/Formulario';
import Header from './components/Header';
import Index from './components/Index';
import Footer from './components/Footer';


function App() {
  
  const isLoggedIn = false;

  return (
    <div className="">
      <Router>
        <Header />
          <div className=''>
            <Routes>
              <Route exact path='/' element={<Index />} />
              <Route exact path='/iniciarsesion' element={<IniciarSesion />} />
              <Route exact path='/formulario' element={<Formulario />} />
            </Routes>
          </div>
        <Footer />
      </Router>

    </div>
  );
}

export default App;
