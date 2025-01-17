package abc.ignite.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import abc.ignite.dto.UserRequestDTO;
import abc.ignite.dto.UserResponseDTO;
import abc.ignite.entity.User;
import abc.ignite.exception.UserServiceException;
import abc.ignite.repository.UserRepository;
import abc.ignite.util.ValueMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    
    @Transactional
    public UserResponseDTO createNewUser(UserRequestDTO userRequestDTO) throws UserServiceException {
        UserResponseDTO userResponseDTO = null;

        try {
            User user = ValueMapper.convertToEntity(userRequestDTO);

            User userResult = userRepository.save(user);
            userResponseDTO = ValueMapper.convertToDTO(userResult);
            log.debug("UserService:createNewUser received response from Database {}", ValueMapper.jsonAsString(userResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting user to database , Exception message {}", ex.getMessage());
            throw new UserServiceException("Exception occurred while creating a new user");
        }

        return userResponseDTO;
    }

    public UserResponseDTO getUserByUsername(String username) throws UserServiceException {
        UserResponseDTO userResponseDTO = null;

        try {
            Optional<User> user = userRepository.findById(username);
            if (user.isPresent()) {
                userResponseDTO = ValueMapper.convertToDTO(user.get());
                log.debug("UserService:getUserByUsername received response from Database {}", ValueMapper.jsonAsString(userResponseDTO));
            } else {
                log.debug("UserService:getUserByUsername user '{}' not found in Database", username);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while fetching user of username '{0}' from database, Exception message {1}", username, ex.getMessage());
            throw new UserServiceException("User with username '%s' not found".formatted(username));
        }

        return userResponseDTO;
    }

    @Cacheable(value = "user")
    public List<UserResponseDTO> getUsers() throws UserServiceException {
        List<UserResponseDTO> userResponseDTOS = null;

        try {
            List<User> userList = userRepository.findAll();

            if (!userList.isEmpty()) {
                userResponseDTOS = userList.stream().map(ValueMapper::convertToDTO).toList();
            } else {
                userResponseDTOS = Collections.emptyList();
            }

            log.debug("UserService:getUsers retrieving users from database  {}", ValueMapper.jsonAsString(userResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving users from database , Exception message {}", ex.getMessage());
            throw new UserServiceException("Exception occurred while fetching all users from Database");
        }

        return userResponseDTOS;
    }

    @Cacheable(value = "user")
    public List<UserResponseDTO> getUsersByRole(String role) throws UserServiceException {
        List<UserResponseDTO> userResponseDTOS = null;

        try {
            List<User> userList = userRepository.findByRole(role);

            if (!userList.isEmpty()) {
                userResponseDTOS = userList.stream().map(ValueMapper::convertToDTO).toList();
            } else {
                userResponseDTOS = Collections.emptyList();
            }

            log.debug("UserService:getUsersByRole retrieving users by role from database  {}", ValueMapper.jsonAsString(userResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving users by role from database , Exception message {}", ex.getMessage());
            throw new UserServiceException("Exception occurred while fetching all users by role from Database");
        }

        return userResponseDTOS;
    }

    public UserResponseDTO deleteUser(String username) throws UserServiceException {
        UserResponseDTO userResponseDTO = null;

        try {
            Optional<User> user = userRepository.findById(username);

            if (user.isPresent()){
                userResponseDTO = ValueMapper.convertToDTO(user.get());
                userRepository.deleteById(username);
                log.debug("UserService:deleteUser deleted user {} from Database", username);
            } else {
                log.debug("UserService:deleteUser user '{}' not found in Database", username);
            }
        } catch (Exception ex) {
            log.error("Exception occurred while deleting user from database , Exception message {}", ex.getMessage());
            throw new UserServiceException("Exception occurred while deleting user " + username);
        }

        return userResponseDTO;
    }
}
