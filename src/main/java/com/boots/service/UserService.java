package com.boots.service;

import com.boots.entity.Friends;
import com.boots.entity.Images;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.FriendsRepository;
import com.boots.repository.ImagesRepository;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ImagesRepository imagesRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public Images toImageEntity(MultipartFile file) throws IOException {
        Images images = new Images();
        images.setName(file.getName());
        images.setOriginalFileName(file.getOriginalFilename());
        images.setContentType(file.getContentType());
        images.setSize(file.getSize());
        images.setBytes(file.getBytes());
        return images;
    }
    public void saveProduct(MultipartFile file) throws IOException {
        Images images = null;
        if(file.getSize()!=0) {
            images = toImageEntity(file);
        }
        User user1 = userRepository.findUserById(12L);
        images.setUser(user1);
        imagesRepository.save(images);
        user1.setPreviewImageId(82L);
        userRepository.save(user1);
    }
    public User get_user(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    UserService userService;
    public List<User> get_all_friends(){
        User user = userService.get_user();
        List<Friends> list1 = friendsRepository.findAllByUser1(user);
        List<Friends> list2 = friendsRepository.findAllByUser2(user);
        List<User>itog_list = new ArrayList<>();
        for(Friends friends:list1){
            if (friends.getType_friends()==3)
                itog_list.add(friends.getUser2());
        }
        for(Friends friends:list2){
            if (friends.getType_friends()==3)
                itog_list.add(friends.getUser1());
        }
        return itog_list;
    }
}
