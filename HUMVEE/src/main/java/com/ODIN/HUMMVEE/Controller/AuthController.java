package com.ODIN.HUMMVEE.Controller;

import com.ODIN.HUMMVEE.DTO.AuthRequest;
import com.ODIN.HUMMVEE.Service.JWTTokenGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    JWTTokenGenerationService jwtTokenGenerationService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> generateJWTToken(@RequestBody AuthRequest authRequest){
        return jwtTokenGenerationService.generateJWTToken(authRequest);
    }

}
