import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';

function Languages() {
  const navigate = useNavigate();
  const [languages, setLanguages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchLanguages();
  }, []);

  const fetchLanguages = async () => {
    try {
      const response = await fetch('http://localhost:8082/languages');
      if (!response.ok) {
        throw new Error('Failed to fetch languages');
      }
      const data = await response.json();
      setLanguages(data);
      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const handleLanguageClick = (languageId) => {
    navigate(`/language/${languageId}/modules`);
  };

  if (error) return <div className="App-main">Error: {error}</div>;

  return (
    <div className="App-main">
      <h2>Available Languages</h2>
      {languages.length === 0 ? (
        <p>No languages available. Add some languages to get started!</p>
      ) : (
        <div className="languages-list">
          {languages.map(language => (
            <div
              key={language.id}
              className="language-card clickable"
              onClick={() => handleLanguageClick(language.id)}
            >
              <h3>{language.name}</h3>
              <p>ID: {language.id}</p>
              <p className="click-hint">Click to view modules</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Languages;
