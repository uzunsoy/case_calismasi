package com.uzunsoy.model;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode 
@NoArgsConstructor 
public class Article extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	 
	
	public Article(String title, String author) {
	 
		this.title = title;
		this.author = author;
	}

	public Article(String title, String articleContent, Integer starCount) {
		 
		this.title = title;
		this.articleContent = articleContent;
		this.starCount = starCount;
	}
	
	public Article(Long id,String title, String articleContent, Integer starCount) {
		this.artId = id; 
		this.title = title;
		this.articleContent = articleContent;
		this.starCount = starCount;
	}
	
	@Id
	@GeneratedValue(strategy = TABLE, generator = "SEQ_ARTICLE_PK")
	@TableGenerator(name = "SEQ_ARTICLE_PK", table = "TABLES_SEQ_LIST", pkColumnName = "TABLE_PK", initialValue = 1, allocationSize = 1)
	private Long artId;

	@Column(unique = true, nullable = false)
	private String title;
	
	private String author;
 
	private String articleContent;

	@Temporal(TemporalType.DATE)
	private Calendar publishDate;

	private Integer starCount;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Review.class)
	private List<Review> reviews;

}
