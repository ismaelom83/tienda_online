package com.proyecto_tienda.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordForm {
	
	@NotNull
	private int id;
	
	@NotBlank(message="La contraseña actual no debe estar en blanco")
	private String currentPassword;

	@NotBlank(message="La Nueva Contraseña no debe de estar en blanco")
	private String newPassword;

	@NotBlank(message="La confirmacion de contraseña no debe de estar en blanco")
	private String confirmPassword;

	public ChangePasswordForm() { }
	public ChangePasswordForm(@NotNull int id) {this.id = id;}
	
	public @NotNull int getId() {
		return id;
	}
	public void setId(@NotNull int id) {
		this.id = id;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordForm [id=" + id + ", currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}

}
