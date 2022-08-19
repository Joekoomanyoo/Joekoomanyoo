package com.project.common.service.Group;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Chat.GroupChatRoomDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupChatRoomService{

    private Map<Integer, GroupChatRoomDto> chatRoomDTOMap;
    
    
    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }
    
    // 특정 채팅방 조회
    public GroupChatRoomDto getChatRoomByGroupSeq(int groupSeq){
        return chatRoomDTOMap.get(groupSeq);
    }
    
    // 채팅방 생성
    public GroupChatRoomDto createChatRoom(int groupSeq){
    	GroupChatRoomDto room = GroupChatRoomDto.create(groupSeq);
        chatRoomDTOMap.put(room.getGroupSeq(), room);

        return room;
    }

}
