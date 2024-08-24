package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.services.auth.PendingUserService;
import com.blog.cesmusic.services.auth.TokenService;
import com.blog.cesmusic.services.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "https://musical-blog-cesmac.vercel.app"})
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for registration and login to the system")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private PendingUserService pendingUserService;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    @Operation(
            summary = "Log into the system",
            description = "Log into the system",
            tags = {"Authentication"},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TokenDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<TokenDTO> login(@RequestBody AuthenticationDTO data) {
        userService.checkUserStatus(data);

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Register in the system",
            description = "Register in the system",
            tags = {"Authentication"},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PendingUserDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<PendingUserDTO> register(@Valid @RequestBody RegisterDTO data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pendingUserService.register(data));
    }

    @PutMapping(
            value = "/activate/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Activate a inactive user",
            description = "Activate a inactive user",
            tags = {"Authentication"},
            method = "PUT"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
            }
    )
    public ResponseEntity<UserDTO> acceptUser(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.acceptUser(id));
    }

    @PutMapping(
            value = "/validate-login-code/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Validate a login code",
            description = "Validate a login code",
            tags = {"Authentication"},
            method = "PUT"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
            }
    )
    public ResponseEntity<UserDTO> validateLoginCode(@PathVariable String code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pendingUserService.validateLoginCode(code));
    }
}
