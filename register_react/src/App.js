import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import IniciarSesion from './components/IniciarSesion';
import Formulario from './components/Formulario';
import Crud from './components/CRUD';
import Header from './components/Header';
import Index from './components/Index';
import Footer from './components/Footer';


function App() {
  return (
    <div className="">
      <Router>
        <Header />
        <div className='container'>
          <Routes>
            <Route exact path='/' element={<Crud />} />
          </Routes>
        </div>
        <Footer />
      </Router>

      <Formulario />
      <IniciarSesion />

    </div>
  );
}

export default App;
