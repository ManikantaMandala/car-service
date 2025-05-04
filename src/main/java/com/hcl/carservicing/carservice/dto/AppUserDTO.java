package com.hcl.carservicing.carservice.dto;

import com.hcl.carservicing.carservice.enums.Gender;
import com.hcl.carservicing.carservice.enums.UserRole;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AppUserDTO {

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
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact Number must be only 10 digits")
    private String contactNumber;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 6, max = 18, message = "Username must be between 6 and 18 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,18}$",
			message = "Password must be between 8 and 18 characters and contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;

    @NotNull(message = "Role is mandatory")
    private UserRole role;
    
    private Boolean available = true;

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
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
}
