import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../app/app.scss';

interface LanguageData {
  name: string;
  countryCode?: string;
  languagePresentation?: string;
}

function Language() {
  const { languageName } = useParams<string>();
  const navigate = useNavigate();
  const [language, setLanguage] = useState<LanguageData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [imageError, setImageError] = useState(false);

  const fetchLanguage = useCallback(async () => {
    try {
      const response = await fetch(`http://localhost:8082/languages/${languageName}`);
      if (!response.ok) {
        throw new Error('Failed to fetch language');
      }
      const data = await response.json();
      setLanguage(data);
      setLoading(false);
    } catch (err) {
      setError((err as Error).message);
      setLoading(false);
    }
  }, [languageName]);

  useEffect(() => {
    fetchLanguage();
  }, [fetchLanguage]);

  useEffect(() => {
    if (language) {
      const img = new Image();
      img.onload = () => setImageError(false);
      img.onerror = () => setImageError(true);
      img.src = `http://localhost:8082/images/${language.name}/background.jpg`;
    }
  }, [language]);

  const handleBackToLanguages = () => {
    navigate('/');
  };

  const handleBeginJourney = () => {
    navigate(`/languages/${languageName}/modules`);
  };

  if (loading) return <div className="App-main"><p>Loading...</p></div>;
  if (error) return <div className="App-main">Error: {error}</div>;

  return (
    <div className="App-main" style={{
      backgroundImage: !imageError && language ? `url(http://localhost:8082/images/${language.name}/background.jpg)` : undefined,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundColor: imageError ? '#E5DCC5' : undefined,
      position: 'relative'
    }}>
      {!imageError && <div className="language-card-overlay" style={{ position: 'absolute', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0, 0, 0, 0.3)', zIndex: 0 }}></div>}
      <div className="modules-content" style={{ position: 'relative', zIndex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <button onClick={handleBackToLanguages} style={{ alignSelf: 'flex-start', backgroundColor: 'transparent', color: !imageError ? 'white' : '#4A5899', border: '2px solid ' + (!imageError ? 'white' : '#4A5899'), borderRadius: '5px', padding: '10px 20px', cursor: 'pointer', fontSize: '1rem', marginBottom: '20px' }}>
          ← Back to Languages
        </button>
        <p style={{ textAlign: 'left', color: !imageError ? 'white' : '#4A5899', alignSelf: 'flex-start', fontSize: '1.5rem', margin: '20px 0 10px 0' }}>Start your adventure with</p>
        <div style={{ display: 'flex', alignItems: 'center', alignSelf: 'flex-start', margin: '0 0 10px 0' }}>
          <h1 style={{ fontWeight: 'bold', textAlign: 'left', color: !imageError ? 'white' : '#4A5899', fontSize: '6rem', margin: 0 }}>{language ? language.name : 'Language'}</h1>
          <img
            src={`https://flagcdn.com/${language?.countryCode?.toLowerCase()}.svg`}
            alt={`${language?.name} Flag`}
            style={{
              height: '6rem',
              width: 'auto',
              borderRadius: '8px',
              marginLeft: '40px'
            }}
          />
        </div>
        <p style={{ textAlign: 'left', color: !imageError ? 'white' : '#4A5899', alignSelf: 'flex-start', fontSize: '1rem', margin: '0 0 20px 0', maxWidth: '600px', overflowWrap: 'break-word' }}>{language && language.languagePresentation ? language.languagePresentation : 'No language presentation available.'}</p>
        <button onClick={handleBeginJourney} className="begin-journey-button" style={{ alignSelf: 'flex-start', marginBottom: '20px', padding: '10px 20px', backgroundColor: !imageError ? 'white' : '#618B4A', color: !imageError ? '#4A5899' : '#F6F0ED', border: 'none', borderRadius: '5px', cursor: 'pointer', fontSize: '1.2rem' }}>Begin your journey</button>
      </div>
    </div>
  );
}

export default Language;
