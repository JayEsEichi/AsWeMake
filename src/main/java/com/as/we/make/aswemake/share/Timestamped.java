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

  @JsonFormat(timezone = "Asia/Seoul")
  @CreatedDate
  private LocalDateTime createdAt;

  @JsonFormat(timezone = "Asia/Seoul")
  @LastModifiedDate
  private LocalDateTime modifiedAt;

}
