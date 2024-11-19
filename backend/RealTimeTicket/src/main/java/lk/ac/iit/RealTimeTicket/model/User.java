package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;

//import lk.ac.iit.RealTimeTicket.dto.UserDTO;
//import lk.ac.iit.RealTimeTicket.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="users")
    @Data
    @DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
    public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String phone;

    public User(String name, String email) {
    }

    public User() {

    }
}
        //private UserRole role;

        // Constructors
      /*  public User() {
        }

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }*/

    /*public UserDTO getDto() {
        UserDTO userDto = new UserDTO();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setRole(role);

        return userDto;
    }
}*/

