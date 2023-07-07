package com.as.we.make.aswemake.share;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

  // 엔티티 데이터 생성 시 자동 저장될 생성일자
  @JsonFormat(timezone = "Asia/Seoul")
  @CreatedDate
  private LocalDateTime createdAt;

  // 엔티티 데이터 생성 시 자동 저장될 수정일자
  @JsonFormat(timezone = "Asia/Seoul")
  @LastModifiedDate
  private LocalDateTime modifiedAt;

}
