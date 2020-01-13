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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.app.agileintent.validators.ValidIssueType;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, unique = true)
	private String projectTaskSequence;

	@NotEmpty(message = "Please include a project summary")
	private String summary;

	@Lob
	@NotEmpty(message = "Please include acceptance criteria")
	private String acceptanceCriteria;

	@NotEmpty(message = "status of the Project Task is required")
	private String status;

	@NotNull(message = "Priority of the Project Task is required")
	@Min(1)
	@Max(4)
	private Integer priority;

	@Column(updatable = false)
	private String projectIdentifier;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "en_NZ", timezone = "Pacific/Auckland")
	@NotNull
	private Date dueDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	@Column(updatable = false)
	private Date createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;

	@NotNull
	@ValidIssueType(enumClass = IssueType.class, ignoreCase = true)
	private String issueType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectTask", orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<Comment>();

	@OneToMany(mappedBy = "projectTask", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Attachment> attachments = new ArrayList<>();
	
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="assigned_to",updatable = true,nullable = true)
	private User user;
	
	
	public ProjectTask() {
	}

	
	@PrePersist
	public void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = new Date();
	}

	/*
	 * Bi directional relationship between comment and project Task
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setProjectTask(this);
	}

	public void removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setProjectTask(null);
	}

	/*
	 * / projecttask-attachment convinience methods
	 */
	public void addAttachment(Attachment attachment) {
		attachment.setProjectTask(this);
		this.attachments.add(attachment);
	}

	public void removeAttachment(Attachment attachment) {
		this.attachments.remove(attachment);
		attachment.setProjectTask(null);
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

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectTaskSequence=" + projectTaskSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", backlog=" + backlog + "]";
	}

}
