package com.hcl.carservicing.carservice.model;

import java.time.LocalDateTime;
import java.util.List;

import com.hcl.carservicing.carservice.enums.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.hcl.carservicing.carservice.enums.UserRole;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "First Name is mandatory")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;
    
    @NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
    
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
    
    @NotBlank(message = "Contact Number is mandatory")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact Number must be only 10 digits")
    private String contactNumber;

    @Size(min = 6, max = 18, message = "UserId must be between 6 and 18 characters")
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;

    private String password;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    private UserRole role;

	@CreationTimestamp
    private LocalDateTime createdAt;

	@OneToMany(mappedBy = "user",
		fetch = FetchType.EAGER,
		cascade = {
			CascadeType.PERSIST, CascadeType.MERGE
		}
	)
	private List<ServiceRequest> serviceRequests;

	@OneToMany(mappedBy = "appUser",
		fetch = FetchType.EAGER,
		cascade = {
			CascadeType.PERSIST, CascadeType.MERGE
		}
	)
	private List<VehicleDetails> vehicleDetails;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<ServiceRequest> getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public List<VehicleDetails> getVehicleDetails() {
		return vehicleDetails;
	}

	public void setVehicleDetails(List<VehicleDetails> vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
	}

	@Override
	public String toString() {
		return "AppUser{" +
				"user_id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				", gender=" + gender +
				", contactNumber='" + contactNumber + '\'' +
				", username='" + username + '\'' +
				", role=" + role +
				", createdAt=" + createdAt +
				", servicingRequests=" + serviceRequests +
				'}';
	}

}
	
