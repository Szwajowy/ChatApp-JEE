package com.jsf.entities;
// Generated 2019-02-19 14:33:32 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Friend generated by hbm2java
 */
@Entity
@Table(name = "friend", catalog = "chatapp")
public class Friend implements java.io.Serializable {

	private Integer id;
	private User userByUserId;
	private User userByFriendId;

	public Friend() {
	}

	public Friend(User userByUserId, User userByFriendId) {
		this.userByUserId = userByUserId;
		this.userByFriendId = userByFriendId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID", nullable = false)
	public User getUserByUserId() {
		return this.userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friendID", nullable = false)
	public User getUserByFriendId() {
		return this.userByFriendId;
	}

	public void setUserByFriendId(User userByFriendId) {
		this.userByFriendId = userByFriendId;
	}

}
