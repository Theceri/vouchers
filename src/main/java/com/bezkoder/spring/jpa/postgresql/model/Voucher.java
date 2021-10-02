package com.bezkoder.spring.jpa.postgresql.model;

import javax.persistence.*;


@Entity
@Table(name = "vouchers")
public class Voucher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name = "mail")
	private String mail;

	@Column(name = "published")
	private boolean published;

	@Column(name = "username")
	private String username;

	public Voucher() {

	}

	public Voucher(String title, String amount, String mail, boolean published) {
		this.title = title;
		this.amount = amount;
		this.mail = mail;
		this.published = published;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Voucher [id=" + id + ", title=" + title + ", published=" + published + ", amount=" + amount + "]";
	}

}
