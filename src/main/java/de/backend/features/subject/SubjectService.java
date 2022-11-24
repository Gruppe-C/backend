package de.backend.features.subject;

import de.backend.exception.EntityNotFoundException;
import de.backend.features.group.Group;
import de.backend.features.group.dto.CreateGroupDto;
import de.backend.features.group.dto.GroupDto;
import de.backend.features.subject.dto.CreateSubjectDto;
import de.backend.features.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository repository;

    @Autowired
    public SubjectService(final SubjectRepository subjectRepository) {
        this.repository = subjectRepository;
    }

    public Subject create(Subject subject) throws IllegalAccessException {
        if (subject.getName() == null || subject.getName().isBlank() || subject.getName().isEmpty()) {
            throw new IllegalAccessException("Name must be set!");
        }
        return repository.save(subject);
    }

    public Subject get(final String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject not found"));
    }

    public List<Subject> getListBySchoolYear(final String schoolYearId) {
        return repository.findBySchoolYear_Id(schoolYearId);
    }

    public Subject update(Subject subject) throws IllegalAccessException {
        if (subject.getName() == null || subject.getName().isBlank() || subject.getName().isEmpty()) {
            throw new IllegalAccessException("Name must be set!");
        }
        return repository.save(subject);
    }

    public void delete(final String id) {
        repository.deleteById(id);
    }
}
