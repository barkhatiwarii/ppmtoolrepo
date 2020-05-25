package com.yash.ppmtoolapi.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yash.ppmtoolapi.domain.*;
import com.yash.ppmtoolapi.service.MapValidationErrorService;
import com.yash.ppmtoolapi.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {
	@Autowired
	ProjectService projectService;
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	@PostMapping("")
	public ResponseEntity<?> saveProject(@Valid @RequestBody Project project,BindingResult result)
	{
		System.out.print(project);

		ResponseEntity<?> errorMap=mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null)
			return errorMap;
		Project pro=projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(pro, HttpStatus.CREATED);
	}
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectId)
	{
		Project project=projectService.findByProjectIdentifier(projectId);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}
	@GetMapping("/all")
	public Iterable<Project> findAllProjects()
	{
		return projectService.findAllProjects();
	}
	@DeleteMapping("/{projectId}")
	public Project deleteProject(@PathVariable String projectId)
	{
		return projectService.deleteProjectByIdentifier(projectId);
	}
	

}
