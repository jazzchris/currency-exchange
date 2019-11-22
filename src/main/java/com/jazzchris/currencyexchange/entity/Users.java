package com.jazzchris.currencyexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@ApiModel
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="username")
	@ApiModelProperty(example = "john")
	private String username;
	
	@Column(name="first_name")
	@ApiModelProperty(value = "First name", example = "John")
	private String firstName;
	
	@Column(name="last_name")
	@ApiModelProperty(value = "Last name", example = "Doe")
	private String lastName;
	
	@Column(name="password")
	@JsonIgnore
	private String password;
	
	@Column(name="email")
	@Email
	private String email;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="users_wallet_id")
	private UsersWallet usersWallet;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "users_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@OneToMany(mappedBy="users")
	@JsonManagedReference
	private List<FutureOrder> futureOrder;

	@OneToMany(mappedBy="users")
	@JsonManagedReference
	private List<Order> orders;
	
	public Users() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public UsersWallet getUsersWallet() {
		return usersWallet;
	}

	public void setUsersWallet(UsersWallet usersWallet) {
		this.usersWallet = usersWallet;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public List<FutureOrder> getFutureOrder() {
		return futureOrder;
	}

	public void setFutureOrder(List<FutureOrder> futureOrder) {
		this.futureOrder = futureOrder;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
