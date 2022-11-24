package de.backend.features.schoolyear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, String> {

    List<SchoolYear> findByGroupId(String groupId);
}
