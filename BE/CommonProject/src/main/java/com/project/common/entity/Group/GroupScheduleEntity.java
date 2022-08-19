package com.project.common.entity.Group;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_schedule")
public class GroupScheduleEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gs_seq")
	private int gsSeq;
	
	@Column(name = "gs_content")
    private String gsContent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gs_date_time")
	private Date gsDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gs_registered_at")
	private Date gsRegisteredAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "gs_updated_at")
    private Date gsUpdatedAt;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;

}
