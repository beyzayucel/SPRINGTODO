package com.haratres.todo.controller;
import com.haratres.todo.entity.UserImage;
import com.haratres.todo.repository.UserImagesRepository;
import com.haratres.todo.repository.UsersRepository;
import com.haratres.todo.security.TokenProvider;
import com.haratres.todo.dto.*;
import com.haratres.todo.entity.Users;
import com.haratres.todo.services.cloudinary.CloudinaryService;
import com.haratres.todo.services.user.UsersService;
import com.haratres.todo.util.UserUtil;
import com.haratres.todo.validators.LoginValidators;
import com.haratres.todo.validators.UserUpdateValidator;
import com.haratres.todo.validators.UsersValidators;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UsersController extends BaseController {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    UsersValidators usersValidators;

    @Autowired
    LoginValidators loginValidators;

    @Autowired
    UserUpdateValidator userUpdateValidator;

    @Autowired
    UserUtil userUtil;

    @Autowired
    UserImagesRepository userImageRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CloudinaryService cloudinaryService;

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody UsersDto usersDto) {
        validate(usersDto, "user", loginValidators);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersDto.getEmail(), usersDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UsersDto usersDto) {
        validate(usersDto,"user",usersValidators);
        usersService.registerUser(usersDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/update/me")
    public ResponseEntity<Users> updateUser(@RequestBody UsersDto usersDto, HttpServletRequest request) {
        validate(usersDto,"user",userUpdateValidator);
        Users users=userUtil.tokenProcess(request);
        Users user = modelMapper().map(usersDto, Users.class);
        usersService.updateUser(user,users.getEmail());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload-images")
    public ResponseEntity<Void> uploadImages(@RequestParam("images") MultipartFile[] images,HttpServletRequest request) throws IOException {
        Users user=userUtil.tokenProcess(request);
        for (MultipartFile image : images) {
            String imageUrl = cloudinaryService.uploadImage(image);
            UserImage userImage = new UserImage(new Date(), imageUrl, user);
            userImageRepository.save(userImage);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-images")
    public ResponseEntity<List<ImageDto>> getMyImages(HttpServletRequest request) {
        Users user=userUtil.tokenProcess(request);
        List<UserImage> images = userImageRepository.findByUsers(user);
        List<ImageDto> imageDto=images.stream().map(s->modelMapper().map(s, ImageDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(imageDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current-user")
    public ResponseEntity<GetUserDto> getCurrentUser(HttpServletRequest request) {
        Users users=userUtil.tokenProcess(request);
        String email=users.getEmail();
        Users user=usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User didn't find."));
        GetUserDto getUserDto=modelMapper().map(user,GetUserDto.class);
        return ResponseEntity.ok(getUserDto);
    }
}
