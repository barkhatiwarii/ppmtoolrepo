import {GET_PROJECT_TASK, GET_BACKLOG, DELETE_PROJECT_TASK} from "../actions/type";
const initialState={
    project_task:[],
    project_tasks:{}
};

export default function(state=initialState,action){
    switch(action.type)
    {
        case GET_BACKLOG:
            return {
                ...state,
                project_tasks:action.payload
            };
        case GET_PROJECT_TASK:
            return{
                ...state,
                project_task:action.payload
            };
        case DELETE_PROJECT_TASK:
            return{
              //todo
            };
        default:
            return state;
    }
}