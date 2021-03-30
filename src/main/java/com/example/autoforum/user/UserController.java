package com.example.autoforum.user;

import com.example.autoforum.comment.CommentController;
import com.example.autoforum.post.Post;
import com.example.autoforum.post.PostService;
import com.example.autoforum.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = "/user", produces = "application/json")
    public ResponseEntity<?> getUser(@RequestParam int id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPicture(user.getPicture());
            userDTO.setCreatedAt(user.getCreatedAt());
            return ResponseEntity.ok().header("Content-Type", "application/json").body(userDTO);
        }
    }

//    @GetMapping(path = "/user/{id}", produces = "application/json")
//    public ResponseEntity<?> getUser(@PathVariable int id) {
//        User user = userService.getUser(id);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(user.getId());
//            userDTO.setUsername(user.getUsername());
//            userDTO.setEmail(user.getEmail());
//            userDTO.setPicture(user.getPicture());
//            userDTO.setCreatedAt(user.getCreatedAt());
//            return ResponseEntity.ok().header("Content-Type", "application/json").body(userDTO);
//        }
//    }
//    @GetMapping(path = "/user/{id}", produces = "application/json")
//    public ResponseEntity<?> getUser(@PathVariable int id) {
//        if (userService.getUser(id) == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok().header("Content-Type", "application/json").body(userService.getUser(id));
//        }
//    }

    @GetMapping(path = "/login", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> getUserLogin(@RequestPart String usernameLogin, @RequestPart String passwordLogin) {

        List<User> allUsersCompare = userService.getAllUsers();

        for (var userInstance: allUsersCompare) {
            if (userInstance.getUsername().equals(usernameLogin) && userInstance.getPassword().equals(passwordLogin) && userInstance.getEnabled()) {
                return ResponseEntity.ok().header("Content-Type", "application/json").body(userInstance.getId());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/user", consumes = "multipart/form-data")
    public ResponseEntity<?> addUser(@RequestPart User user, @RequestPart MultipartFile picture) throws URISyntaxException, IOException {
        
        List<User> allUsersCompare = userService.getAllUsers();

        for (var userInstance: allUsersCompare) {
            if (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername())) {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        user.setPicture(picture.getBytes());
        LOGGER.info(user.toString());

        URI location = new URI("http://www.concretepage.com/");
        userService.addUser(user);
        return ResponseEntity.created(location).build();
        
//        if (user == null) {
//            return ResponseEntity.unprocessableEntity().build();
//        } else {
//            user.setPicture(picture.getBytes());
//            LOGGER.info(user.toString());
//
//            URI location = new URI("http://www.concretepage.com/");
//            userService.addUser(user);
//            return ResponseEntity.created(location).build();
//        }
    }

    @PostMapping(path = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> addUserRegister(@RequestPart User user, @RequestPart MultipartFile picture) throws URISyntaxException, IOException {

        List<User> allUsersCompare = userService.getAllUsers();

        for (var userInstance: allUsersCompare) {
            if (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername())) {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        user.setPicture(picture.getBytes());
        LOGGER.info(user.toString());

        URI location = new URI("http://www.concretepage.com/");
        userService.addUser(user);
        return ResponseEntity.created(location).build();
    }

//    @PostMapping(path = "/user", consumes = "application/json")
//    public ResponseEntity<?> addUser(@RequestBody User user) throws URISyntaxException {
//        if (user == null) {
//            return ResponseEntity.unprocessableEntity().build();
//        } else {
//            URI location = new URI("http://www.concretepage.com/");
//            userService.addUser(user);
//            return ResponseEntity.created(location).build();
//        }
//    }

    @PutMapping(path = "/user", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestPart User user, @RequestPart MultipartFile picture) throws IOException {
//        User existingUser = userService.getUser(id);
//        if (existingUser == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            user.setPicture(picture.getBytes());
//            LOGGER.info(user.toString());
//            user.setId(id);
//            userService.updateUser(id, user);
//            return ResponseEntity.ok().build();
//        }

        List<User> allUsersCompare = userService.getAllUsers();

        for (var userInstance: allUsersCompare) {
            if (!(userInstance.getId() == id) && (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername()))) {
                return ResponseEntity.unprocessableEntity().build();
            }
            if (userInstance.getId() == id) {
                user.setCreatedAt(userInstance.getCreatedAt());
            }
        }

        user.setPicture(picture.getBytes());
        LOGGER.info(user.toString());
        user.setId(id);
        userService.updateUser(id, user);
        return ResponseEntity.ok().build();
    }

//    @PutMapping(path = "/user/{id}", consumes = "application/json")
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id) {
//        User existingUser = userService.getUser(id);
//        if (existingUser == null) {
//            return ResponseEntity.notFound().build();
//        } else if (user.getEmail().equals(existingUser.getEmail())) {
//            return ResponseEntity.badRequest().build();
//        } else {
//            userService.updateUser(id, user);
//            return ResponseEntity.ok().build();
//        }
//    }

    @DeleteMapping(path = "/user")
    public ResponseEntity<?> deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // potom zmenit na POST
    @PostMapping(path = "/favorite")
    public ResponseEntity<?> makeFavorite(@RequestParam int userId, @RequestParam int postId) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getDetails();
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);

        user.getFavoritePosts().add(post);
        userService.updateUser(userId, user);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/favorites")
    public ResponseEntity<?> getFavorites(@RequestParam int userId) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(userId);

        return ResponseEntity.ok().header("Content-Type", "application/json").body(user.getFavoritePosts());
    }

    @DeleteMapping(path = "/favorite")
    public ResponseEntity<?> deleteFavorite(@RequestParam int userId, @RequestParam int postId) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);

        user.getFavoritePosts().remove(post);
        userService.updateUser(userId, user);

        return ResponseEntity.noContent().build();
    }


//    //PROFILES - VIEW
//    @RequestMapping("/user/{id}")
//    public String viewProfile(Model model, @PathVariable String id) {
//        model.addAttribute("userId", UserService.getPerson(Integer.parseInt(id)));
//        return "admin/profiles/view";
//    }
}
