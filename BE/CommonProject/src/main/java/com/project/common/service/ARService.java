package com.project.common.service;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.AR.*;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.entity.AR.StampCategoryEntity;
import com.project.common.dto.AR.UserStampRankDto;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.AR.MyStampMapper;
import com.project.common.mapper.AR.StampCategoryMapper;
import com.project.common.mapper.AR.StampMapper;
import com.project.common.entity.AR.MyStampEntity;
import com.project.common.entity.AR.StampEntity;
import com.project.common.repository.AR.ARRepository;
import com.project.common.repository.AR.ARRepositoryCustom;
import com.project.common.repository.AR.MyARRepository;
import com.project.common.repository.AR.StampCategoryRepository;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ARService {
    private final ARRepository arRepository;

    private final UserRepository userRepository;

    private final MyARRepository myARRepository;
    private final HeritageRepository heritageRepository;
    private final ARRepositoryCustom arRepositoryCustom;

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenController fcmTokenController;
    private final StampCategoryRepository stampCategoryRepository;

    // 지역 위도, 경도
    public Map Local(){
        Map<String, double[]> map = new HashMap<String, double[]>();
        map.put("서울특별시", new double[]{37.540705, 126.956764});
        map.put("인천광역시", new double[]{37.469221, 126.573234});
        map.put("광주광역시", new double[]{35.126033, 126.831302});
        map.put("대구광역시", new double[]{35.798838, 128.583052});
        map.put("울산광역시", new double[]{35.519301, 129.239078});
        map.put("대전광역시", new double[]{36.321655, 127.378953});
        map.put("부산광역시", new double[]{35.198362, 129.053922});
        map.put("경기도", new double[]{37.567167, 127.190292});
        map.put("강원도", new double[]{37.555837, 128.209315});
        map.put("충청남도", new double[]{36.557229, 126.779757});
        map.put("충청북도", new double[]{36.628503, 127.929344});
        map.put("경상남도", new double[]{35.259787, 128.664734});
        map.put("경상북도", new double[]{36.248647, 128.664734});
        map.put("전라북도", new double[]{35.716705, 127.144185});
        map.put("전라남도", new double[]{34.819400, 126.893113});
        map.put("제주특별자치도", new double[]{33.364805, 126.542671});
        return map;
    }


    // 스탬프 리스트 반환
    public List<StampDto> listStamp() {
        List<StampEntity> list = arRepository.findAll();

        // 리스트가 하나도 없으면 null 리턴
        if (list.size() == 0) {
            return null;
        } else {
            List<StampDto> listDto = new ArrayList<>();
            for (StampEntity stmStampEntity : list) {
                listDto.add(StampMapper.MAPPER.toDto(stmStampEntity));
            }
            return listDto;
        }
    }

    // 내 스탬프 리스트 받아오기
    public List<StampDto> listMyStamp(int userSeq) {
        // 내 스탬프 리스트 받아옴
        List<MyStampEntity> list = myARRepository.findAllByUserSeq(userSeq);

        // 내 스탬프 리스트의 스탬프 번호를 통해 스탬프Dto 받아오기
        List<StampDto> listDto = new ArrayList<>();
        for (MyStampEntity myStampEntity : list) {
            listDto.add(StampMapper.MAPPER.toDto(arRepository.findByStampSeq(myStampEntity.getStampSeq())));
        }
        return listDto;
    }

    // AR Stamp 추가하기 ( 잡음 )
    public boolean plusMyStamp(int userSeq, int stampSeq) {
        // 사용자가 없다면 false
        if (userRepository.findByUserSeq(userSeq) == null) {
            return false;
        }
        // 스탬프가 없다면 false
        StampEntity stampEntity = arRepository.findByStampSeq(stampSeq);
        if (stampEntity == null) {
            return false;
        }

        // 이미 그 사용자가 그 스탬프가 있다면 false
        if(arRepositoryCustom.findByUserSeqAndStampSeq(userSeq,stampSeq) != null){
            return false;
        };

        // 문화유산 번호
        int heritageSeq = stampEntity.getHeritageSeq();

        MyStampDto myStampDto = MyStampDto.builder()
                 .myStampSeq(0)
                .stampSeq(stampSeq)
                .userSeq(userSeq)
                .heritageSeq(heritageSeq)
                .myStampRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();

        myARRepository.save(MyStampMapper.MAPPER.toEntity(myStampDto));
        
        // 스탬프 카운팅 해주기
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        int cnt = userEntity.getMyStampCnt() + 1;
        userEntity.setMyStampCnt(cnt);
        userRepository.save(userEntity);
        
        return true;
    }

    public void createStamp() {
        List<HeritageEntity> list = heritageRepository.findAll();
        for(HeritageEntity heritageEntity : list){
            if(heritageEntity.getStampExist() == 'Y'){
                // 만약 이미 있는 경우 통과
                if(arRepository.findByHeritageSeq(heritageEntity.getHeritageSeq()) == null) {
                    StampDto stampDto = StampDto.builder()
                            .stampSeq(0)
                            .stampImgUrl("")
                            .stampTitle(heritageEntity.getHeritageName())
                            .heritageSeq(heritageEntity.getHeritageSeq())
                            .heritageLocal(heritageEntity.getHeritageLocal())
                            .heritageLng(heritageEntity.getHeritageLng())
                            .heritageLat(heritageEntity.getHeritageLat())
                            .stampCategory(heritageEntity.getHeritageCategory())
                            .build();
                    arRepository.save(StampMapper.MAPPER.toEntity(stampDto));
                }
            }
        }
    }
    
    // 내 위치 따라 근처에 있는 AR 알림
    public List<StampDto> myLocationStampList(MyLocationDto myLocationDto) {
        // 사용자 위도 경도
        int userSeq = myLocationDto.getUserSeq();
        // 위도
        double lat = Double.parseDouble(myLocationDto.getLat());
        // 경도
        double lng = Double.parseDouble(myLocationDto.getLng());

        // 지역 정보 받아오기
        Map<String, double[]> map = Local();

        // 지역으로 한번 거르고
        // 검사할 지역 리스트
        List<String> listLocal = new ArrayList<>();
        // 전체 Map 돌면서 범위안에 있는지 체크 -> 범위안에 있으면 그 지역을 지역 리스트에 넣어주기
        for(Map.Entry<String, double[]> entry : map.entrySet()){
            // 지역이름
            String local = entry.getKey();
            // 위도
            double mapLat = entry.getValue()[0];
            // 경도
            double mapLng = entry.getValue()[1];

            // 내 위치가 어느 지역의 범위안에 있는지 체크
            // 위도, 경도가 범위안에 있는지
            // 범위안에 있으면 list에 지역 추가
            if((mapLat-0.5) <= lat && lat <= (mapLat+0.5) && (mapLng-0.5) <= lng && lng <= (mapLng+0.5)){
                listLocal.add(local);
            }
        }

        // 탐색할 지역으로 DB 검색해서 리스트 가져오기 ( 거리 탐색할 스탬프 )
        List<StampEntity> listStamp = new ArrayList<>();
        for(String local : listLocal){
            // 지역으로 찾은 스탬프들
            List<StampEntity> listStampLocal = arRepository.findByHeritageLocal(local);

            
            // 만약 찾는 지역이 없으면 통과
            if(listStampLocal.size() == 0) continue;

            // 찾는 지역의 스탬프들 다 저장
            for(StampEntity stampEntity : listStampLocal){
                listStamp.add(stampEntity);
            }
        }


        List<StampDto> listDto = new ArrayList<>();

        // 일정 거리 이내에 있는 것들 리스트 반환 ( 거리계산 )
        // 100m 이내 있으면 알림!
        for(StampEntity stampEntity : listStamp){
            double stampLat = Double.parseDouble(stampEntity.getHeritageLat());
            double stampLng = Double.parseDouble(stampEntity.getHeritageLng());
            double dis = distance(lat,lng,stampLat,stampLng, "meter");

            // 100m 안에 있으면 알림 울리기
            if(dis<=100){
                // 만약 이미 잡은 거면 통과
                if(arRepositoryCustom.findByUserSeqAndStampSeq(userSeq, stampEntity.getStampSeq()) != null){
                    continue;
                }

                listDto.add(StampMapper.MAPPER.toDto(stampEntity));

                UserEntity userEntity = userRepository.findByUserSeq(userSeq);
                
                String fcmToken = userEntity.getFcmToken();
                String title = "근처 AR 알림";
                String body = "100m 이내에 " + stampEntity.getStampTitle() + " AR이 있습니다. 잡으세요 !";
                try {
                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 알림 기록하기
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

        return listDto;
    }

    // 거리계산 메서드
    // 유저위도, 유저경도, 스탬프위도, 스탬프경도, 거리단위
    private static double distance(double userLat, double userLng, double stampLat, double stampLng, String unit){
        double theta = userLng - stampLng;
        double dist = Math.sin(deg2rad(userLat)) * Math.sin(deg2rad(stampLat)) + Math.cos(deg2rad(userLat)) * Math.cos(deg2rad(stampLat)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    // 스탬프 카테고리 DB에 저장
    public void stampCategory() {
        String[] list = {"탑", "비", "불교", "공예품", "궁궐", "기록유산", "왕릉", "건축", "종", "기타"};
        // 스탬프에 저장되어 있는 문화유산 리스트를 찾아온다
        List<StampEntity> stampList = arRepository.findAll();
        for(StampEntity stampEntity : stampList) {
            // 문화유산 번호 가져와서
            int heritageSeq = stampEntity.getHeritageSeq();
            // 문화유산 가져온다
            HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageSeq);
            // 그 문화유산의 카테고리를 가져온다
            String category = heritageEntity.getHeritageCategory();
            
            // 문화유산 개수 추가해준다
            for(int i=0; i<list.length; i++){
                if(category.equals(list[i])){
                    StampCategoryEntity stampCategoryEntity = stampCategoryRepository.findByCategoryName(category);
                    int cnt = stampCategoryEntity.getCategoryCnt() + 1;
                    stampCategoryEntity.setCategoryCnt(cnt);
                    stampCategoryRepository.save(stampCategoryEntity);
                    break;
                }
            }
        }
    }

    // 스탬프 카테고리 별 개수 반환 ( 리스트로 )
    public List<StampCategoryDto> stampCategoryCnt() {
        List<StampCategoryEntity> stampCategoryEntityList = stampCategoryRepository.findAll();
        List<StampCategoryDto> list = StampCategoryMapper.MAPPER.toDtoList(stampCategoryEntityList);
        return list;
    }

    public List<MyStampResponseDto> listCategoryMyStamp(int userSeq, int categorySeq) {
        // 먼저 내 스탬프 리스트 받기
        List<MyStampEntity> myStampList = myARRepository.findAllByUserSeq(userSeq);

        // 반환할 리스트
        List<MyStampResponseDto> list = new ArrayList<>();

        for(MyStampEntity myStampEntity : myStampList){
            // 스탬프 번호
            int stampSeq = myStampEntity.getStampSeq();

            // 스탬프의 카테고리 명과 받은 카테고리번호의 카테고리 명이 같으면 List에 추가
            if(arRepository.findByStampSeq(stampSeq).getStampCategory().equals(stampCategoryRepository.findByCategorySeq(categorySeq).getCategoryName())){
                StampDto stampDto = StampMapper.MAPPER.toDto(arRepository.findByStampSeq(stampSeq));

                MyStampResponseDto myStampResponseDto = MyStampResponseDto.builder()
                        .stampTitle(stampDto.getStampTitle())
                        .stampImgUrl("")
                        .stampCategory(stampDto.getStampCategory())
                        .build();

                list.add(myStampResponseDto);
            }
        }

        return list;
    }

    // 사용자 스탬프 순위
    public List<UserStampRankDto> userStampRank() {
        List<UserStampRankDto> list = new ArrayList<>();
        List<UserEntity> userList = userRepository.findAll(Sort.by(Sort.Direction.DESC,"myStampCnt"));
        int idx = 1;
        // 처음 값의 스탬프 개수를 받는다.
        int before_cnt = userList.get(0).getMyStampCnt();
        // 처음 user를 list에 저장
        list.add(UserStampRankDto.builder()
                .userNickname(userList.get(0).getUserNickname())
                .myStampCnt(before_cnt)
                .userRank(idx)
                .build());

        for(int i=1; i<userList.size(); i++){
            int cnt = userList.get(i).getMyStampCnt();
            // 만약 그 전값이랑 다르면 순위 추가 ( 스탬프 개수가 )
            if(cnt != before_cnt){
                idx++;
            }
            before_cnt = cnt;
            UserStampRankDto userStampRankDto = UserStampRankDto.builder()
                    .userNickname(userList.get(i).getUserNickname())
                    .myStampCnt(cnt)
                    .userRank(idx)
                    .profileImgUrl(userList.get(i).getProfileImgUrl())
                    .build();
            list.add(userStampRankDto);
        }
        return list;
    }
}
