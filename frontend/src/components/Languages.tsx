import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../app/app.scss';

interface LanguageData {
  id: number;
  name: string;
  countryCode?: string;
  backgroundImageUrl?: string;
}

function Languages() {
  const navigate = useNavigate();
  const [languages, setLanguages] = useState<LanguageData[]>([]);
  const [error, setError] = useState<string | null>(null);

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
    } catch (err) {
      setError((err as Error).message);
    }
  };

  const handleLanguageClick = (languageName: string) => {
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
          {languages.map(language => {
            const backgroundImageUrl = language.backgroundImageUrl ?
              (language.backgroundImageUrl.includes('blob.core.windows.net') ?
                language.backgroundImageUrl.replace('https://languagesprod.blob.core.windows.net/languages/', 'http://localhost:8082/images/').replace('tagalog', 'Tagalog') :
                language.backgroundImageUrl) :
              null;

            return (
              <div
                key={language.id}
                className="language-card clickable"
                onClick={() => handleLanguageClick(language.name)}
                style={backgroundImageUrl ? {
                  backgroundImage: `url(${backgroundImageUrl})`,
                  backgroundSize: 'cover',
                  backgroundPosition: 'center'
                } : {}}
              >
                {!language.backgroundImageUrl && <div className="language-card-overlay"></div>}
                <div className="language-card-content">
                  <h3 style={{ color: 'white' }}>{language.name}</h3>
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
            );
          })}
        </div>
      )}
    </div>
  );
}

export default Languages;
