package com.example.autoforum.user;

import com.example.autoforum.comment.CommentController;
import com.example.autoforum.post.Post;
import com.example.autoforum.post.PostService;
import com.example.autoforum.role.RoleService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final UserService userService;
    private final PostService postService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, PostService postService, RoleService roleService) {
        this.userService = userService;
        this.postService = postService;
        this.roleService = roleService;
    }

    @GetMapping(path = "/user", produces = "application/json")
    public ResponseEntity<?> getUser(@RequestParam int id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(user.getId());
//            userDTO.setUsername(user.getUsername());
//            userDTO.setEmail(user.getEmail());
//            userDTO.setPicture(user.getPicture());
//            userDTO.setCreatedAt(user.getCreatedAt());
//            return ResponseEntity.ok().header("Content-Type", "application/json").body(userDTO);
            return ResponseEntity.ok().header("Content-Type", "application/json").body(user);
        }
    }

//    @GetMapping(path = "/login", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> getUserLogin(@RequestBody String usernameLogin, @RequestBody String passwordLogin) {
//
//        List<User> allUsersCompare = userService.getAllUsers();
//
//        for (var userInstance: allUsersCompare) {
//            if (userInstance.getUsername().equals(usernameLogin) && userInstance.getPassword().equals(passwordLogin) && userInstance.getEnabled()) {
//                return ResponseEntity.ok().header("Content-Type", "application/json").body(userInstance.getId());
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//    @GetMapping(path = "/login", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> getUserLogin(@RequestBody User user) {
//
//        List<User> allUsersCompare = userService.getAllUsers();
//
//        for (var userInstance: allUsersCompare) {
//            if (userInstance.getUsername().equals(user.getUsername()) && userInstance.getPassword().equals(user.getPassword()) && userInstance.getEnabled()) {
//                return ResponseEntity.ok().header("Content-Type", "application/json").body(userInstance.getId());
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getUserLogin(@RequestBody User user) {

        List<User> allUsersCompare = userService.getAllUsers();

        JSONObject jsonObject = new JSONObject();

        for (var userInstance: allUsersCompare) {
            if (userInstance.getUsername().equals(user.getUsername()) && userInstance.getPassword().equals(user.getPassword()) && userInstance.getEnabled()) {
                jsonObject.put("id", userInstance.getId());
                return ResponseEntity.ok().header("Content-Type", "application/json").body(jsonObject.toString());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/getPass", produces = "application/json")
    public ResponseEntity<?> getUserPass(@RequestParam int id) {

        User newUser = userService.getUser(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", newUser.getPassword());

        return ResponseEntity.ok().header("Content-Type", "application/json").body(jsonObject.toString());

    }


    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<?> addUserRegister(@RequestBody User user) throws URISyntaxException, IOException {

        String filename="src\\main\\resources\\static\\images\\user_default_icon.png";
        Path pathToFile = Paths.get(filename);

        byte[] bytes = Files.readAllBytes(Paths.get(pathToFile.toAbsolutePath().toString()));

        List<User> allUsersCompare = userService.getAllUsers();

        for (var userInstance: allUsersCompare) {
            if (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername())) {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        user.setEnabled(true);
        user.setRoleId(roleService.getRoleByName("user"));
        user.setPicture(bytes);
        LOGGER.info(user.toString());

        URI location = new URI("http://www.concretepage.com/");
        userService.addUser(user);

        JSONObject jsonObject = new JSONObject();
        return ResponseEntity.created(location).body(jsonObject);
    }

    @PostMapping(path = "/user", consumes = "multipart/form-data")
    public ResponseEntity<?> addUser(@RequestBody User user, @RequestBody MultipartFile picture) throws URISyntaxException, IOException {
        
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

        JSONObject jsonObject = new JSONObject();
        return ResponseEntity.created(location).body(jsonObject);
    }

    @PutMapping(path = "/changePicture", consumes = "application/json")
    public ResponseEntity<?> changePicture(@RequestBody User user) {
        User newUser = userService.getUser(user.getId());
        newUser.setPicture(user.getPicture());
        userService.updateUser(user.getId(), newUser);
        LOGGER.info("Picture changed!");

        JSONObject jsonObject  = new JSONObject();
        return ResponseEntity.ok().header("Content-Type", "application/json").body(jsonObject.toString());
    }

    @PutMapping(path = "/user", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestBody User user) {

        User newUser = userService.getUser(id);
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userService.updateUser(user.getId(), newUser);

        JSONObject jsonObject = new JSONObject();
        return ResponseEntity.ok().body(jsonObject);
    }


//    @PostMapping(path = "/register", consumes = "multipart/form-data")
//    public ResponseEntity<?> addUserRegister(@RequestPart User user, @RequestPart MultipartFile picture) throws URISyntaxException, IOException {
//
//        List<User> allUsersCompare = userService.getAllUsers();
//
//        for (var userInstance: allUsersCompare) {
//            if (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername())) {
//                return ResponseEntity.unprocessableEntity().build();
//            }
//        }
//
//        user.setPicture(picture.getBytes());
//        LOGGER.info(user.toString());
//
//        URI location = new URI("http://www.concretepage.com/");
//        userService.addUser(user);
//        return ResponseEntity.created(location).build();
//    }

//    @PostMapping(path = "/register", consumes = "application/json")
//    public ResponseEntity<?> addUserRegister(@RequestBody User user) throws URISyntaxException, IOException {
//
////        Resource resource = new ClassPathResource("user_default_icon.png");
////
////        InputStream input = resource.getInputStream();
////
////        File file = resource.getFile();
//
//        String filename="src\\main\\resources\\static\\images\\user_default_icon.png";
//        Path pathToFile = Paths.get(filename);
////        System.out.println(pathToFile.toAbsolutePath());
////
////        String filePath = "D:\\Dokumenty\\lSkola\\6. Semester\\MTAA\\Zadanie\\auto-forum\\src\\main\\resources\\static\\images\\user_default_icon.png";
//
//        // file to byte[], Path
//        byte[] bytes = Files.readAllBytes(Paths.get(pathToFile.toAbsolutePath().toString()));
//
//        List<User> allUsersCompare = userService.getAllUsers();
//
//        for (var userInstance: allUsersCompare) {
//            if (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername())) {
//                return ResponseEntity.unprocessableEntity().build();
//            }
//        }
//
//        user.setEnabled(true);
//        user.setRoleId(roleService.getRoleByName("user"));
//        user.setPicture(bytes);
//        LOGGER.info(user.toString());
//
//        URI location = new URI("http://www.concretepage.com/");
//        userService.addUser(user);
//        return ResponseEntity.created(location).build();
//    }

//    @PutMapping(path = "/user", consumes = "multipart/form-data")
//    public ResponseEntity<?> updateUser(@RequestParam int id, @RequestPart User user, @RequestPart MultipartFile picture) throws IOException {
//
//        List<User> allUsersCompare = userService.getAllUsers();
//
//        for (var userInstance: allUsersCompare) {
//            if (!(userInstance.getId() == id) && (userInstance.getEmail().equals(user.getEmail()) || userInstance.getUsername().equals(user.getUsername()))) {
//                return ResponseEntity.unprocessableEntity().build();
//            }
//            if (userInstance.getId() == id) {
//                user.setCreatedAt(userInstance.getCreatedAt());
//            }
//        }
//
//        user.setPicture(picture.getBytes());
//        LOGGER.info(user.toString());
//        user.setId(id);
//        userService.updateUser(id, user);
//
//        JSONObject jsonObject = new JSONObject();
//        return ResponseEntity.ok().body(jsonObject);
//    }


    @DeleteMapping(path = "/user")
    public ResponseEntity<?> deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/favorite")
    public ResponseEntity<?> makeFavorite(@RequestParam int userId, @RequestParam int postId) {
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);

        user.getFavoritePosts().add(post);
        userService.updateUser(userId, user);


        JSONObject jsonObject = new JSONObject();
        return ResponseEntity.ok().body(jsonObject);
    }

    @GetMapping(path = "/favorites")
    public ResponseEntity<?> getFavorites(@RequestParam int userId) {
        User user = userService.getUser(userId);

        return ResponseEntity.ok().header("Content-Type", "application/json").body(user.getFavoritePosts());
    }

    @DeleteMapping(path = "/favorite")
    public ResponseEntity<?> deleteFavorite(@RequestParam int userId, @RequestParam int postId) {
        User user = userService.getUser(userId);
        Post post = postService.getPost(postId);

        user.getFavoritePosts().remove(post);
        userService.updateUser(userId, user);

        return ResponseEntity.noContent().build();
    }
}
