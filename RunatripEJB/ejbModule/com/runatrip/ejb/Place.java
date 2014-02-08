package com.runatrip.ejb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "place")
@XmlRootElement
@NamedQueries({
//		@NamedQuery(name = "Notice.findComments", query = "SELECT c FROM Comment c WHERE c.notice.id = :id"),
//		@NamedQuery(name = "Notice.findByCategory", query = "SELECT n FROM Notice n WHERE n.category.id = :id"),
//		@NamedQuery(name = "Notice.findByUser", query = "SELECT n FROM Notice n WHERE n.user.id = :id")
		})
public class Place {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = false)
	protected Long id;

	@Column(name = "name")
	private String title;
	@Column(name = "address")
	private String address;

	@Column(name = "lat")
	private double lat;
	@Column(name = "lon")
	private double lon;

	public Place() {
		super();
	}

}
