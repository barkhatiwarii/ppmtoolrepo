package com.yash.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.yash.ppmtoolapi.domain.Backlog;
import com.yash.ppmtoolapi.domain.Project;
import com.yash.ppmtoolapi.domain.ProjectTask;
import com.yash.ppmtoolapi.exception.ProjectNotFoundException;
import com.yash.ppmtoolapi.repository.BacklogRepository;
import com.yash.ppmtoolapi.repository.ProjectRepository;
import com.yash.ppmtoolapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			// exception handling: in case project is not available
			// project task should be added to a specific project, project!=null, backlog
			// exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			// set the backlog to the project task
			projectTask.setBacklog(backlog);

			// projectTask seq should be like : projID-1
			Integer backlogSequence = backlog.getPTSequence();

			// update project task seq number
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);

			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence); // FP-01

			projectTask.setProjectIdentifier(projectIdentifier);

			// setting the default priority and status
			// if project task is created first time, then set the default priority as 3

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);

			}
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TODO");
			}

			return projectTaskRepository.save(projectTask);
		} catch (Exception ex) {

			throw new ProjectNotFoundException("Project not found");
		}
	}

	public Iterable<ProjectTask> findBacklogById(String id) { // fp02 if this projectID is not available, means project
																// is not there

		Project project = projectRepository.findByProjectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
}
