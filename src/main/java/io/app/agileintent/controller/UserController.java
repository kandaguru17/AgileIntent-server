package io.app.agileintent.controller;

import io.app.agileintent.domain.ResetPassword;
import io.app.agileintent.domain.User;
import io.app.agileintent.exceptions.UserProfileException;
import io.app.agileintent.model.UsernameModel;
import io.app.agileintent.security.AuthenticationRequest;
import io.app.agileintent.security.AuthenticationResponse;
import io.app.agileintent.security.JwtTokenProvider;
import io.app.agileintent.service.CustomUserDetailService;
import io.app.agileintent.service.EmailConfirmationService;
import io.app.agileintent.service.ErrorMapService;
import io.app.agileintent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static io.app.agileintent.security.SecurityConstants.JWT_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorMapService errorMapService;
    @Autowired
    private JwtTokenProvider jwtProvider;
    @Autowired
    private EmailConfirmationService emailConfirmationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping({"/authenticate"})
    public ResponseEntity<?> getJwtToken(@Valid @RequestBody AuthenticationRequest authenticationRequest,
                                         BindingResult result) throws ParseException {

        ResponseEntity<Map<String, String>> errors = errorMapService.mapErrors(result);
        if (errors != null)
            return errors;

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        String jwt = jwtProvider.generateToken(auth);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(true, JWT_PREFIX + jwt);

        return new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.OK);

    }

    @PostMapping({"/register"})
    public ResponseEntity<?> authenticate(@Valid @RequestBody User user, BindingResult result) {

        ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
        if (error != null)
            return error;

        User newUser = userService.registerUser(user);
        emailConfirmationService.sendRegistrationEmail(newUser);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }

    @GetMapping({"/activate"})
    public ResponseEntity<?> activateAccount(@RequestParam String token) {

        User activatedUser = emailConfirmationService.activateAccount(token);
        return new ResponseEntity<User>(activatedUser, HttpStatus.OK);
    }


    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody UsernameModel user, BindingResult result) {

        ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
        if (error != null)
            return error;

        User foundUser = (User) customUserDetailService.loadUserByUsername(user.getUsername());
        if (foundUser == null)
            throw new UserProfileException("No such user");
        emailConfirmationService.sendPasswordResetEmail(foundUser);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);

    }

    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassWord(
            @RequestParam("token") String resetToken,
            @Validated(ResetPassword.class) @RequestBody User user, BindingResult result) {

        ResponseEntity<Map<String, String>> error = errorMapService.mapErrors(result);
        if (error != null)
            return error;

        User resetUser = emailConfirmationService.resetPassword(user, resetToken);
        return ResponseEntity.status(HttpStatus.OK).body(resetUser);
    }


    @GetMapping({"/"})
    public ResponseEntity<?> getAllUsers(Principal principal) {
        List<User> users = userService.getAllUsers(principal);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

}
