import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Languages from './Languages';
import Modules from './Modules';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Languages />} />
          <Route path="/language/:languageId/modules" element={<Modules />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
