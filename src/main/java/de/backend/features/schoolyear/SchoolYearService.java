package de.backend.features.schoolyear;

import de.backend.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearService {

    private final SchoolYearRepository repository;

    public SchoolYearService(SchoolYearRepository repository) {
        this.repository = repository;
    }

    public SchoolYear create(SchoolYear schoolYear) {
        if (schoolYear.getStartYear() >= schoolYear.getEndYear()) {
            throw new IllegalArgumentException("Start year must be before end year");
        }
        return this.repository.save(schoolYear);
    }

    public SchoolYear get(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("SchoolYear not found"));
    }

    public List<SchoolYear> getList(String groupId) {
        return this.repository.findByGroupId(groupId);
    }

    public SchoolYear update(SchoolYear schoolYear) {
        if (schoolYear.getStartYear() >= schoolYear.getEndYear()) {
            throw new IllegalArgumentException("Start year must be before end year");
        }
        return this.repository.save(schoolYear);
    }

    public void delete(String id) {
        this.repository.deleteById(id);
    }
}
