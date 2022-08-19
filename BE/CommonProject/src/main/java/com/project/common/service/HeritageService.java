package com.project.common.service;

import com.project.common.dto.AR.MyLocationDto;
import com.project.common.dto.Heritage.*;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.entity.Heritage.HeritageScrapEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Heritage.HeritageMapper;
import com.project.common.mapper.Heritage.HeritageReviewMapper;
import com.project.common.mapper.Heritage.HeritageScrapMapper;
import com.project.common.repository.Heritage.*;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HeritageService {

    private final HeritageReviewRepository heritageReviewRepository;
    private final HeritageRepository heritageRepository;
    private final HeritageScrapRepository heritageScrapRepository;
    private final HeritageScrapRepositoryCustom heritageScrapRepositoryCustom;

    private final UserRepository userRepository;

    // 문화유산 리스트
    @Transactional
    public List<HeritageDto> listInfo() throws Exception {
        List<HeritageDto> list = new ArrayList<>();
        for (HeritageEntity entity : heritageRepository.findAll()) {
            list.add(HeritageMapper.MAPPER.toDto(entity));
        }
        return list;
    }

    // 리뷰 등록
    @Transactional
    public boolean createReview(HeritageReivewDto heritageReivewDto) {
        UserEntity userEntity = userRepository.findByUserSeq(heritageReivewDto.getUserSeq());
        String nickname = userEntity.getUserNickname();
        HeritageReviewEntity heritageReviewEntity = HeritageReviewMapper.MAPPER.toEntity(heritageReivewDto);
        heritageReviewEntity.setUserNickname(nickname);
        heritageReviewEntity.setProfileImgUrl(userEntity.getProfileImgUrl());

        // 문화유산 번호로 찾기
        HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageReivewDto.getHeritageSeq());

        // 리뷰 개수 하나 늘리기
        int cnt = heritageEntity.getHeritageReviewCnt() + 1;
        // 개수 entity에 세팅하기
        heritageEntity.setHeritageReviewCnt(cnt);

        // 저장
        heritageRepository.save(heritageEntity);

        if (heritageReviewEntity == null) {
            return false;
        } else {
            // 리뷰 저장하기
            heritageReviewRepository.save(heritageReviewEntity);
            return true;
        }
    }

    // 리뷰 삭제
    @Transactional
    public boolean deleteReview(int heritageReviewSeq, int heritageSeq) {
        // 리뷰가 없으면 false
        if(heritageReviewRepository.findByHeritageReviewSeq(heritageReviewSeq) == null){
            return false;
        }else{
            // 리뷰 개수 줄이기 ( 문화유산에 )
            HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageSeq);
            
            // 리뷰 개수 하나 줄이기
            int cnt = heritageEntity.getHeritageReviewCnt() - 1;

            // 개수 entity에 세팅하기
            heritageEntity.setHeritageReviewCnt(cnt);

            // 개수 heritage DB에 저장하기
            heritageRepository.save(heritageEntity);

            // 리뷰 삭제하기
            heritageReviewRepository.deleteByHeritageReviewSeq(heritageReviewSeq);
            return true;
        }
    }

    // 리뷰 리스트 반환
    @Transactional
    public List<HeritageReivewDto> reviewList(int heritageSeq) {
        List<HeritageReviewEntity> list = heritageReviewRepository.findAllByHeritageSeq(heritageSeq);
        List<HeritageReivewDto> listDto= new ArrayList<>();
        for(HeritageReviewEntity heritageReviewEntity : list){
            listDto.add(HeritageReviewMapper.MAPPER.toDto(heritageReviewEntity));
        }
        return listDto;
    }



    // 스크랩 등록
    @Transactional
    public boolean createScrap(HeritageScrapDto heritageScrapDto){
        HeritageScrapEntity heritageScrapEntity = HeritageScrapMapper.MAPPER.toEntity(heritageScrapDto);
        
        heritageScrapEntity.setHeritageScrapSeq(0);
        heritageScrapEntity.setHeritageScrapRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));        // 초기값 입력

        // 스크랩 개수 추가
        HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageScrapEntity.getHeritageSeq());
        // 그전 스크랩 개수에서 하나 더해주기
        int cnt = heritageEntity.getHeritageScrapCnt()+1;
        
        // 개수 바꿔주기
        heritageEntity.setHeritageScrapCnt(cnt);

        // 스크랩 개수 Heritage DB에 저장
        heritageRepository.save(heritageEntity);

        // 스크랩 DB에 저장
        heritageScrapRepository.save(heritageScrapEntity);

        // 올바르게 들어갔으면 true
        if(heritageScrapEntity != null){
            return true;
        }else{
            return false;
        }
    }

    // 유저 scrap 리스트 반환
    @Transactional
    public List<HeritageDto> myScrapList(int userSeq) {
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // 사용자가 없는경우 null 반환
        if (userEntity == null) {
            return null;
        } else {
            // 유저 번호로 스크랩 목록들 가져오기
            // 그 목록들의 번호로 문화유산 DTO에 담아주기
            // 그 리스트를 반환
            List<HeritageScrapEntity> list = heritageScrapRepository.findAllByUserSeq(userSeq);
            List<HeritageDto> listHeritageDto = new ArrayList<>();
            for (HeritageScrapEntity heritageScrapEntity : list) {
                listHeritageDto.add(HeritageMapper.MAPPER.toDto(heritageRepository.findByHeritageSeq(heritageScrapEntity.getHeritageSeq())));
            }
            return listHeritageDto;
        }
    }
    
    // 유저 scrap 삭제
    @Transactional
    public boolean deleteScrap(int userSeq, int heritageSeq) {
        // 값이 없으면 false
        if(heritageScrapRepositoryCustom.findByUserSeqAndHeritageSeq(userSeq, heritageSeq) == null){
            return false;
        }
        else{
            // 스크랩 개수 하나 줄이기
            HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageSeq);
            // 그전 스크랩 개수에서 하나 빼주기
            int cnt = heritageEntity.getHeritageScrapCnt()-1;

            // 개수 바꿔주기
            heritageEntity.setHeritageScrapCnt(cnt);

            // 스크랩 개수 Heritage DB에 저장
            heritageRepository.save(heritageEntity);

            return heritageScrapRepositoryCustom.deleteByUserSeqAndHeritageSeq(userSeq, heritageSeq);
        }
    }

    // 내 위치 기준으로 정렬해서 문화재 리스트 보내주기
    public List<HeritageDto> sortHeritage(MyLocationDto myLocationDto) {
        double lat = Double.parseDouble(myLocationDto.getLat());
        double lng = Double.parseDouble(myLocationDto.getLng());

        List<HeritageEntity> list = heritageRepository.findAll();
        List<HeritageDto> listDto = HeritageMapper.MAPPER.toDtoList(list);
        // 거리 정보 저장
        List<HeritageDto> sortList = new ArrayList<>();

        // 10km, 20km, 30km ... 순으로 증가
        double distance = 0.05;
        while (listDto.size() > 0) {
            int size = listDto.size();
            for (int i = 0; i < size; i++) {
                // 문화재 위치가 위도 경도 다 범위안에 있는 경우
                if (((lat - distance) <= Double.parseDouble(listDto.get(i).getHeritageLat())) && ((lat + distance) >= Double.parseDouble(listDto.get(i).getHeritageLat())) && ((lng - distance) <= Double.parseDouble(listDto.get(i).getHeritageLng())) && ((lng + distance) >= Double.parseDouble(listDto.get(i).getHeritageLng()))) {
                    sortList.add(listDto.get(i));
                    listDto.remove(i);
                    if (i == 0) i = 0;
                    else i--;
                }
                if(listDto.size()==0)break;
                if(i==listDto.size()-1) break;
            }
            distance += 0.05;
        }
            return sortList;
        }


    // 카테고리, 정렬 별로 문화유산 리스트 가져오기
    public List<HeritageDto> categorySortHeritage(SortHeritageDto sortHeritageDto) {
        double lat = Double.parseDouble(sortHeritageDto.getLat());
        double lng = Double.parseDouble(sortHeritageDto.getLng());
        int categorySeq = sortHeritageDto.getCategorySeq();
        int sortSeq = sortHeritageDto.getSortSeq();

        // 번호가 넘을경우 오류
        if(categorySeq >= 11 || sortSeq>=3) return null;

        // 카테고리 배열
        String[] categoryList = {"전체", "탑", "비", "불교", "공예품", "궁궐", "기록유산", "왕릉", "건축", "종", "기타"};

        String category = categoryList[categorySeq];

        List<HeritageEntity> listCategory;
        // 전체리스트 가져오는 경우
        if(categorySeq == 0){
            listCategory= heritageRepository.findAll();
        }else {
            // 나머지
            // 카테고리 별로 정렬하기
            // 카테고리 배열 가져오기
            listCategory = heritageRepository.findAllByHeritageCategory(category);
        }
        // 정렬 별로 정렬하기
        // 카테고리 배열에서 정렬 하기
        // 0 : 거리순 , 1 : 스크랩순 , 2 : 리뷰순
        List<HeritageDto> listDto = HeritageMapper.MAPPER.toDtoList(listCategory);
        List<HeritageDto> listSort = new ArrayList<>();
        switch (sortSeq){
            case 0:
                // 10km, 20km, 30km ... 순으로 증가
                double distance = 0.05;
                while(listDto.size()> 0){
                    int size = listDto.size();
                    for(int i=0; i<size; i++){
                        // 문화재 위치가 위도 경도 다 범위안에 있는 경우
                        if (((lat - distance) <= Double.parseDouble(listDto.get(i).getHeritageLat())) && ((lat + distance) >= Double.parseDouble(listDto.get(i).getHeritageLat())) && ((lng - distance) <= Double.parseDouble(listDto.get(i).getHeritageLng())) && ((lng + distance) >= Double.parseDouble(listDto.get(i).getHeritageLng()))) {
                            listSort.add(listDto.get(i));
                            listDto.remove(i);
                            if (i == 0) i = 0;
                            else i--;
                        }
                        if(listDto.size()==0)break;
                        if(i==listDto.size()-1) break;
                    }
                    distance += 0.05;
                }
                break;
            case 1:
                // 스크랩 수 순으로 정렬하고
                Sort sortScrap = Sort.by(
                        Sort.Order.desc("heritageScrapCnt"),
                        Sort.Order.asc("heritageSeq")
                );
                List<HeritageEntity> listSortScrap = heritageRepository.findAll(sortScrap);
                // 만약 전체일 경우
                if(categorySeq == 0){
                    listSort = HeritageMapper.MAPPER.toDtoList(listSortScrap);
                }

                // 카테고리와 같은 것만 리스트에 저장
                List<HeritageDto> listSortScrapDto = HeritageMapper.MAPPER.toDtoList(listSortScrap);
                for(HeritageDto heritageDto : listSortScrapDto){
                    // 카테고리명과 같으면 추가
                    if(heritageDto.getHeritageCategory().equals(category)){
                        listSort.add(heritageDto);
                    }
                }
                break;
            case 2:
                // 리뷰 수 순으로 정렬하고
                Sort sortReview = Sort.by(
                        Sort.Order.desc("heritageReviewCnt"),
                        Sort.Order.asc("heritageSeq")
                );
                List<HeritageEntity> listSortReview = heritageRepository.findAll(sortReview);
                // 만약 전체일 경우
                if(categorySeq == 0){
                    listSort = HeritageMapper.MAPPER.toDtoList(listSortReview);
                }
                // 카테고리와 같은 것만 리스트에 저장
                List<HeritageDto> listSortReviewDto = HeritageMapper.MAPPER.toDtoList(listSortReview);
                for(HeritageDto heritageDto : listSortReviewDto){
                    // 카테고리명과 같으면 추가
                    if(heritageDto.getHeritageCategory().equals(category)){
                        listSort.add(heritageDto);
                    }
                }
                break;
        }

        return listSort;
    }
}
