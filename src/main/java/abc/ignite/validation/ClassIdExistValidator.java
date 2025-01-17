package abc.ignite.validation;

import org.springframework.beans.factory.annotation.Autowired;

import abc.ignite.repository.ClassRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClassIdExistValidator implements ConstraintValidator<ClassIdExist, String> {

    @Autowired
    private ClassRepository classRepository;
    private boolean exist = true;
    
    @Override
    public void initialize(ClassIdExist constraintAnnotation) {
        exist = constraintAnnotation.exist();
    }

    @Override
    public boolean isValid(String classId, ConstraintValidatorContext context) {
        return classRepository.findById(classId).isPresent() == exist;
    }
}