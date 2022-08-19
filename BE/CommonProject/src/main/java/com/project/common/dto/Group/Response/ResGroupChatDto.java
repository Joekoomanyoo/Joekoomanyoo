package com.project.common.dto.Group.Response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.common.entity.Group.GroupChatEntity;
import com.project.common.entity.User.UserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResGroupChatDto {

	private int chatSeq;
	    
	private int groupSeq;
	
	private int userSeq;

	private String chatContent;
	    
	private String chatImgUrl;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;

	private String sender;
	
	private String userImg;
	
  public ResGroupChatDto(GroupChatEntity entity, UserEntity user) {
		this.chatSeq = entity.getChatSeq();
		this.groupSeq = entity.getGroupSeq();
		this.userSeq = entity.getUserSeq();
		this.chatContent = entity.getChatContent();
		this.chatImgUrl = entity.getChatImgUrl();
		this.sender = user.getUserNickname();
		this.userImg = user.getProfileImgUrl();
		this.createdTime=entity.getCreatedTime();
	}
}
