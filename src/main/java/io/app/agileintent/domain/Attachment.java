package io.app.agileintent.domain;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String attachmentName;

	private String attachmentType;

	@Lob
	@JsonIgnore
	private byte[] attachmentData;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private ProjectTask projectTask;

	private Long attachmentSize;

	public Attachment() {
	}

	public Attachment(String attachmentName, String attachmentType, byte[] attachmentData, Long attachmentSize) {
		this.attachmentName = attachmentName;
		this.attachmentType = attachmentType;
		this.attachmentData = attachmentData;
		this.attachmentSize = attachmentSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public byte[] getAttachmentData() {
		return attachmentData;
	}

	public void setAttachmentData(byte[] bs) {
		this.attachmentData = bs;
	}

	public ProjectTask getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}

	public Long getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(Long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

////	attachment-project task convenience method
//	public void addProjectTask(ProjectTask projectTask) {
//		this.setProjectTask(projectTask);
//		projectTask.getAttachments().add(this);
//	}
//
//	public void removeProjectTask(ProjectTask projectTask) {
//		this.setProjectTask(null);
//		projectTask.getAttachments().remove(this);
//	}

	@Override
	public String toString() {
		return "ProjectTaskAttachment [id=" + id + ", attachmentName=" + attachmentName + ", attachmentType="
				+ attachmentType + ", attachmentData=" + Arrays.toString(attachmentData) + "]";
	}

}
