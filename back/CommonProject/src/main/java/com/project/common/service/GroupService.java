package com.project.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupAttributeDto;
import com.project.common.dto.GroupAttributeMapper;
import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupMapper;
import com.project.common.entity.GroupAttributeEntity;
import com.project.common.entity.GroupEntity;
import com.project.common.repository.GroupAttributeRepository;
import com.project.common.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class GroupService{
	private final GroupRepository groupRepository;
	private final GroupAttributeRepository groupAttributeRepository;
	
	//전체 목록 조회
	public List<GroupDto> getGroupList(){
		return GroupMapper.MAPPER.toDtoList(groupRepository.findAll());
	}
	
	//모임 개설
	public GroupDto addGroup(GroupDto groupDto) {
		groupRepository.save(groupDto.toEntity());
		return groupDto;
	}

	//모임 기본 정보 수정
	public GroupDto updateGroup(Long groupSeq,GroupDto groupDto) {
		GroupEntity groupUpdated =groupRepository.findById(groupSeq).orElse(null);
		
		if(groupUpdated != null) {
			groupUpdated=groupDto.toEntity();
			groupRepository.save(groupUpdated);
		}
		return GroupMapper.MAPPER.toDto(groupUpdated);
	}
	
	//모임 상세 정보 수정
	public GroupAttributeDto updateGroupAttribute(Long groupSeq,GroupAttributeDto groupAttributeDto) {
		GroupAttributeEntity groupAttributeUpdated = groupAttributeRepository.findById(groupSeq).orElse(null);
		
		if(groupAttributeUpdated != null) {
			groupAttributeUpdated = groupAttributeDto.toEntity();
			groupAttributeRepository.save(groupAttributeUpdated);
		}
		return GroupAttributeMapper.MAPPER.toDto(groupAttributeUpdated);
	}
	
	//모임 기본정보 보기
	public GroupDto getGroupInfo(Long groupSeq) {
		GroupEntity groupInfo=groupRepository.findById(groupSeq).orElse(null);
		return GroupMapper.MAPPER.toDto(groupInfo);
	}
	
	//모임 상세정보 보기
	public GroupAttributeDto getGroupAttribute(Long groupSeq) {
		GroupAttributeEntity groupAttribute=groupAttributeRepository.findById(groupSeq).orElse(null);
		return GroupAttributeMapper.MAPPER.toDto(groupAttribute);
	}
	
	//모임 삭제
	public void deleteGroup(Long groupSeq){
		groupRepository.deleteById(groupSeq);
	}

}
