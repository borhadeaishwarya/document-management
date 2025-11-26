package com.document.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.document.entity.Document;

@Repository
public interface DocDao extends JpaRepository<Document, String> {

}
