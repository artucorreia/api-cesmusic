package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.auth.UserDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "https://musical-blog-cesmac.vercel.app"})
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Endpoints to manager users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping(
            value = "/inactive",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find all inactive users",
            description = "Find all inactive users",
            tags = {"User"},
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
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<List<UserDTO>> findInactiveUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findInactiveUsers());
    }
}
