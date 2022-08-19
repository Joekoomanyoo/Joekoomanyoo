package com.project.common.service;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Group.Response.ResGroupEvalDto;
import com.project.common.dto.My.MyDailyMemoDto;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.mapper.My.MyDailyMemoMapper;
import com.project.common.dto.My.MyScheduleDto;
import com.project.common.mapper.My.MyScheduleMapper;
import com.project.common.dto.User.UserEvalDto;
import com.project.common.dto.User.UserKeywordDto;
import com.project.common.mapper.User.UserKeywordMapper;
import com.project.common.dto.User.UserResponseEvalDto;
import com.project.common.entity.My.MyDailyMemoEntity;
import com.project.common.entity.My.MyScheduleEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.entity.User.UserKeywordEntity;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupMemberRepositoryCustom;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.My.MyDailyMemoRepository;
import com.project.common.repository.My.MyDailyMemoRepositoryCustom;
import com.project.common.repository.My.MyScheduleRepository;
import com.project.common.repository.My.MyScheduleRepositoryCustom;
import com.project.common.repository.User.UserEvalRepository;
import com.project.common.repository.User.UserKeywordRepository;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
    private final UserKeywordRepository userKeywordRepository;
    private final UserEvalRepository userEvalRepository;
    private final MyDailyMemoRepositoryCustom myDailyMemoRepositoryCustom;
    private final MyDailyMemoRepository myDailyMemoRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final MyScheduleRepositoryCustom myScheduleRepositoryCustom;

    private final UserRepository userRepository;
    private final GroupMemberRepositoryCustom groupMemberRepositoryCustom;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;




    // 키워드 생성
    public boolean createKeyword(UserKeywordDto userKeywordDto) {
        UserKeywordEntity userKeywordEntity = UserKeywordMapper.MAPPER.toEntity(userKeywordDto);

        // 만약 이미 키워드가 존재하는 경우
        if(userKeywordEntity == null){
            return false;
        }else{
            // 현재 날짜 , 인덱스 주기
            userKeywordEntity.setMyKeywordRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            userKeywordEntity.setMyKeywordSeq(0);
            userKeywordRepository.save(userKeywordEntity);
            return true;
        }
    }

    // 키워드 리스트 보여주기
    public List<UserKeywordDto> listKeyword(int userSeq) {

        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // 사용자가 없는 경우
        if (userEntity == null) {
            return null;
        } else {

            List<UserKeywordDto> listDto = new ArrayList<>();

            List<UserKeywordEntity> list = userKeywordRepository.findAllByUserSeq(userSeq);
            for (UserKeywordEntity userKeywordEntity : list) {
                listDto.add(UserKeywordMapper.MAPPER.toDto(userKeywordEntity));
            }

            return listDto;
        }
    }

    // 키워드 삭제
    public boolean deleteKeyword(int myKeywordSeq) {
        // 키워드가 없는 경우
        if(userKeywordRepository.findByMyKeywordSeq(myKeywordSeq)==null){
            return false;
        }else {
            userKeywordRepository.deleteByMyKeywordSeq(myKeywordSeq);
            return true;
        }
    }

    // 데일리 메모 생성
    public boolean createDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        // 이미 메모가 있으면
        if(myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate()) != null){
            return false;
        }else {
            myDailyMemoDto.setMyDailyMemoRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            myDailyMemoDto.setMyDailyMemoUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            MyDailyMemoEntity myDailyMemoEntity = MyDailyMemoMapper.MAPPER.toEntity(myDailyMemoDto);
            myDailyMemoRepository.save(myDailyMemoEntity);
            return true;
        }
    }

    // 데일리 메모 보여주기
    public MyDailyMemoDto showDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity = myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // 데일리 메모가 없는 경우
        if(myDailyMemoEntity == null){
            return null;
        }else{
            return MyDailyMemoMapper.MAPPER.toDto(myDailyMemoEntity);
        }
    }

    // 데일리 메모 수정하기
    public boolean modifyDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity =myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // 메모가 없으면
        if(myDailyMemoEntity == null){
            return false;
        }else {
            // 메모 내용 수정
            myDailyMemoEntity.setMyDailyMemo(myDailyMemoDto.getMyDailyMemo());
            myDailyMemoEntity.setMyDailyMemoUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            myDailyMemoRepository.save(myDailyMemoEntity);
            return true;
        }
    }

    // 데일리메모 삭제하기
    public boolean deleteDailyMemo(int myDailyMemoSeq) {
        // 만약 메모가 없는 경우
        if(myDailyMemoRepository.findByMyDailyMemoSeq(myDailyMemoSeq) == null){
            return false;
        }else {
            myDailyMemoRepository.deleteByMyDailyMemoSeq(myDailyMemoSeq);
            return true;
        }
    }

    // 일정 생성하기
    public boolean createSchedule(MyScheduleDto myScheduleDto) {
        // 사용자가 없는 경우 false
        if(userRepository.findByUserSeq(myScheduleDto.getUserSeq()) == null){
            return false;
        }else {
            MyScheduleEntity myScheduleEntity = MyScheduleMapper.MAPPER.toEntity(myScheduleDto);
            // 시간 등록
            myScheduleEntity.setMyScheduleRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            myScheduleEntity.setMyScheduleUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            myScheduleRepository.save(myScheduleEntity);
            return true;
        }
    }

    // 일정 리스트 보여주기
    public List<MyScheduleDto> listSchedule(int userSeq) {
        List<MyScheduleEntity> list = myScheduleRepositoryCustom.findByUserSeq(userSeq);
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // 사용자가 없을 때
        if(userEntity==null){
            return null;
        }else {
            List<MyScheduleDto> listDto = new ArrayList<>();
            for (MyScheduleEntity myScheduleEntity : list) {
                listDto.add(MyScheduleMapper.MAPPER.toDto(myScheduleEntity));
            }
            return listDto;
        }
    }

//    // 일정 수정하기
//    public boolean modifySchedule(MyScheduleDto myScheduleDto) {
//        // 그전 시간 값 구하기
//        int beforeTime = myScheduleRepository.findByMyScheduleSeq(myScheduleDto.getMyScheduleSeq()).getMyScheduleTime();
//
//        // 일정 체크 ( 날짜, 시간, 사용자 이용해서 )
//        MyScheduleEntity myScheduleEntity = myScheduleRepositoryCustom.findByUserSeqAndMyScheduleDateAndMyScheduleTime(myScheduleDto.getUserSeq(), myScheduleDto.getMyScheduleDate(), beforeTime);
//        // 일정이 없는 경우
//        if(myScheduleEntity==null){
//            return false;
//        }else{
//            // 시간 설정
//            myScheduleEntity.setMyScheduleTime(myScheduleDto.getMyScheduleTime());
//            // 일정 내용 설정
//            myScheduleEntity.setMyScheduleContent(myScheduleDto.getMyScheduleContent());
//            // 업데이트 시간 설정
//            myScheduleEntity.setMyScheduleUpdatedAt(time);
//            // 날짜는 변경 불가능
//
//            // 저장
//            myScheduleRepository.save(myScheduleEntity);
//            return true;
//        }
//    }

    // 일정 삭제하기
    public boolean deleteSchedule(int myScheduleSeq) {
        // 스케쥴이 없다면 false
        if(myScheduleRepository.findByMyScheduleSeq(myScheduleSeq) == null){
            return false;
        }else{
            myScheduleRepository.deleteByMyScheduleSeq(myScheduleSeq);
            return true;
        }
    }

    // 상호평가
    public boolean evalMutual(List<UserEvalDto> userEvalDtoList) {
        // 만약 한명도 상호평가 안했을 경우
        if(userEvalDtoList.size() == 0){
            return true;
        }

        // 상호평가 한 사람
        int userSeq = userEvalDtoList.get(0).getUserSeq();

        // 상호평가 한 그룹
        int groupSeq = userEvalDtoList.get(0).getGroupSeq();


        for(UserEvalDto userEvalDto : userEvalDtoList) {
            // 상호평가 받은 사람
            int userReceivedSeq = userEvalDto.getUserReceivedSeq();
            System.out.println("평가 받은 사람 : " + userReceivedSeq);
            System.out.println("평가 하는 사람 : " + userSeq);
            System.out.println("평가 그룹 : " + groupSeq);


            // 상호 평가 받은 사람 먼저 사용자 Entity 가져오기 ( 사용자 번호로 )
            UserEntity userEntity = userEvalRepository.getByUserSeq(userReceivedSeq);
            System.out.println("상호평가 받ㅇ느 살마 이름 : " + userEntity.getUserNickname());

            // Entity에 상호평가 값 넣기
            // 먼저 총 횟수 카운팅
            int cnt = userEntity.getEvalCnt() + 1;
            userEntity.setEvalCnt(cnt);

            // 리스트 값들 카운팅 해주기
            // 기존 값 + 이번에 들어온 값
            userEntity.setEvalList1((userEntity.getEvalList1() + userEvalDto.getEvalList1()));
            userEntity.setEvalList2((userEntity.getEvalList2() + userEvalDto.getEvalList2()));
            userEntity.setEvalList3((userEntity.getEvalList3() + userEvalDto.getEvalList3()));
            userEntity.setEvalList4((userEntity.getEvalList4() + userEvalDto.getEvalList4()));
            userEntity.setEvalList5((userEntity.getEvalList5() + userEvalDto.getEvalList5()));

            // 시간 등록
            userEntity.setEvalUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            userRepository.save(userEntity);
        }

        // 상호평가 했다고 표시해주기
        GroupMemberEntity groupMemberEntity = groupMemberRepositoryCustom.findByUserSeqAndGroupSeq(userSeq, groupSeq);
        groupMemberEntity.setMemberIsEvaluated('Y');

        groupMemberRepository.save(groupMemberEntity);

        return true;
        }

    // 상호평가 가져오기
    public UserResponseEvalDto evalMutualInfo(int userSeq) {
        UserEntity userEntity = userEvalRepository.findByUserSeq(userSeq);

        // 만약 사용자가 없다면
        if(userEntity == null){
            return null;
        }else{
            UserResponseEvalDto userResponseEvalDto = new UserResponseEvalDto();

            // DB에 있는 점수들 가져와서 계산해서 응답 DTO에 저장하기
            int cnt = userEntity.getEvalCnt();

            return userResponseEvalDto.builder().userSeq(userEntity.getUserSeq())
                    .evalCnt(cnt)
                    .evalList1(userEntity.getEvalList1() * 100 / cnt)
                    .evalList2(userEntity.getEvalList2() * 100 / cnt)
                    .evalList3(userEntity.getEvalList3() * 100 / cnt)
                    .evalList4(userEntity.getEvalList4() * 100 / cnt)
                    .evalList5(userEntity.getEvalList5() * 100 / cnt).build();
        }
    }

    public List<ResGroupEvalDto> groupEvalInfo(int userSeq, int groupSeq) {
        // 반환할 리스트
        List<ResGroupEvalDto> listDto = new ArrayList<>();

        GroupMemberEntity groupMemberEntity = groupMemberRepositoryCustom.findByUserSeqAndGroupSeq(userSeq, groupSeq);
        if(groupMemberEntity == null){
            return null;
        }
        GroupEntity groupEntity = groupRepository.findByGroupSeq(groupSeq);

        // 그룹 원들 정보 가져오기
        List<GroupMemberEntity> list = groupMemberRepository.findByGroup(groupEntity);
        for(GroupMemberEntity groupMemberEntity1 : list){
            int groupUserSeq = groupMemberEntity1.getUserSeq();
            // 본인인 경우 통과
            if(userSeq == groupUserSeq) continue;

            // 유저 정보 찾기
            UserEntity userEntity = userRepository.findByUserSeq(groupUserSeq);
            String userNickname = userEntity.getUserNickname();
            String profileImgUrl = userEntity.getProfileImgUrl();

            ResGroupEvalDto resGroupEvalDto = ResGroupEvalDto.builder()
                    .userSeq(groupUserSeq)
                    .groupSeq(groupSeq)
                    .userNickname(userNickname)
                    .profileImgUrl(profileImgUrl)
                    .build();

            listDto.add(resGroupEvalDto);
        }

        return listDto;
    }
}