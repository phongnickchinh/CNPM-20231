package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserDetailsService {

//    public ResponseEntity<?> register(String name, String username, String password);
//    public ResponseEntity<?> login(String username, String password);
//    public List<User> getAllById(Integer Id);

    private final UserRepository repository;

    @Autowired
    private  PasswordEncoder encoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(com.example.cnpm.quanlythuchinhatro.service.UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        // Tạo một danh sách authorities dựa trên roles của User
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
//
//        // Trả về một đối tượng UserDetails mới
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return "User Added Successfully";
    }
}
