package com.runatrip.ejb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
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

	private String info;
	
	@Column(name = "lat")
	private double lat;
	@Column(name = "lon")
	private double lon;
	
	public Place(String title, String address) {
		this.title = title;
		this.address = address;
		
		this.info = "Э́йфелева ба́шня (фр. la tour Eiffel, МФА (фр.): [la tu.ʁ‿ɛ.fɛl]) — металлическая башня в центре г. Парижа, самая узнаваемая его архитектурная достопримечательность; также всемирно известна как символ Франции. Этот (впоследствии) символ Парижа был построен в 1889 году и первоначально задумывался как временное сооружение — башня служила входной аркой парижской Всемирной выставки 1889 года. От планировавшегося сноса (через 20 лет после выставки) башню спасли радиоантенны, установленные на самом верху, — пришла эпоха внедрения радио. Названа в честь своего конструктора Гюстава Эйфеля; сам Эйфель называл её просто — 300-метровой башней (tour de 300 mètres). В 2006 году на башне побывало 6 719 200 человек, а за всю её историю, до 31 декабря 2007, — 236 445 812 человек[1]. Таким образом, башня является самой посещаемой[2] и самой фотографируемой[3] достопримечательностью мира.";
	}
	
	public Place(String title, String address, String info) {
		this.title = title;
		this.address = address;
		this.info = info;
	}

	public Place() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
