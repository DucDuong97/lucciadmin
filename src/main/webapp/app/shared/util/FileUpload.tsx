import React, {useState} from 'react';
import { toast } from 'react-toastify';
import axios from 'axios';
import {createEntity} from "app/entities/img-url/img-url.reducer";

export function FileUpload(){
  const [selectedFile, setSelectedFile] = useState<File>();
  const [isFilePicked, setIsFilePicked] = useState(false);

  const changeHandler = (event) => {
    const imgType = event.target.files[0].type.split('/')[0];
    if (imgType !== 'image') {
      toast.error("Wrong file format");
      return;
    }
    setSelectedFile(event.target.files[0]);
    setIsFilePicked(true);
  };

  const handleSubmission = () => {
    const formData = new FormData();
    formData.append('image', selectedFile);
    axios.post('api/img-urls/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  };

  return(
    <div className="float-right jh-create-entity">
      <input type="file" name="file" onChange={changeHandler} />
      {isFilePicked ? (
        <div>
          <p>Filename: {selectedFile.name}</p>
          <p>Size in bytes: {selectedFile.size}</p>
        </div>
      ) : (
        <p>Select a file to show details</p>
      )}
      <div>
        <button onClick={handleSubmission} className="btn btn-primary">
          Submit
        </button>
      </div>
    </div>
  )
}
