package io.app.agileintent.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.app.agileintent.domain.Attachment;
import io.app.agileintent.domain.AttachmentResponse;
import io.app.agileintent.service.AttachmentService;

@RestController
@RequestMapping({ "/api/attachments" })
@CrossOrigin
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@PostMapping({ "upload/{projectIdentifier}/{projectTaskSequence}" })
	public AttachmentResponse uploadAttachment(@RequestParam("file") MultipartFile file,
			@PathVariable String projectIdentifier, @PathVariable String projectTaskSequence, Principal principal) {

		Attachment uploadedFile = attachmentService.storeAttachment(file, projectIdentifier, projectTaskSequence,
				principal);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/attachment/")
				.path(projectIdentifier + "/" + projectTaskSequence + "/" + uploadedFile.getId().toString())
				.toUriString();

		return new AttachmentResponse(uploadedFile.getAttachmentName(), file.getContentType(), fileDownloadUri,
				file.getSize());

	}

	@PostMapping({ "/multipleUpload/{projectIdentifier}/{projectTaskSequence}" })
	public List<AttachmentResponse> multipleUpload(@RequestParam("files") MultipartFile[] files,
			@PathVariable String projectIdentifier, @PathVariable String projectTaskSequence, Principal principal) {

		return Arrays.asList(files).stream()
				.map(file -> uploadAttachment(file, projectIdentifier, projectTaskSequence, principal))
				.collect(Collectors.toList());

	}

	@GetMapping({ "download/{projectIdentifier}/{projectTaskSequence}/{fileId}" })
	public ResponseEntity<?> downloadAttachment(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @PathVariable Long fileId, Principal principal) {

		Attachment foundFile = attachmentService.getFile(projectIdentifier, projectTaskSequence, fileId, principal);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(foundFile.getAttachmentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + foundFile.getAttachmentName() + "\"")
				.body(new ByteArrayResource(foundFile.getAttachmentData()));
	}

	@GetMapping({ "/{projectIdentifier}/{projectTaskSequence}/" })
	public ResponseEntity<?> getAllFile(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, Principal principal) {

		List<Attachment> attachments = attachmentService.getAllFile(projectIdentifier, projectTaskSequence, principal);
		List<AttachmentResponse> allAttachments = new ArrayList<AttachmentResponse>();

		// constructing the response object
		for (int i = 0; i < attachments.size(); i++) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/attachment/")
					.path(projectIdentifier + "/" + projectTaskSequence + "/" + attachments.get(i).getId().toString())
					.toUriString();
			AttachmentResponse attachmentResponse = new AttachmentResponse(attachments.get(i).getAttachmentName(),
					attachments.get(i).getAttachmentType(), fileDownloadUri, attachments.get(i).getAttachmentSize());
			allAttachments.add(attachmentResponse);
		}

		return new ResponseEntity<List<AttachmentResponse>>(allAttachments, HttpStatus.OK);
	}

	@DeleteMapping({ "/{projectIdentifier}/{projectTaskSequence}/{fileId}" })
	public ResponseEntity<?> DeleteAttachment(@PathVariable String projectIdentifier,
			@PathVariable String projectTaskSequence, @PathVariable Long fileId, Principal principal) {

		attachmentService.deleteAttachment(projectIdentifier, projectTaskSequence, fileId, principal);
		return ResponseEntity.ok().body("deleted successfully");

	}

}
