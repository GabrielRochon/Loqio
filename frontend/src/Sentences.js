import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './App.css';

function Sentences() {
  const { moduleId } = useParams();
  const navigate = useNavigate();
  const [sentences, setSentences] = useState([]);
  const [module, setModule] = useState(null);
  const [language, setLanguage] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchSentences();
  }, [moduleId]);

  const fetchSentences = async () => {
    try {
      // Fetch sentences for this module
      const sentencesResponse = await fetch(`http://localhost:8082/modules/${moduleId}/sentences`);
      if (!sentencesResponse.ok) {
        throw new Error('Failed to fetch sentences');
      }
      const sentencesData = await sentencesResponse.json();

      // Sort sentences by position
      sentencesData.sort((a, b) => (a.position || 0) - (b.position || 0));
      setSentences(sentencesData);

      // If we have sentences, try to get module and language info
      if (sentencesData.length > 0 && sentencesData[0].module) {
        setModule(sentencesData[0].module);
        if (sentencesData[0].module.language) {
          setLanguage(sentencesData[0].module.language);
        }
      }

      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const handleBackToModules = () => {
    navigate(-1); // Go back to previous page
  };

  if (loading) return <div className="App"><header className="App-header"><p>Loading...</p></header></div>;
  if (error) return <div className="App">Error: {error}</div>;

  return (
    <div className="App">
      <header className="App-header">
        <button onClick={handleBackToModules} className="back-button">
          ← Back
        </button>
        <h1>{language ? language.name : 'Language'} - {module ? module.name : 'Module'}</h1>
        <div className="sentences-container">
          {sentences.length === 0 ? (
            <p>No sentences available for this module at the moment.</p>
          ) : (
            <div className="sentences-list">
              {sentences.map((sentence, index) => (
                <div key={sentence.id} className={`sentence-bubble ${sentence.speaker === 1 ? 'speaker-1' : 'speaker-2'}`}>
                  <div className="sentence-content">
                    <div className="learning-text">{sentence.learningText}</div>
                    <div className="translation-text">{sentence.translationText}</div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </header>
    </div>
  );
}

export default Sentences;
