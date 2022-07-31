package com.project.common.repository;


import com.project.common.entity.GroupAttributeEntity;
import com.project.common.entity.GroupMemberEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
//	boolean existsByStudyAndParticipant(Study study, User user);

  //  void deleteByStudy(Study study);

//    intcountByStudyId(long studyId);

//    void deleteBygroupIdAndmemberId(long groupSeq, long memberSeq);
//
//  //  Set<StudyParticipant> findAllByStudy(Study study);
//
////    List<StudyParticipant> findAllByParticipant(User participant);
//
//    Optional<GroupMemberEntity> findBygroupIdAndmemberId(long groupSeq, long userSeq);
  
    
}
