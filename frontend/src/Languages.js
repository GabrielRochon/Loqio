import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';

function Languages() {
  const navigate = useNavigate();
  const [languages, setLanguages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [imageErrors, setImageErrors] = useState({});

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

  const handleLanguageClick = (languageName) => {
    navigate(`/languages/${languageName}`);
  };

  if (error) return <div className="App-main">Error: {error}</div>;

  return (
    <div className="App-main">
      <h1>What language would you like to learn today?</h1>
      {languages.length === 0 ? (
        <p>No languages available. Add some languages to get started!</p>
      ) : (
        <div className="languages-list">
          {languages.map(language => (
            <div
              key={language.id}
              className="language-card clickable"
              onClick={() => handleLanguageClick(language.name)}
              style={{
                backgroundImage: !imageErrors[language.id] ? `url(http://localhost:8082/images/${language.name}/background.jpg)` : undefined,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundColor: imageErrors[language.id] ? '#E5DCC5' : undefined
              }}
            >
              {!imageErrors[language.id] && <div className="language-card-overlay"></div>}
              <div className="language-card-content">
                <h3 style={{ color: !imageErrors[language.id] ? 'white' : '#4A5899' }}>{language.name}</h3>
                <img
                  src={`https://flagcdn.com/${language.countryCode?.toLowerCase()}.svg`}
                  alt={`${language.name} Flag`}
                  style={{
                    position: 'absolute',
                    top: '50%',
                    right: '0',
                    width: '36px',
                    height: '24px',
                    borderRadius: '2px',
                    objectFit: 'cover',
                    transform: 'translateY(-50%)'
                  }}
                />
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Languages;
