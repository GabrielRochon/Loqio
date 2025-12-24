import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
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

  if (loading) return <div className="App">Loading languages...</div>;
  if (error) return <div className="App">Error: {error}</div>;

  return (
    <div className="App">
      <header className="App-header">
        <h1>Loqio</h1>
        <h3>Available Languages</h3>
        {languages.length === 0 ? (
          <p>No languages available. Add some languages to get started!</p>
        ) : (
          <div className="languages-list">
            {languages.map(language => (
              <div key={language.id} className="language-card">
                <h3>{language.name}</h3>
              </div>
            ))}
          </div>
        )}
      </header>
    </div>
  );
}

export default App;
