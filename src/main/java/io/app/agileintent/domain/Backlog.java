package io.app.agileintent.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer projectTaskSequenceTracker= 0;
	
	//same as the projectidentifier in project entity
	private String projectIdentifier;
	
	@OneToOne
	@JsonIgnore
    @JoinColumn(name="project_id",nullable = false,updatable = false)
	private Project project;
	
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER,mappedBy = "backlog",orphanRemoval = true )
	private List<ProjectTask> projectTasks= new ArrayList<ProjectTask>();

	public Backlog() {}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectTaskSequenceTracker() {
		return projectTaskSequenceTracker;
	}

	public void setProjectTaskSequenceTracker(Integer projectTaskSequenceTracker) {
		this.projectTaskSequenceTracker = projectTaskSequenceTracker;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}


	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	
	public void addProjectTask(ProjectTask projectTask) {
		this.projectTasks.add(projectTask);
		projectTask.setBacklog(this);
		
	}
	
	
	public void removeProjectTask(ProjectTask projectTask) {
		this.projectTasks.remove(projectTask);
		projectTask.setBacklog(null);	
	}

	@Override
	public String toString() {
		return "Backlog [id=" + id + ", projectTaskSequenceTracker=" + projectTaskSequenceTracker
				+ ", projectIdentifier=" + projectIdentifier + ", project=" + project;
	}

}
