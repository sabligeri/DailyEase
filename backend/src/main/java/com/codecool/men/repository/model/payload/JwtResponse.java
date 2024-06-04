package com.codecool.men.repository.model.payload;

import java.util.List;

public record JwtResponse(String jwt, long id, String userName, List<String> roles) {}
