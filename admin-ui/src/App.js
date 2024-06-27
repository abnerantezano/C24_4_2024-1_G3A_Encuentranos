import React from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//COMPONENTES
import NavegadorVertical from "./componentes/NavegadorVertical";
//PAGINAS
import Servicios from "./paginas/Servicios";
import Panel from "./paginas/Panel";
import Usuarios from "./paginas/Usuarios";
import Chats from "./paginas/Chats";
import Contratos from "./paginas/Contratos";

const App = () => {
  
  return (
    <Router>
      <div className="flex">
        <NavegadorVertical/>
        <div className="h-screen flex-1 p-7 overflow-auto custom-scrollbar bg-[#F8F5F5]">
          <Routes>
            <Route exact path='/panel' element={<Panel />} />
            <Route exact path='/servicios' element={<Servicios />} />
            <Route exact path='/usuarios' element={<Usuarios/>} />
            <Route exact path='/contratos' element={<Contratos />} />
            <Route exact path='/chats' element={<Chats />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};
export default App;
