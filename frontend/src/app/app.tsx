eimport React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, IconButton } from '@mui/material';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import Languages from '../components/Languages';
import Language from '../components/Language';
import Modules from '../components/Modules';
import Sentences from '../components/Sentences';
import './app.scss';

function App() {
  return (
    <Router>
      <div className="App">
        <AppBar position="fixed" sx={{ backgroundColor: 'transparent', boxShadow: 'none' }}>
          <Toolbar sx={{ paddingLeft: 2, paddingRight: 2 }}>
            <Typography variant="h6" component={Link} to="/" style={{ textDecoration: 'none', color: '#4A5899' }} sx={{ fontWeight: 'bold' }}>
              LOQIO
            </Typography>
            <div style={{ flexGrow: 1 }} />
            <IconButton sx={{ color: '#4A5899' }}>
              <SearchOutlinedIcon />
            </IconButton>
            <IconButton sx={{ color: '#4A5899' }}>
              <PersonOutlinedIcon />
            </IconButton>
            <IconButton sx={{ color: '#4A5899' }}>
              <MenuOutlinedIcon />
            </IconButton>
          </Toolbar>
        </AppBar>
        <Routes>
          <Route path="/" element={<Languages />} />
          <Route path="/languages/:languageName" element={<Language />} />
          <Route path="/languages/:languageName/modules" element={<Modules />} />
          <Route path="/languages/:languageName/modules/:moduleName" element={<Sentences />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
