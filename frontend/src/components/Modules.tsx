import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import * as Icons from '@mui/icons-material';
import '../app/app.scss';

interface ModuleData {
  id: number;
  name: string;
  description: string;
  modulePresentation: string;
  materialIconName: string;
}

interface LanguageData {
  id: number;
  name: string;
  countryCode?: string;
  backgroundImageUrl?: string;
}

function Modules() {
  const { languageName } = useParams<string>();
  const [modules, setModules] = useState<ModuleData[]>([]);
  const [language, setLanguage] = useState<LanguageData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const getIconComponent = (iconName: string) => {
    const IconComponent = Icons[iconName as keyof typeof Icons] as React.ComponentType<any>;
    return IconComponent || Icons.HelpOutline;
  };

  const fetchData = useCallback(async () => {
    try {
      const [modulesResponse, languageResponse] = await Promise.all([
        fetch(`http://localhost:8082/languages/${languageName}/modules`),
        fetch(`http://localhost:8082/languages/${languageName}`)
      ]);

      if (!modulesResponse.ok) {
        throw new Error('Failed to fetch modules');
      }
      if (!languageResponse.ok) {
        throw new Error('Failed to fetch language');
      }

      const modulesData = await modulesResponse.json();
      const languageData = await languageResponse.json();

      setModules(modulesData);
      setLanguage(languageData);
      setLoading(false);
    } catch (err) {
      setError((err as Error).message);
      setLoading(false);
    }
  }, [languageName]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  if (loading) return <div className="App-main"><p>Loading...</p></div>;
  if (error) return <div className="App-main">Error: {error}</div>;

  // Process background image URL similar to Languages component
  const backgroundImageUrl = language?.backgroundImageUrl ?
    (language.backgroundImageUrl.includes('blob.core.windows.net') ?
      language.backgroundImageUrl.replace('https://languagesprod.blob.core.windows.net/languages/', 'http://localhost:8082/images/').replace('tagalog', 'Tagalog') :
      language.backgroundImageUrl) :
    null;

  return (
    <div className="App-main modules-page">
      <h1 style={{ marginBottom: '20px', fontSize: '2em' }}>{languageName} Modules</h1>
      <div className="modules-content-container">
        <div className="modules-left-panel" style={backgroundImageUrl ? {
          backgroundImage: `url(${backgroundImageUrl})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center'
        } : {}}>
          {/* Background image panel */}
        </div>
        <div className="modules-right-panel">
          <div className="modules-list">
            {modules.length === 0 ? (
              <p>No modules available for this language.</p>
            ) : (
              <>
                <div className="module-divider"></div>
                {modules.map(module => {
                  const IconComponent = getIconComponent(module.materialIconName || 'MenuBook');
                  return (
                    <React.Fragment key={module.id}>
                      <div className="module-text">
                        <div style={{ display: 'flex', alignItems: 'flex-start', gap: '10px' }}>
                          <IconComponent style={{ color: '#4C5C6D', fontSize: '1.5rem', marginTop: '4px' }} />
                          <div>
                            <h3 style={{ margin: '0' }}>{module.name}</h3>
                            <p style={{ color: '#4C5C6D', margin: '4px 0 0 0' }}>{module.modulePresentation}</p>
                          </div>
                        </div>
                      </div>
                      <div className="module-divider"></div>
                    </React.Fragment>
                  );
                })}
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Modules;
