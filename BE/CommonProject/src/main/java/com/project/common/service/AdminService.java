package com.project.common.service;

import com.project.common.controller.FcmTokenController;
import com.project.common.dto.Admin.AdminReportDto;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.entity.Admin.AdminReportEntity;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Admin.AdminReportMapper;
import com.project.common.repository.Admin.AdminRepository;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.Heritage.HeritageReviewRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.Feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final HeritageReviewRepository heritageReviewRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final HeritageService heritageService;
    private final FeedService feedService;
    private final UserService userService;
    private final HeritageRepository heritageRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FcmTokenController fcmTokenController;

    // 신고 접수 받기
    public boolean reportCreate(AdminReportDto adminReportDto) {
        int type = adminReportDto.getReportType();
        // 신고받은 정보 번호 -> 사용자의 아이디 추출
        int reportTypeSeq = adminReportDto.getReportTypeSeq();

        // 타입을 이상하게 가져올 경우
        if (type > 2) return false;

        AdminReportEntity adminReportEntity = AdminReportMapper.MAPPER.toEntity(adminReportDto);
        adminReportEntity.setIsSolved('N');
        adminReportEntity.setReportDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        switch (type) {
            // 리뷰 신고
            case 0:
                // 리뷰 정보 가져오기
                HeritageReviewEntity heritageReviewEntity = heritageReviewRepository.findByHeritageReviewSeq(reportTypeSeq);

                // 리뷰 있는지 체크
                if (heritageReviewEntity == null) {
                    return false;
                }
                // 리뷰 작성자 아이디
                String reviewUserId = userRepository.findByUserSeq(heritageReviewEntity.getUserSeq()).getUserId();

                // 사용자 있는지 체크
                if (userRepository.findByUserId(reviewUserId).getIsDeleted() == 'Y') {
                    return false;
                }

                adminReportEntity.setUserId(reviewUserId);
                break;

            // 피드 신고
            case 1:
                // 피드 정보 가져오기
                FeedEntity feedEntity = feedRepository.findByFeedSeq(reportTypeSeq);

                // 피드 있는지 체크
                if (feedEntity == null) {
                    return false;
                }
                // 피드 작성자 아이디
                String feedUserId = feedEntity.getUser().getUserId();

                // 사용자 있는지 체크
                if (feedEntity.getUser().getIsDeleted() == 'Y') {
                    return false;
                }

                adminReportEntity.setUserId(feedUserId);
                break;

            // 사용자 신고
            case 2:
                // 사용자 정보 가져오기
                UserEntity userEntity = userRepository.findByUserSeq(reportTypeSeq);

                // 사용자 있는지 체크
                if (userEntity == null || userEntity.getIsDeleted() == 'Y') {
                    return false;
                }
                // 사용자 아이디
                String userId = userEntity.getUserId();
                adminReportEntity.setUserId(userId);
                break;
        }
        // 저장하기
        adminRepository.save(adminReportEntity);
        return true;
    }

    // 신고 리스트
    public List<AdminReportDto> listReport() {
        List<AdminReportEntity> list = adminRepository.findAll();
        List<AdminReportDto> listDto = AdminReportMapper.MAPPER.toDtoList(list);
        return listDto;
    }


    // 신고에 따라 리뷰, 피드, 사용자 탈퇴시키기
    public boolean deleteReport(int reportSeq) {
        AdminReportEntity adminReportEntity = adminRepository.findByReportSeq(reportSeq);

        // 이미 신고가 처리 된 경우 false
        if (adminReportEntity.getIsSolved() == 'Y') {
            return false;
        }


        // 신고 종류
        int type = adminReportEntity.getReportType();

        // 신고 번호
        int reportTypeSeq = adminReportEntity.getReportTypeSeq();

        // 올바르지 않은 경우 false
        if (adminReportEntity == null) {
            return false;
        }

        switch (type) {
            // 리뷰 삭제
            case 0:

                // 리뷰 찾기
                HeritageReviewEntity heritageReviewEntity = heritageReviewRepository.findByHeritageReviewSeq(reportTypeSeq);

                // 만약 리뷰가 없다면 false
                if (heritageReviewEntity == null) {
                    return false;
                }

                // 리뷰 번호, 문화유산 번호
                int heritageReviewSeq = heritageReviewEntity.getHeritageReviewSeq();
                int heritageSeq = heritageReviewEntity.getHeritageSeq();

                // 리뷰 삭제
                heritageService.deleteReview(heritageReviewSeq, heritageSeq);
                break;
            // 피드 삭제    
            case 1:

                // 피드 찾기
                FeedEntity feedEntity = feedRepository.findByFeedSeq(reportTypeSeq);

                // 만약 피드가 없다면 false
                if (feedEntity == null) {
                    return false;
                }

                // 피드 삭제
                feedService.deleteFeed(feedEntity.getUser().getUserId(), reportTypeSeq);
                break;
            // 사용자 삭제
            case 2:

                // 사용자 찾기
                UserEntity userEntity = userRepository.findByUserSeq(reportTypeSeq);

                // 만약 사용자가 없다면 false
                if (userEntity == null) {
                    return false;
                }

                // 사용자 탈퇴 처리
                userService.resignUser(userEntity.getUserId());
                break;
        }

        // 신고 해결 처리
        adminReportEntity.setIsSolved('Y');
        adminRepository.save(adminReportEntity);

        return true;
    }

    public boolean warningAlarm(int reportSeq) {
        AdminReportEntity adminReportEntity = adminRepository.findByReportSeq(reportSeq);

        // 이미 신고가 처리 된 경우 false
        if (adminReportEntity.getIsSolved() == 'Y') {
            return false;
        }

        // 신고 종류
        int type = adminReportEntity.getReportType();

        // 신고 번호
        int reportTypeSeq = adminReportEntity.getReportTypeSeq();

        // 올바르지 않은 경우 false
        if (adminReportEntity == null) {
            return false;
        }

        // 알림 받을 사용자
        UserEntity userEntity = userRepository.findByUserId(adminReportEntity.getUserId());
        String userNickname = userEntity.getUserNickname();

        // 알림에 필요한 정보
        String fcmToken = userEntity.getFcmToken();
        String title = "신고로 인한 경고 알림";
        String body = userNickname + " 님의 ";

        switch (type) {
            // 리뷰
            case 0:
                // 리뷰 찾기
                HeritageReviewEntity heritageReviewEntity = heritageReviewRepository.findByHeritageReviewSeq(reportTypeSeq);
                // 문화재 이름
                String heritageName = heritageRepository.findByHeritageSeq(heritageReviewEntity.getHeritageSeq()).getHeritageName();
                body += "' " + heritageName + " ' 에 작성한 리뷰가 신고처리가 되어 삭제되었습니다.";
                try {
                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            // 피드    
            case 1:
                // 피드 찾기
                FeedEntity feedEntity = feedRepository.findByFeedSeq(reportTypeSeq);

                // 피드 제목
                String feedTitle = feedEntity.getFeedTitle();
                body += "' " + feedTitle + " ' 피드가 신고처리가 되어 삭제되었습니다.";
                try {
                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            // 사용자
            case 2:
                body += "아이디가 탈퇴처리 되었습니다.";
                try {
                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
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

        return true;
    }

    public boolean passReport(int reportSeq) {
        AdminReportEntity adminReportEntity = adminRepository.findByReportSeq(reportSeq);
        // 이미 처리된 신고이면 false
        if(adminReportEntity.getIsSolved() == 'Y') return false;

        // 취소 처리 -> 완료 처리하기
        adminReportEntity.setIsSolved('Y');

        adminRepository.save(adminReportEntity);
        return true;
    }
}
