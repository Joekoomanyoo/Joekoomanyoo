package com.project.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.project.common.dto.Push.FcmHistoryDto;
import com.project.common.dto.Push.FcmMessage;
import com.project.common.dto.Push.FcmRequestDto;
import com.project.common.entity.Push.FcmHistoryEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.FcmHistoryMapper;
import com.project.common.repository.Push.FcmHistoryRepository;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private final FcmHistoryRepository fcmHistoryRepository;


    private final String API_URL = "https://fcm.googleapis.com/v1/projects/d102-95c18/messages:send";

    /**
     * FCM에 push 요청을 보낼 때 인증을 위해 Header에 포함시킬 AccessToken 생성
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        // GoogleApi를 사용하기 위해 oAuth2를 이용해 인증한 대상을 나타내는객체
        GoogleCredentials googleCredentials = GoogleCredentials
                // 서버로부터 받은 service key 파일 활용
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                // 인증하는 서버에서 필요로 하는 권한 지정
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        String token = googleCredentials.getAccessToken().getTokenValue();

        System.out.println(token);
        return token;
    }

    /**
     * FCM 알림 메시지 생성
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonProcessingException
     */

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage.Notification noti = new FcmMessage.Notification(title, body, null);
        FcmMessage.Message message = new FcmMessage.Message(noti, targetToken);
        FcmMessage fcmMessage = new FcmMessage(false, message);

        // 직렬화
        return objectMapper.writeValueAsString(fcmMessage);
    }


    /**
     * targetToken에 해당하는 device로 FCM 푸시 알림 전송
     * @param targetToken
     * @param title
     * @param body
     * @throws IOException
     */
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                // 전송 토큰 추가
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("응답 : " + response.body().string());
    }

    // 전체 알림 세팅
    public boolean pushSetting(int userSeq, char setting) {
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);
        // 만약 사용자가 없을 경우
        if(userEntity == null){
            return false;
        }else{
            // 알림 설정으로 요청을 받은 경우
            if(setting == 'Y'){
                userEntity.setPushSettingStatus('Y');
                userRepository.save(userEntity);
            }
            // 알림 미설정으로 요청을 받은 경우
            else if(setting == 'N'){
                userEntity.setPushSettingStatus('N');
                userRepository.save(userEntity);
            }
            return true;
        }
    }

    // 알림 기록하기
    public boolean createHistory(FcmHistoryDto fcmHistoryDto) {
        // 사용자가 없으면 false
        if(userRepository.findByUserSeq(fcmHistoryDto.getUserSeq()) == null){
            return false;
        }else{
            FcmHistoryEntity fcmHistoryEntity = FcmHistoryMapper.MAPPER.toEntity(fcmHistoryDto);
            fcmHistoryEntity.setPushSeq(0);
            fcmHistoryEntity.setPushCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            fcmHistoryRepository.save(fcmHistoryEntity);
            return true;
        }
    }

    // 알림 리스트 보기
    public List<FcmHistoryDto> listHistory(int userSeq) {
        List<FcmHistoryEntity> list = fcmHistoryRepository.findAllByUserSeq(userSeq);

        // 알림이 없거나 사용자가 없는 경우
        if(list.size() == 0 || userRepository.findByUserSeq(userSeq) == null){
            return null;
        }else{
            List<FcmHistoryDto> listDto = new ArrayList<>();
            for(FcmHistoryEntity fcmHistoryEntity : list){
                listDto.add(FcmHistoryMapper.MAPPER.toDto(fcmHistoryEntity));
            }
            return listDto;
        }
    }

    // 내 알림 설정 조회
    public String settingInfo(int userSeq) {
        UserEntity userEntity = userRepository.findByUserSeq(userSeq);

        // 사용자가 없는 경우
        if(userEntity==null){
            return null;
        }else{
            return String.valueOf(userEntity.getPushSettingStatus());
        }
    }
}
