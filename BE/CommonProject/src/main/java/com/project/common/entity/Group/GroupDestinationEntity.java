package com.project.common.entity.Group;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_destination")
public class GroupDestinationEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gd_seq")
	private int gdSeq;
	
	@Column(name="heritage_seq")
    private int heritageSeq;
	
	@Column(name = "gd_completed")
    private char gdCompleted;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;
	



}
