package com.runatrip.ejb;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "trip")
@XmlRootElement
@NamedQueries({
//		@NamedQuery(name = "Comment.deleteByNoticeId", query = "UPDATE Comment c "
//				+ "SET c.isDeleted = true " + "WHERE c.notice.id = :noticeId"),
//		@NamedQuery(name = "Comment.getComments", query = "SELECT c FROM Comment c WHERE c.notice.id = :noticeId ORDER BY c.id DESC") 
		
})
public class Trip {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = false)
	protected Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "com_parent")
	private List< Place> places;

}
