/*package lk.ac.iit.RealTimeTicket.services.Authentication;

import lk.ac.iit.RealTimeTicket.Repo.UserRepo;
import lk.ac.iit.RealTimeTicket.dto.SignUpRequestDTO;
import lk.ac.iit.RealTimeTicket.dto.UserDTO;
import lk.ac.iit.RealTimeTicket.enums.UserRole;
import lk.ac.iit.RealTimeTicket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    public UserDTO SignUpCustomer(SignUpRequestDTO signUpRequestDTO) {
        User user = new User();

        user.setName(signUpRequestDTO.getName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPhone(signUpRequestDTO.getPhone());
        user.setPassword(signUpRequestDTO.getPassword());

        user.setRole(UserRole.CUSTOMER);

        return userRepo.save(user).getDto();

    }

    public Boolean presentByEmail(String email) {
        return userRepo.findFirstByEmail(email) != null;
    }

    public UserDTO SignUpVendor(SignUpRequestDTO signUpRequestDTO) {
        User user = new User();

        user.setName(signUpRequestDTO.getName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPhone(signUpRequestDTO.getPhone());
        user.setPassword(signUpRequestDTO.getPassword());

        user.setRole(UserRole.CUSTOMER);

        return userRepo.save(user).getDto();
    }
}*/
