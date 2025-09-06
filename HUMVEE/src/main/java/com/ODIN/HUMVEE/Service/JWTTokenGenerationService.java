package com.ODIN.HUMMVEE.Service;

import com.ODIN.HUMMVEE.DTO.AuthRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import com.ODIN.HUMMVEE.utilities.JWTGenerationUtility;

@Service
public class JWTTokenGenerationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerationUtility jwtGenerationUtility;

    Logger log = LoggerFactory.getLogger(JWTTokenGenerationService.class);

    public ResponseEntity<String> generateJWTToken(AuthRequest authRequest) {

        ResponseEntity<String>result = null;

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            result = new ResponseEntity<>(jwtGenerationUtility.generateToken(authRequest.getUsername()), HttpStatus.OK);
            log.info("JWT token to be generated for user with username : {}" , authRequest.getUsername());

        }
        catch (Exception e){
            result = new ResponseEntity<>("Invalid username or password" , HttpStatus.UNAUTHORIZED);
            log.error("Some error in generating JWT token, {}" , e.toString());
        }
        return result;

    }
}
