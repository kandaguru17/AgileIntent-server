package io.app.agileintent.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "project name is required")
	private String projectName;
	// not using the name projectId because jpa uses project_id to establish
	// relationships
	@NotBlank(message = "project identifier is required")
	@Size(min = 4, max = 5)
	@Column(updatable = false, unique = true)
	private String projectIdentifier;

	@Lob
	@NotBlank(message = "description is required")
	private String description;

	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date endDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	@Column(updatable = false)
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-MM" + "-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	private Date updatedAt;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
	private Backlog backlog;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="user_id",updatable = false)
//	@JsonIgnore
//	private User user;

	@ManyToMany(mappedBy = "projects")
	@JsonIgnore
	private List<User> users = new ArrayList<User>();

	@Column(updatable = false)
	private String reportingPerson;

	@PrePersist
	public void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = new Date();
	}

	public Project() {
	}

	// helper method to establish bidirectional relationship with backlog
	public void addBackLog(Backlog backlog) {
		this.setBacklog(backlog);
		backlog.setProject(this);
	}
	
//	public void addUser(User user) {
//		this.getUsers().add(user);
//		user.getProjects().add(this);
//	}
//	
//	public void removeUser(User user) {
//		this.getUsers().remove(user);
//		user.removeProject(this);
//	}

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getReportingPerson() {
		return reportingPerson;
	}

	public void setReportingPerson(String reportingPerson) {
		this.reportingPerson = reportingPerson;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", projectIdentifier=" + projectIdentifier
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", reportingPerson=" + reportingPerson + "]";
	}

}
