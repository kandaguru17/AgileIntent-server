package io.app.agileintent.domain;

public class AttachmentResponse {

	private String attachmentType;
	private String downloadUri;
	private String attachmentName;
	private Long attachmentSize;
	
	public AttachmentResponse( String attachmentName,String attachmentType, String downloadUri,Long attachmentSize) {
		this.attachmentType = attachmentType;
		this.downloadUri = downloadUri;
		this.attachmentName = attachmentName;
		this.attachmentSize = attachmentSize;
	}
	
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getDownloadUri() {
		return downloadUri;
	}
	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public Long getAttachmentSize() {
		return attachmentSize;
	}
	public void setAttachmentSize(Long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	
}
