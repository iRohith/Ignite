package abc.ignite.service;

import org.springframework.stereotype.Service;

import abc.ignite.dto.ClassRequestDTO;
import abc.ignite.dto.ClassResponseDTO;
import abc.ignite.entity.ClassEntity;
import abc.ignite.exception.ClassServiceException;
import abc.ignite.repository.ClassRepository;
import abc.ignite.util.ValueMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ClassService {
    private ClassRepository classRepository;

    @Transactional
    public ClassResponseDTO createNewClass(ClassRequestDTO classRequestDTO) throws ClassServiceException {
        ClassResponseDTO classResponseDTO = null;

        try {
            ClassEntity classEntity = ValueMapper.convertToEntity(classRequestDTO);

            ClassEntity classResult = classRepository.save(classEntity);
            classResponseDTO = ValueMapper.convertToDTO(classResult);
            log.debug("ClassService:createNewClass received response from Database {}", ValueMapper.jsonAsString(classResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting class to database , Exception message {}", ex.getMessage());
            throw new ClassServiceException("Exception occurred while creating a new class");
        }

        return classResponseDTO;
    }
}
