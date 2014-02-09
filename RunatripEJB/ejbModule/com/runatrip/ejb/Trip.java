package com.runatrip.ejb;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "trip")
@XmlRootElement
@NamedQueries({
//		@NamedQuery(name = "Notice.findComments", query = "SELECT c FROM Comment c WHERE c.notice.id = :id"),
//		@NamedQuery(name = "Notice.findByCategory", query = "SELECT n FROM Notice n WHERE n.category.id = :id"),
//		@NamedQuery(name = "Notice.findByUser", query = "SELECT n FROM Notice n WHERE n.user.id = :id")
		})
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = false)
	protected Long id;


	@Column(name = "city")
	private String city;
	
	@Column
	@OneToMany
	private List<Place> places;
	
	@Column
	private String url;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
