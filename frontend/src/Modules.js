import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './App.css';

function Modules() {
  const { languageId } = useParams();
  const navigate = useNavigate();
  const [modules, setModules] = useState([]);
  const [language, setLanguage] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchModules();
  }, [languageId]);

  const fetchModules = async () => {
    try {
      // First fetch the language details
      const languagesResponse = await fetch('http://localhost:8082/languages');
      if (!languagesResponse.ok) {
        throw new Error('Failed to fetch languages');
      }
      const languages = await languagesResponse.json();
      const currentLanguage = languages.find(lang => lang.id.toString() === languageId);
      setLanguage(currentLanguage);

      // Then fetch modules for this language
      const modulesResponse = await fetch(`http://localhost:8082/languages/${languageId}`);
      if (!modulesResponse.ok) {
        throw new Error('Failed to fetch modules');
      }
      const modulesData = await modulesResponse.json();
      setModules(modulesData);
      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const handleBackToLanguages = () => {
    navigate('/');
  };

  if (loading) return <div className="App"><header className="App-header"><p>Loading...</p></header></div>;
  if (error) return <div className="App">Error: {error}</div>;

  return (
    <div className="App">
      <header className="App-header">
        <button onClick={handleBackToLanguages} className="back-button">
          ← Back to Languages
        </button>
        <h1>{language ? language.name : 'Language'} Modules</h1>
        {modules.length === 0 ? (
          <p>No modules available for this language at the moment.</p>
        ) : (
          <div className="modules-list">
            {modules.map(module => (
              <div key={module.id} className="module-card">
                <h3>{module.name}</h3>
                <p>ID: {module.id}</p>
              </div>
            ))}
          </div>
        )}
      </header>
    </div>
  );
}

export default Modules;
