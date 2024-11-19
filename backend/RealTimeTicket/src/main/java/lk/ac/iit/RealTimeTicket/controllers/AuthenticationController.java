/*package lk.ac.iit.RealTimeTicket.controllers;

import lk.ac.iit.RealTimeTicket.dto.SignUpRequestDTO;
import lk.ac.iit.RealTimeTicket.dto.UserDTO;
import lk.ac.iit.RealTimeTicket.services.Authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/customer/signup")
    public ResponseEntity<?>SignUpCustomer(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        if(authService.presentByEmail(signUpRequestDTO.getEmail())) {
            return new ResponseEntity<>("Customer already exists with this Email", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createdUser=authService.SignUpCustomer(signUpRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
    @PostMapping("/vendor/signup")
    public ResponseEntity<?>SignUpVendor(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        if(authService.presentByEmail(signUpRequestDTO.getEmail())) {
            return new ResponseEntity<>("Vendor already exists with this Email", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO createdUser=authService.SignUpCustomer(signUpRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}*/
