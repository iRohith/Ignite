package abc.ignite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import abc.ignite.entity.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, String> {
    
}
