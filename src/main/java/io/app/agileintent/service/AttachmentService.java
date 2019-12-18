package io.app.agileintent.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.app.agileintent.domain.Attachment;

@Service
public interface AttachmentService {

	public Attachment storeAttachment(MultipartFile file, String projectIdentifer, String projectTaskSequence,
			Principal principal);

	public Attachment getFile(String projectIdentifier, String projectTaskSequence, Long fileId,
			Principal principal);
	
	public List<Attachment> getAllFile(String projectIdentifier, String projectTaskSequence,
			Principal principal);
	
	public void deleteAttachment(String projectIdentifier, String projectTaskSequence,
		Long fileId,	Principal principal);

}
