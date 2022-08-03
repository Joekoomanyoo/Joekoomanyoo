package com.project.common.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.GroupDto;
import com.project.common.dto.GroupJoinRequestDto;
import com.project.common.dto.GroupMapper;
import com.project.common.dto.GroupMemberDto;
import com.project.common.dto.GroupMemberMapper;
import com.project.common.dto.GroupMemberListDto;
import com.project.common.dto.UserDto;
import com.project.common.entity.GroupEntity;
import com.project.common.entity.GroupMemberEntity;
import com.project.common.repository.GroupMemberRepository;
import com.project.common.repository.GroupRepository;



import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupMemberService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserService userService;

	//참가자 목록 
	public List<GroupMemberListDto> getMemberList(long groupSeq){
		List<GroupMemberListDto> list = new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupMemberListDto(entity));
			}
		}
		return list;
	}
	
	//모임 참가
	public GroupMemberDto joinGroup(long groupSeq,GroupJoinRequestDto requestDto) {
		GroupMemberDto info=new GroupMemberDto();
		info.setMemberAppeal(requestDto.getMemberAppeal());
		info.setUserSeq(requestDto.getUserSeq());
		info.setMemberIsEvaluated('N');
		
		GroupEntity group=groupRepository.findById(groupSeq).orElse(null);
		group.addMember(info.toEntity());
		
		GroupMemberEntity saved= groupMemberRepository.save(info.toEntity());

		return GroupMemberMapper.MAPPER.toDto(saved);
	
	}
	
	//모임 삭제
	public void deleteGroup(Long groupSeq){
		groupRepository.deleteById(groupSeq);
	}
	
	@Transactional
    public void withdrawGroup(long groupSeq, UserDto userSignupDto) {
        long userSeq = userSignupDto.getUserSeq();

    //    groupMemberRepository.findByGroupIdAndMemberId(groupSeq, userSeq)
  //              .orElseThrow(GroupMemberNotFoundException::new);
      //          .checkPresenter(userId);

        //가입명단에서 삭제
     //   groupMemberRepository.deleteByGroupIdAndMemberId(groupSeq, userSeq);
    }
}
