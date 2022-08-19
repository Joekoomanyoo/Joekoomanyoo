package com.project.common.repository.Admin;

import com.project.common.entity.Admin.AdminReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminReportEntity, Integer> {
    AdminReportEntity findByReportSeq(int reportSeq);
}
