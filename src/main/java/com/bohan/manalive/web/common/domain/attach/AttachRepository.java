package com.bohan.manalive.web.common.domain.attach;

import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachRepository extends JpaRepository<Attach, Long>, CrudRepository<Attach, Long> {

    @Query(" SELECT new com.bohan.manalive.web.common.dto.AttachDto (a.attNo, a.category, a.superKey, a.filename, a.extension, a.uuid, a.url, a.createDate, a.modifiedDate) " +
            " FROM Attach a " +
            " WHERE a.superKey = :seq AND a.category = :category " +
            " ORDER BY a.attNo ASC")
    List<AttachDto> getAttachListBySuperKey(@Param("seq") Long seq, @Param("category") String category);

}

