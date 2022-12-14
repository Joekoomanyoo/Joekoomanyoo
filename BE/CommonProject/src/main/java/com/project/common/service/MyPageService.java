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




    // ????????? ??????
    public boolean createKeyword(UserKeywordDto userKeywordDto) {
        UserKeywordEntity userKeywordEntity = UserKeywordMapper.MAPPER.toEntity(userKeywordDto);

        // ?????? ?????? ???????????? ???????????? ??????
        if(userKeywordEntity == null){
            return false;
        }else{
            // ?????? ?????? , ????????? ??????
            userKeywordEntity.setMyKeywordRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            userKeywordEntity.setMyKeywordSeq(0);
            userKeywordRepository.save(userKeywordEntity);
            return true;
        }
    }

    // ????????? ????????? ????????????
    public List<UserKeywordDto> listKeyword(int userSeq) {

        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // ???????????? ?????? ??????
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

    // ????????? ??????
    public boolean deleteKeyword(int myKeywordSeq) {
        // ???????????? ?????? ??????
        if(userKeywordRepository.findByMyKeywordSeq(myKeywordSeq)==null){
            return false;
        }else {
            userKeywordRepository.deleteByMyKeywordSeq(myKeywordSeq);
            return true;
        }
    }

    // ????????? ?????? ??????
    public boolean createDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        // ?????? ????????? ?????????
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

    // ????????? ?????? ????????????
    public MyDailyMemoDto showDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity = myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // ????????? ????????? ?????? ??????
        if(myDailyMemoEntity == null){
            return null;
        }else{
            return MyDailyMemoMapper.MAPPER.toDto(myDailyMemoEntity);
        }
    }

    // ????????? ?????? ????????????
    public boolean modifyDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity =myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // ????????? ?????????
        if(myDailyMemoEntity == null){
            return false;
        }else {
            // ?????? ?????? ??????
            myDailyMemoEntity.setMyDailyMemo(myDailyMemoDto.getMyDailyMemo());
            myDailyMemoEntity.setMyDailyMemoUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            myDailyMemoRepository.save(myDailyMemoEntity);
            return true;
        }
    }

    // ??????????????? ????????????
    public boolean deleteDailyMemo(int myDailyMemoSeq) {
        // ?????? ????????? ?????? ??????
        if(myDailyMemoRepository.findByMyDailyMemoSeq(myDailyMemoSeq) == null){
            return false;
        }else {
            myDailyMemoRepository.deleteByMyDailyMemoSeq(myDailyMemoSeq);
            return true;
        }
    }

    // ?????? ????????????
    public boolean createSchedule(MyScheduleDto myScheduleDto) {
        // ???????????? ?????? ?????? false
        if(userRepository.findByUserSeq(myScheduleDto.getUserSeq()) == null){
            return false;
        }else {
            MyScheduleEntity myScheduleEntity = MyScheduleMapper.MAPPER.toEntity(myScheduleDto);
            // ?????? ??????
            myScheduleEntity.setMyScheduleRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            myScheduleEntity.setMyScheduleUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            myScheduleRepository.save(myScheduleEntity);
            return true;
        }
    }

    // ?????? ????????? ????????????
    public List<MyScheduleDto> listSchedule(int userSeq) {
        List<MyScheduleEntity> list = myScheduleRepositoryCustom.findByUserSeq(userSeq);
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // ???????????? ?????? ???
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

//    // ?????? ????????????
//    public boolean modifySchedule(MyScheduleDto myScheduleDto) {
//        // ?????? ?????? ??? ?????????
//        int beforeTime = myScheduleRepository.findByMyScheduleSeq(myScheduleDto.getMyScheduleSeq()).getMyScheduleTime();
//
//        // ?????? ?????? ( ??????, ??????, ????????? ???????????? )
//        MyScheduleEntity myScheduleEntity = myScheduleRepositoryCustom.findByUserSeqAndMyScheduleDateAndMyScheduleTime(myScheduleDto.getUserSeq(), myScheduleDto.getMyScheduleDate(), beforeTime);
//        // ????????? ?????? ??????
//        if(myScheduleEntity==null){
//            return false;
//        }else{
//            // ?????? ??????
//            myScheduleEntity.setMyScheduleTime(myScheduleDto.getMyScheduleTime());
//            // ?????? ?????? ??????
//            myScheduleEntity.setMyScheduleContent(myScheduleDto.getMyScheduleContent());
//            // ???????????? ?????? ??????
//            myScheduleEntity.setMyScheduleUpdatedAt(time);
//            // ????????? ?????? ?????????
//
//            // ??????
//            myScheduleRepository.save(myScheduleEntity);
//            return true;
//        }
//    }

    // ?????? ????????????
    public boolean deleteSchedule(int myScheduleSeq) {
        // ???????????? ????????? false
        if(myScheduleRepository.findByMyScheduleSeq(myScheduleSeq) == null){
            return false;
        }else{
            myScheduleRepository.deleteByMyScheduleSeq(myScheduleSeq);
            return true;
        }
    }

    // ????????????
    public boolean evalMutual(List<UserEvalDto> userEvalDtoList) {
        // ?????? ????????? ???????????? ????????? ??????
        if(userEvalDtoList.size() == 0){
            return true;
        }

        // ???????????? ??? ??????
        int userSeq = userEvalDtoList.get(0).getUserSeq();

        // ???????????? ??? ??????
        int groupSeq = userEvalDtoList.get(0).getGroupSeq();


        for(UserEvalDto userEvalDto : userEvalDtoList) {
            // ???????????? ?????? ??????
            int userReceivedSeq = userEvalDto.getUserReceivedSeq();
            System.out.println("?????? ?????? ?????? : " + userReceivedSeq);
            System.out.println("?????? ?????? ?????? : " + userSeq);
            System.out.println("?????? ?????? : " + groupSeq);


            // ?????? ?????? ?????? ?????? ?????? ????????? Entity ???????????? ( ????????? ????????? )
            UserEntity userEntity = userEvalRepository.getByUserSeq(userReceivedSeq);
            System.out.println("???????????? ????????? ?????? ?????? : " + userEntity.getUserNickname());

            // Entity??? ???????????? ??? ??????
            // ?????? ??? ?????? ?????????
            int cnt = userEntity.getEvalCnt() + 1;
            userEntity.setEvalCnt(cnt);

            // ????????? ?????? ????????? ?????????
            // ?????? ??? + ????????? ????????? ???
            userEntity.setEvalList1((userEntity.getEvalList1() + userEvalDto.getEvalList1()));
            userEntity.setEvalList2((userEntity.getEvalList2() + userEvalDto.getEvalList2()));
            userEntity.setEvalList3((userEntity.getEvalList3() + userEvalDto.getEvalList3()));
            userEntity.setEvalList4((userEntity.getEvalList4() + userEvalDto.getEvalList4()));
            userEntity.setEvalList5((userEntity.getEvalList5() + userEvalDto.getEvalList5()));

            // ?????? ??????
            userEntity.setEvalUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            userRepository.save(userEntity);
        }

        // ???????????? ????????? ???????????????
        GroupMemberEntity groupMemberEntity = groupMemberRepositoryCustom.findByUserSeqAndGroupSeq(userSeq, groupSeq);
        groupMemberEntity.setMemberIsEvaluated('Y');

        groupMemberRepository.save(groupMemberEntity);

        return true;
        }

    // ???????????? ????????????
    public UserResponseEvalDto evalMutualInfo(int userSeq) {
        UserEntity userEntity = userEvalRepository.findByUserSeq(userSeq);

        // ?????? ???????????? ?????????
        if(userEntity == null){
            return null;
        }else{
            UserResponseEvalDto userResponseEvalDto = new UserResponseEvalDto();

            // DB??? ?????? ????????? ???????????? ???????????? ?????? DTO??? ????????????
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
        // ????????? ?????????
        List<ResGroupEvalDto> listDto = new ArrayList<>();

        GroupMemberEntity groupMemberEntity = groupMemberRepositoryCustom.findByUserSeqAndGroupSeq(userSeq, groupSeq);
        if(groupMemberEntity == null){
            return null;
        }
        GroupEntity groupEntity = groupRepository.findByGroupSeq(groupSeq);

        // ?????? ?????? ?????? ????????????
        List<GroupMemberEntity> list = groupMemberRepository.findByGroup(groupEntity);
        for(GroupMemberEntity groupMemberEntity1 : list){
            int groupUserSeq = groupMemberEntity1.getUserSeq();
            // ????????? ?????? ??????
            if(userSeq == groupUserSeq) continue;

            // ?????? ?????? ??????
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