package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.auth.AuthenticationDTO;
import com.blog.cesmusic.data.DTO.v1.auth.RegisterDTO;
import com.blog.cesmusic.data.DTO.v1.auth.TokenDTO;
import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
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
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<TokenDTO> login(@RequestBody AuthenticationDTO data) {
        userService.verifyPendingUser(data);

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
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
                            description = "Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(data));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @PutMapping(
            value = "/accept/{login}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Accept a new user",
            description = "Accept a new user",
            tags = {"Authentication"},
            method = "GET"
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
    public ResponseEntity<UserDTO> acceptUser(@PathVariable String login) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.acceptUser(login));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @DeleteMapping(value = "/recuse/{login}")
    @Operation(
            summary = "Recuse a new user",
            description = "Recuse a new user",
            tags = {"Authentication"},
            method = "DELETE"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
            }
    )
    public ResponseEntity<?> recuseUser(@PathVariable String login) {
        userService.recuseUser(login);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
