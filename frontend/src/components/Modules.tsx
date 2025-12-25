import React, { useState, useEffect, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import '../app/app.scss';

interface ModuleData {
  id: number;
  name: string;
  description: string;
}

function Modules() {
  const { languageName } = useParams<string>();
  const [modules, setModules] = useState<ModuleData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchModules = useCallback(async () => {
    try {
      const modulesResponse = await fetch(`http://localhost:8082/languages/${languageName}/modules`);
      if (!modulesResponse.ok) {
        throw new Error('Failed to fetch modules');
      }
      const modulesData = await modulesResponse.json();
      setModules(modulesData);
      setLoading(false);
    } catch (err) {
      setError((err as Error).message);
      setLoading(false);
    }
  }, [languageName]);

  useEffect(() => {
    fetchModules();
  }, [fetchModules]);

  if (loading) return <div className="App-main"><p>Loading...</p></div>;
  if (error) return <div className="App-main">Error: {error}</div>;

  return (
    <div className="App-main modules-page">
      <h1 style={{ marginBottom: '5px' }}>{languageName} Modules</h1>
      <div className="modules-list">
        {modules.length === 0 ? (
          <p>No modules available for this language.</p>
        ) : (
          modules.map(module => (
            <div key={module.id} className="module-text">
              <h3 style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                <MenuBookIcon style={{ color: '#4A5899', fontSize: '1.5rem' }} />
                {module.name}
              </h3>
              <p>{module.description}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default Modules;
