package ac.cursor.umurimohub.auth.controller;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.auth.dto.AuthResponse;
import ac.cursor.umurimohub.auth.dto.LoginRequest;
import ac.cursor.umurimohub.auth.dto.RegisterRequest;
import ac.cursor.umurimohub.auth.dto.VerifyOtpRequest;
import ac.cursor.umurimohub.auth.model.User;
import ac.cursor.umurimohub.security.JwtUtil;
import ac.cursor.umurimohub.auth.service.OtpService;
import ac.cursor.umurimohub.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, 
                         JwtUtil jwtUtil, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.otpService = otpService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new CEO", description = "Register a new CEO user and send OTP to email for verification")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        // Check if username already exists
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Username is already taken"));
        }

        // Check if email already exists
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Email is already in use"));
        }

        // Create new CEO user (not verified yet)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(User.Role.CEO);
        user.setEmailVerified(false);

        userService.createUser(user);

        // Generate and send OTP
        otpService.generateAndSendOtp(request.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful. Please check your email for OTP verification code.", 
                      request.getEmail()));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and send OTP to email for verification")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByUsername(request.getUsername());
            
            // Generate and send OTP
            otpService.generateAndSendOtp(user.getEmail());

            return ResponseEntity.ok(ApiResponse.success("Login successful. Please check your email for OTP verification code.", 
                  user.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid username or password"));
        }
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify OTP", description = "Verify OTP code and return JWT token")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            // Verify OTP
            otpService.verifyOtp(request.getEmail(), request.getOtpCode());

            // Get user and update email verification status
            User user = userService.getUserByEmail(request.getEmail());
            user.setEmailVerified(true);
            userService.updateUser(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
            AuthResponse authResponse = new AuthResponse(token, user);

            return ResponseEntity.ok(ApiResponse.success("Email verified successfully. You are now logged in.", authResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get currently authenticated user details")
    public ResponseEntity<ApiResponse<User>> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("User retrieved", user));
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout current user (client should discard the token)")
    public ResponseEntity<ApiResponse<Void>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("Logout successful. Please discard your token.", null));
    }
}
