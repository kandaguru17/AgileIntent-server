package io.app.agileintent.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.app.agileintent.domain.Attachment;
import io.app.agileintent.domain.ProjectTask;
import io.app.agileintent.exceptions.AttachmentException;
import io.app.agileintent.repositories.AttachmentRepository;
import io.app.agileintent.service.AttachmentService;
import io.app.agileintent.service.ProjectTaskService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepoistory;

	@Autowired
	private ProjectTaskService projectTaskService;

	@Override
	public Attachment storeAttachment(MultipartFile file, String projectIdentifier, String projectTaskSequence,
			Principal principal) {

		ProjectTask projectTask = projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier,
				projectTaskSequence, principal);

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains(".."))
				throw new AttachmentException("File contains invalid characters");
			
			Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes(), file.getSize());
			projectTask.addAttachment(attachment);
			return attachmentRepoistory.save(attachment);

		} catch (Exception e) {
			throw new AttachmentException("File size greater than 215MB");
		}
	}

	@Override
	public Attachment getFile(String projectIdentifier, String projectTaskSequence, Long fileId, Principal principal) {
		projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier, projectTaskSequence, principal);
		return attachmentRepoistory.findById(fileId).orElseThrow(() -> new AttachmentException("no such file"));
	}

	@Override
	public List<Attachment> getAllFile(String projectIdentifier, String projectTaskSequence, Principal principal) {

		ProjectTask projectTask = projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier,
				projectTaskSequence, principal);
		return attachmentRepoistory.findAllByProjectTaskId(projectTask.getId());
	}

	@Override
	public void deleteAttachment(String projectIdentifier, String projectTaskSequence, Long fileId,
			Principal principal) {
		ProjectTask projectTask = projectTaskService.getProjectTaskByProjectTaskSequence(projectIdentifier,
				projectTaskSequence, principal);
		Attachment foundAttachment = attachmentRepoistory.findById(fileId).orElse(null);

		if (foundAttachment == null)
			throw new AttachmentException("No such attachment for the project task");

		projectTask.removeAttachment(foundAttachment);		
		attachmentRepoistory.delete(foundAttachment);

	}

}
