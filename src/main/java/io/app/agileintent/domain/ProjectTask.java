package io.app.agileintent.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false,unique = true)
	private String projectTaskSequence;
	
	@NotEmpty(message = "Please include a project summary")
	private String summary;

	@Lob
	private String acceptanceCriteria;
	
	@NotEmpty(message = "status of the Project Task is required")
	private String status;
	
	@NotNull(message = "Priority of the Project Task is required")
	@Min(0)
	@Max(3)
	private Integer priority;
	
	private Date dueDate;

	@Column(updatable = false)
	private String projectIdentifier;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="en_NZ",timezone = "Pacific/Auckland")
	@Column(updatable = false)
	private Date createdAt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="en_NZ",timezone = "Pacific/Auckland")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id",updatable = false,nullable=false)
	@JsonIgnore
	private Backlog backlog;
	

	public ProjectTask() {}

	@PrePersist
	public void onCreate() {
		this.createdAt=new Date();		
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedAt=new Date();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectTaskSequence() {
		return projectTaskSequence;
	}

	public void setProjectTaskSequence(String projectTaskSequence) {
		this.projectTaskSequence = projectTaskSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date create_At) {
		this.createdAt = create_At;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date update_At) {
		this.updatedAt = update_At;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectTaskSequence=" + projectTaskSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", backlog=" + backlog + "]";
	}


	
	
}
