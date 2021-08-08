package com.uzunsoy.model;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Review extends BaseEntity implements Serializable{

  
	
	
	public Review(Long l, String string) {
		 this.articleId = l;
		 this.reviewContent=string;
	}

	public Review(String reviewContent, String reviewer) {
		this.reviewContent = reviewContent;
		this.reviewer = reviewer;
	}
	
	public Review(Long artId, String reviewContent, String reviewer) {
		this.reviewContent = reviewContent;
		this.reviewer = reviewer;
		this.articleId = artId;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = TABLE, generator = "SEQ_REVIEW_PK")
	@TableGenerator(name = "SEQ_REVIEW_PK", table = "TABLES_SEQ_LIST", pkColumnName = "TABLE_PK", initialValue = 1, allocationSize = 1)
	private Long revId;

	@Column(nullable = false)
	private Long articleId;
	
	private String reviewer;

	@Column(nullable = false)
	private String reviewContent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Article article;
	 
}
