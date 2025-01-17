package abc.ignite.validation;

import org.springframework.beans.factory.annotation.Autowired;

import abc.ignite.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameExistValidator implements ConstraintValidator<UsernameExist, String> {
    @Autowired
    private UserRepository userRepository;
    private boolean exist = true;
    
    @Override
    public void initialize(UsernameExist constraintAnnotation) {
        exist = constraintAnnotation.exist();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userRepository.findById(username).isPresent() == exist;
    }
}