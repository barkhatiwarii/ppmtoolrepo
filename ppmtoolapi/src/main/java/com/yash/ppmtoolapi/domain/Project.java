package com.yash.ppmtoolapi.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@Entity
public class Project {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		Long id;
		@NotBlank(message = "Project Name should not be blank")
		String projectName;
		@NotBlank(message = "Project Identifier should not be blank")
		@Size(min=4,max=5,message = "Project Identifier size should be in between 4 and 5")
		@Column(updatable = false,unique = true)
		String projectIdentifier;
		@NotBlank(message = "Project Decription should not be blank")
		String description;
		@JsonFormat(pattern = "yyyy-MM-dd")
		Date start_date;
		@JsonFormat(pattern = "yyyy-MM-dd")
		Date end_date;
		@JsonFormat(pattern = "yyyy-MM-dd")
		Date created_At;
		@JsonFormat(pattern = "yyyy-MM-dd")
		Date updated_At;
		
		@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "project")
		private Backlog backlog;
		public Date getCreated_At() {
			return created_At;
		}
		public void setCreated_At(Date created_At) {
			this.created_At = created_At;
		}
		public Date getUpdated_At() {
			return updated_At;
		}
		public void setUpdated_At(Date updated_At) {
			this.updated_At = updated_At;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		public String getProjectIdentifier() {
			return projectIdentifier;
		}
		public void setProjectIdentifier(String projectIdentifier) {
			this.projectIdentifier = projectIdentifier;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getStart_date() {
			return start_date;
		}
		public void setStart_date(Date start_date) {
			this.start_date = start_date;
		}
		public Date getEnd_date() {
			return end_date;
		}
		public void setEnd_date(Date end_date) {
			this.end_date = end_date;
		}
		public Project() {
			super();
		}
		@PrePersist
		protected void onCreate() {
			this.created_At=new Date();
		}
		@PreUpdate
		protected void onUpdate() {
			this.updated_At=new Date();
		}
	
		public Backlog getBacklog() {
			return backlog;
		}
		public void setBacklog(Backlog backlog) {
			this.backlog = backlog;
		}
		@Override
		public String toString() {
			return "Project [id=" + id + ", projectName=" + projectName + ", projectIdentifier=" + projectIdentifier
					+ ", description=" + description + ", start_date=" + start_date + ", end_date=" + end_date
					+ ", created_At=" + created_At + ", updated_At=" + updated_At + ", backlog=" + backlog + "]";
		}
		
}
