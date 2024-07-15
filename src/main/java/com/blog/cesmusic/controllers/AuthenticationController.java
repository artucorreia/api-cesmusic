package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.auth.AuthenticationDTO;
import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
import com.blog.cesmusic.data.DTO.v1.auth.TokenDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserResponseDTO;
import com.blog.cesmusic.exceptions.auth.LoginAlreadyUsedException;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.services.auth.TokenService;
import com.blog.cesmusic.services.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for registration and login to the system")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
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
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request", content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden", content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Error", content = @Content
                    )
            }
    )
    public ResponseEntity<TokenDTO> login(@RequestBody AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
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
                            description = "Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request", content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden", content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Error", content = @Content
                    )
            }
    )
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterDTO data) {
        if (userService.findByLogin(data.getLogin()) != null) throw new LoginAlreadyUsedException("Login already in use");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(data));
    }
}
