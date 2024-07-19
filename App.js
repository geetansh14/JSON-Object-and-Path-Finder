import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [jsonContent, setJsonContent] = useState(null);
  const [selectedField, setSelectedField] = useState('');
  const [responsePath, setResponsePath] = useState('');

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      setJsonContent(JSON.parse(e.target.result));
    };
    reader.readAsText(file);
  };

  const handleSubmitJson = () => {
    axios.post('http://localhost:8080/api/json/search', jsonContent)
      .then(response => {
        console.log('File uploaded successfully', response);
        setResponsePath(response.data.path); // Assuming your response JSON has a 'path' property
      })
      .catch(error => {
        console.error('Error uploading file', error);
      });
  };

  const handleFieldClick = (fieldPath) => {
    setSelectedField(fieldPath);
  };

  const handleSubmitField = () => {
    axios.post('http://localhost:8080/api/json/search', {
      json: JSON.stringify(jsonContent),
      fieldName: selectedField
    })
    .then(response => {
      console.log('Field path found:', response.data.path);
      setResponsePath(response.data.path); // Set the path received from backend response
    })
    .catch(error => {
      console.error('Error submitting field', error);
    });
  };

  const renderJson = (obj, path = '') => {
    return Object.keys(obj).map(key => {
      const value = obj[key];
      const newPath = path ? `${path}.${key}` : key;

      if (typeof value === 'object' && value !== null) {
        return (
          <div key={newPath} className="json-field">
            <span
              className="json-key"
              onClick={() => handleFieldClick(newPath)}
              style={{ cursor: 'pointer' }}
            >
              <strong>{key}</strong>
            </span>
            <div className="json-children">{renderJson(value, newPath)}</div>
          </div>
        );
      } else {
        return (
          <div key={newPath} className="json-field">
            <span
              className="json-key"
              onClick={() => handleFieldClick(newPath)}
              style={{ cursor: 'pointer' }}
              id={newPath}
            >
              <strong>{key}:</strong> {value}
            </span>
          </div>
        );
      }
    });
  };

  return (
    <div className="App">
      <div className="header">
        <h1>Path Finder</h1>
      </div>
      <div className="content">
        <div className="file-upload">
          <input type="file" onChange={handleFileUpload} />
          <button onClick={handleSubmitJson}>Submit</button>
        </div>
        <div className="json-editor">
          <div>{jsonContent && renderJson(jsonContent)}</div>
        </div>
        <div className="field-container">
          <input type="text" value={selectedField} readOnly />
          <button onClick={handleSubmitField} disabled={!selectedField}>Submit</button>
        </div>
        <div>
          <h2>Path</h2>
          <input type="text" value={responsePath} readOnly />
        </div>
      </div>
    </div>
  );
}

export default App;
