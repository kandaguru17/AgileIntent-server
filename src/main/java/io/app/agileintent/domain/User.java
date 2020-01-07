package io.app.agileintent.domain;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.app.agileintent.validators.ValidPassword;

@Entity
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292241843927066193L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "User name cannot be empty")
	@Column(unique = true)
	@Email(message = "User name must be a Email Id", groups = { AddUserGroup.class })
	private String username;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	private String lastName;

	@ValidPassword
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirmPassword;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	@Column(updatable = false)
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
	private Date updatedAt;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_project", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "project_id") })
	@JsonIgnore
	private List<Project> projects = new ArrayList<Project>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user",orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments= new ArrayList<>();

	@PrePersist
	public void onCreate() {
		this.createdAt = new Date();
	}

	

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = new Date();
	}

	public User() {
	}

	// convenience methods for establishing bi directional relationship

	public void addProject(Project project) {
		this.projects.add(project);
		project.getUsers().add(this);
		// project.setUser(this);
	}

	public void removeProject(Project project) {
		this.projects.remove(project);
		project.getUsers().remove(this);
		// project.setUsers(null);
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
	}
	
	public void removeComment(Comment comment) {
		this.comments.remove(comment);
		comment.setUser(null);
	}

		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	/*
	 * UserDetails interface mehtods
	 */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", confirmPassword=" + confirmPassword + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", projects=" + projects + "]";
	}

}
