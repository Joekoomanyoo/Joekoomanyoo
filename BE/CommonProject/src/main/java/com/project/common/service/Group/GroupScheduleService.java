package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.Request.ReqGroupScheduleDto;
import com.project.common.dto.Group.Response.ResGroupScheduleDto;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Group.GroupScheduleEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Group.GroupScheduleRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.FirebaseCloudMessageService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupScheduleService{
	private final GroupRepository groupRepository;
	private final GroupScheduleRepository groupScheduleRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserRepository userRepository;
	private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;
	
	
	//일정 조회
	public List<ResGroupScheduleDto> getGroupSchedule(int groupSeq){
		GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
		List<ResGroupScheduleDto> list = new ArrayList<>();
		for(GroupScheduleEntity entity : group.getSchedules()) {
			list.add(new ResGroupScheduleDto(entity));
		}
		return list;
	}

	//내 모임 일정 조회
	public List<ResGroupScheduleDto> getMyGroupSchedule (String userId){
		List<ResGroupScheduleDto> mySchedule= new ArrayList<>();
		UserEntity user = userRepository.findByUserId(userId);
		
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==user.getUserSeq()) {
				for(int i=0; i<entity.getGroup().getSchedules().size();i++) {
					mySchedule.add(new ResGroupScheduleDto(entity.getGroup().getSchedules().get(i)));	
				}
			}
		}
		return mySchedule;
	}
	//일정 등록
	@Transactional
	public String createGroupSchedule(int groupSeq, ReqGroupScheduleDto gsDto) {
		GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
		if(group==null)
			return "Fail - No group";
		group.addGroupSchedule(GroupScheduleEntity.builder()
				.gsContent(gsDto.getGsContent())
				.gsDateTime(gsDto.getGsDateTime())
				.gsRegisteredAt(new Date())
				.gsUpdatedAt(new Date())
				.build());
		groupRepository.save(group);
		
		
		//알림 받을 인원들
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if (entity.getGroup().getGroupSeq() == groupSeq) {
				UserEntity userEntity = userRepository.findByUserSeq(entity.getUserSeq());

				String fcmToken = userEntity.getFcmToken();
				String title = "일정 추가 알림";
				String body = userEntity.getUserNickname() + "님의 모임에서 일정이 추가되었습니다";
				try {
					firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				// 모임 기록하기
				FcmHistoryDto fcmHistoryDto = FcmHistoryDto.builder()
						.pushSeq(0)
						.userSeq(userEntity.getUserSeq())
						.pushTitle(title)
						.pushContent(body)
						.pushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.build();
				fcmTokenController.createHistory(fcmHistoryDto);
			}
		}
		return "Success";
		
	}
	
	//일정 삭제
	@Transactional
	public String deleteGroupSchedule(int groupSeq,int gsSeq ) {
		GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
		if(group==null)
			return "Fail - No group";
		for(int i=0;i<group.getSchedules().size();i++) {
			if(group.getSchedules().get(i).getGsSeq()==gsSeq) {
				groupScheduleRepository.deleteByGsSeq(group.getSchedules().get(i).getGsSeq());
				group.removeGroupSchedule(group.getSchedules().get(i).getGsSeq());
			}
		}
		return "Success";
	}
	
	//-------------------------------------유기된 기능----------------------------------------//
	//일정 수정
	@Transactional
	public String modifyGroupSchedule(int groupSeq, ReqGroupScheduleDto gsDto) {
		int cnt=0;
		for(GroupScheduleEntity entity: groupRepository.findByGroupSeq(groupSeq).getSchedules()) {
			if(entity.getGsDateTime()==gsDto.getGsDateTime()) {
				entity.setGsContent(gsDto.getGsContent());
				entity.setGsUpdatedAt(new Date());
				groupScheduleRepository.save(entity);
				cnt++;break;
			}
		}
		if(cnt==0)
			return "Fail";	
		return "Success";
	}
}
