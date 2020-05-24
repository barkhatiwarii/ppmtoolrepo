import axios from 'axios';
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from './type';

export const createProject=(project,history)=>async dispatch=> {
    try {
        const res =await axios.post ("http://localhost:8080/api/projects",project)
        history.push("/dashboard");
    } catch (err) {
        dispatch({
            type:GET_ERRORS,
            payload:err.response.data
        });
    }
};
export const getProjects=(project,history)=>async dispatch=> {
        const res =await axios.get ("http://localhost:8080/api/projects/all",project)
        dispatch({
            type:GET_PROJECTS,
            payload:res.data
        });
    
};
export const getProject=(id,history)=>async dispatch=> {
    const res =await axios.get (`http://localhost:8080/api/projects/${id}`);
    dispatch({
        type:GET_PROJECT,
        payload:res.data
    });

};
export const deleteProject=id=>async dispatch=> {
    const res =await axios.delete (`http://localhost:8080/api/projects/${id}`);
    dispatch({
        type:DELETE_PROJECT,
        payload:id
    });

};