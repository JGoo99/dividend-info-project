package com.example.dividend.persist.repository;

import com.example.dividend.persist.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  Optional<MemberEntity> findByUsername(String userName);

  boolean existsByUsername(String username);
}
