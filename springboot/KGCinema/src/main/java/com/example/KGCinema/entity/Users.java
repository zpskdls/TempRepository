package com.example.KGCinema.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	@Id
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private String address;
}
