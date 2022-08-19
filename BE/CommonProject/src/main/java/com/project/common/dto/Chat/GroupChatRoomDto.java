package com.project.common.dto.Chat;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupChatRoomDto {

    private int groupSeq;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static GroupChatRoomDto create(int groupSeq){
        GroupChatRoomDto chatRoom = new GroupChatRoomDto();
        chatRoom.groupSeq = groupSeq;
        return chatRoom;
    }
}