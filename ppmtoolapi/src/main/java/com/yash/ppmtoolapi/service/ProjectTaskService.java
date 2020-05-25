package com.yash.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ppmtoolapi.domain.Backlog;
import com.yash.ppmtoolapi.domain.ProjectTask;
import com.yash.ppmtoolapi.repository.BacklogRepository;
import com.yash.ppmtoolapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private BacklogRepository backlogRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
	
		//exception handling: in case project is not available 
		//project task should be added to a specific project, project!=null, backlog exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		
		//set the backlog to the project task
		projectTask.setBacklog(backlog);
		
		//projectTask seq should be like : projID-1
		Integer backlogSequence = backlog.getPTSequence();
		
		//update project task seq number
		backlogSequence++;
		projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence); //FP-01
		
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//setting the default priority and status
		//if project task is created first time, then set the default priority as 3
		
		if(projectTask.getPriority()==null) {
			projectTask.setPriority(3);
	
		}
		if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
			projectTask.setStatus("TODO");
		}
	
	return projectTaskRepository.save(projectTask);
	}
	
}
