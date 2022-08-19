package com.project.common.service.Group;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.Response.ResGroupDestinationDto;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.service.FirebaseCloudMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.repository.Group.GroupDestinationRepository;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDestinationService{

	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final HeritageRepository heritageRepository;
	private final GroupDestinationRepository groupDestinationRepository;
	private final UserRepository userRepository;
	private final FirebaseCloudMessageService firebaseCloudMessageService;
	private final FcmTokenController fcmTokenController;
	

	//내 모임 목적지 조회
	public List<ResGroupDestinationDto> getMyDestination(String userId){
		UserEntity user = userRepository.findByUserId(userId);	
		List<ResGroupDestinationDto> destinationList= new ArrayList<>();
		
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==user.getUserSeq()) {
				for(int i=0; i<entity.getGroup().getDestinations().size();i++) {
					HeritageEntity heritage = heritageRepository.findByHeritageSeq(entity.getGroup().getDestinations().get(i).getHeritageSeq());
					if(heritage!=null)
						destinationList.add(new ResGroupDestinationDto(entity.getGroup().getDestinations().get(i),heritage));
				}
			}
		}
		return destinationList;
	}
	
	//모임 목적지 조회
	public List<ResGroupDestinationDto> getGroupDestination(int groupSeq){
		List<ResGroupDestinationDto> list = new ArrayList<>();
		HeritageEntity herritage;
		for(GroupDestinationEntity entity : groupRepository.findByGroupSeq(groupSeq).getDestinations()) {
			herritage=heritageRepository.findByHeritageSeq(entity.getHeritageSeq());
			if(herritage!=null)
				list.add(new ResGroupDestinationDto(entity,herritage));
		}
		return list;
	}
	
	//모임 목적지 추가
	@Transactional
	public String addGroupDestination(int groupSeq, int heritageSeq) {
		GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
		for(GroupDestinationEntity entity:group.getDestinations()) {
			if(entity!=null&&entity.getHeritageSeq()==heritageSeq) {
				return "Fail - Already Registered";
			}
		}
		group.addGroupDestination(GroupDestinationEntity.builder().gdCompleted('N').heritageSeq(heritageSeq).build());
		groupRepository.save(group);
		
		//FCM 알림
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if (entity.getGroup().getGroupSeq() == groupSeq) {
				UserEntity userEntity = userRepository.findByUserSeq(entity.getUserSeq());

				String fcmToken = userEntity.getFcmToken();
				String title = "목적지 추가 알림";
				String body = userEntity.getUserNickname() + "님의 모임에서 목적지가 추가되었습니다";
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
	
	//모임 목적지 삭제
	@Transactional
	public String deleteGroupDestination(int groupSeq, int heritageSeq) {
		GroupEntity group = groupRepository.findByGroupSeq(groupSeq);
		if(group==null)
			return "Fail - group not exist";
		for(int i=0;i<group.getDestinations().size();i++) {
			if(group.getDestinations().get(i).getHeritageSeq()==heritageSeq) {
				groupDestinationRepository.deleteByGdSeq(group.getDestinations().get(i).getGdSeq());
				group.removeGroupDestination(group.getDestinations().get(i).getGdSeq());
			}
		}
		return "Success";
	}
	
	//모임 목적지 완료 표시
	@Transactional
	public String completeGroupDestination(int groupSeq,int heritageSeq) {
		for(GroupDestinationEntity entity: groupRepository.findByGroupSeq(groupSeq).getDestinations()) {
			if(entity.getHeritageSeq()==heritageSeq) {
				entity.setGdCompleted('Y');
				groupDestinationRepository.save(entity);
				return "Success";
			}
		}
		return "Fail";
	}
	
}
