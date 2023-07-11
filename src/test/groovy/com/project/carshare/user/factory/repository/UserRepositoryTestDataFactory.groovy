package com.project.carshare.user.factory.repository

import com.project.carshare.user.context.admin.dto.VerificationRequest
import com.project.carshare.user.domain.User
import com.project.carshare.user.domain.enums.Role
import com.project.carshare.user.domain.enums.UserStatus
import com.project.carshare.user.domain.enums.VerificationStatus

trait UserRepositoryTestDataFactory {

    private User getUser(String email, UserStatus userStatus, VerificationStatus verificationStatus) {
        new User(
                TestDataConstants.USER_ID,
                "Tadeusz",
                "Testowy",
                email,
                "password",
                Role.USER,
                userStatus,
                verificationStatus,
                null,
                null,
                null,
                "4273849237689",
                null
        )
    }

    User getTestUser() {
        getUser("test@test.pl", UserStatus.ACTIVE, VerificationStatus.NOT_VERIFIED)
    }

    User getTestUserArchived() {
        getUser("test@test.pl", UserStatus.ARCHIVED, VerificationStatus.NOT_VERIFIED)
    }

    User getTestUserLocked() {
        getUser("test@test.pl", UserStatus.LOCKED, VerificationStatus.NOT_VERIFIED)
    }

    User getTestUserVerified() {
        getUser("test@test.pl", UserStatus.LOCKED, VerificationStatus.VERIFIED)

    }

    List<User> getTestUserList() {
        [getUser("test@test.pl", UserStatus.ACTIVE, VerificationStatus.PENDING),
         getUser("adam@małysz.pl", UserStatus.LOCKED, VerificationStatus.NOT_VERIFIED),
         getUser("usuniety@user.pl", UserStatus.ARCHIVED, VerificationStatus.NOT_VERIFIED)]
    }

    VerificationRequest getPositiveVerification() {
        new VerificationRequest(true)
    }

    VerificationRequest getNegativeVerification() {
        new VerificationRequest(false)
    }
}