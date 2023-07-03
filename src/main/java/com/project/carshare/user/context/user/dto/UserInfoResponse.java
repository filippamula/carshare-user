package com.project.carshare.user.context.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.carshare.user.domain.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("verified")
    private VerificationStatus verified;

    @JsonProperty("drivingLicense")
    private byte[] drivingLicense;

    @JsonProperty("pesel")
    private String pesel;

    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("phoneNo")
    private String phoneNo;
}
