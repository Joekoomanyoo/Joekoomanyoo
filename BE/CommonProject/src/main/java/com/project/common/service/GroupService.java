package com.project.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupMapper;
import com.project.common.entity.GroupEntity;
import com.project.common.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService{
	private final GroupRepository groupRepository;
	
/////////////////////////////////////////
	//모임 개설
	public GroupDto addGroup(GroupDto groupDto) {
		GroupEntity saved= groupRepository.save(groupDto.toEntity());
		//참가자 목록 등록코드 추가 필요	
		return GroupMapper.MAPPER.toDto(saved);
	}
	
	//전체 목록 조회
	public List<GroupDto> getGroupList(){
		List<GroupEntity> groupList=groupRepository.findAll();
		return GroupMapper.MAPPER.toDtoList(groupList);
	}
	
	//모임 정보 보기
	public GroupDto getGroupInfo(Long groupSeq) {
		GroupEntity groupInfo=groupRepository.findById(groupSeq).orElse(null);
		return GroupMapper.MAPPER.toDto(groupInfo);
	}
	
	//모임 삭제
	public void deleteGroup(Long groupSeq){
		groupRepository.deleteById(groupSeq);
	}
	
	//모임 기본 정보 수정
		public GroupDto updateGroup(Long groupSeq,GroupDto groupDto) {
			GroupEntity oldGroup =groupRepository.findById(groupSeq).orElse(null);
			GroupDto updateGroup=new GroupDto();
			updateGroup=groupDto;
			if(oldGroup != null) {
				updateGroup.setGroupSeq(oldGroup.getGroupSeq());
				updateGroup.setGroupCreatedAt(oldGroup.getGroupCreatedAt());
				groupRepository.save(updateGroup.toEntity());
			}
			return updateGroup;
		}
	
////////////////////////////////////////////
	
//	//내 목록 조회
//	public List<GroupDto> getMyGroupList(long userSeq){
//		// UserEntity user= userService.findById(userSeq);
//		
//		return groupJoinService
//				.findByUser(user).stream()
//				.map(GroupParticipant::getGroup)
//				.map(GroupDto::of)
//				.collect(Collectors.toList());
//	}
	
	
	
//	
//
//	
//	//모임 정보 보기
//	public GroupDto getGroupInfo(Long groupSeq) {
//		GroupEntity group=groupRepository.findById(groupSeq).orElse(null);
//		GroupDto groupDto=GroupMapper.MAPPER.toDto(group);
//		groupDto.setGroupAttributeDto(GroupAttributeMapper.MAPPER.toDto(group.getGroupAttributeEntity()));
//		System.out.println(groupDto.getGroupAttributeDto().getGaAge());
//		return groupDto;
//	}
	

	
//	
//	//모임 참가
//	public String joinGroup(long groupSeq) {
//		GroupEntity selectGroup=groupRepository.findById(groupSeq).orElse(null);
//		GroupParticipantEntity groupParticipant = GroupParticipantEntity.builder()
//				.study(selectGroup)
//				.participant(user)
//				.build();
//		groupPariticipantService.save(groupParticipant)
//	
//	}

}
