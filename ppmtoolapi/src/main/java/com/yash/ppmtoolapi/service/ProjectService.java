package com.yash.ppmtoolapi.service;
import com.yash.ppmtoolapi.domain.*;
import com.yash.ppmtoolapi.exception.ProjectIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yash.ppmtoolapi.repository.*;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdate(Project project) {
		try {
			//while saving the project backlog should be saved
			//when project is not having id, it is a new project - you need to create a backlog
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog); //OneToOne Relationship
				backlog.setProject(project); //OneToOne Relationship
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			}
			//when updating the project, backlog should be set as it is, it should not be null
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch(Exception e)
		{
			throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists !");
		}
		
	}
	public Project findByProjectIdentifier(String projectId)
	{
		Project project=projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null)
		{
			throw new ProjectIdException("Project Id '"+projectId.toUpperCase()+"' does not exists !");
		}
		return project;
		
	}
	public Iterable<Project> findAllProjects()
	{
		return projectRepository.findAll();
	}
	public Project deleteProjectByIdentifier(String projectIdentifier)
	{
		Project project=projectRepository.findByProjectIdentifier(projectIdentifier);
		if(project==null)
		{
			throw new ProjectIdException("Project with Id '"+projectIdentifier+"' does not exists !");
		}
		projectRepository.delete(project);
		return project;
	}
	
}
