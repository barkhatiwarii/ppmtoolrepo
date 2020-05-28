package com.yash.ppmtoolapi.service;

import java.util.List;

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
		// make sure that backlog id exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id: " + backlog_id + " does not exists");
		}

		// make sure that project task id exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task with id :'" + pt_id + "' does not exists");
		}

		// make sure that backlog_id and project identifier are same
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Backlog id " + backlog_id + "does not match with the project identifier"
					+ projectTask.getProjectIdentifier());
		}
		return projectTask;
	}
	
	public ProjectTask UpdateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		//find the existing project task
		ProjectTask projectTask=findPTByProjectSequence(backlog_id, pt_id);
		// replace the project task with the updated task
		projectTask=updatedTask;
		
		//save the project
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask= findPTByProjectSequence(backlog_id, pt_id);
		Backlog backlog= projectTask.getBacklog();
		List<ProjectTask> pts= backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
	}
}
