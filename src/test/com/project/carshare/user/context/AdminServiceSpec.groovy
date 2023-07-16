package com.project.carshare.user.context


import com.project.carshare.user.context.admin.AdminService
import com.project.carshare.user.context.user.dto.UserInfoResponse
import com.project.carshare.user.domain.UserRepository

import com.project.carshare.user.factory.repository.UserRepositoryTestDataFactory
import com.project.carshare.user.infrastructurte.utility.UserUtility
import spock.lang.Specification

class AdminServiceSpec extends Specification implements UserRepositoryTestDataFactory {

    private UserUtility userUtility
    private UserRepository userRepository
    private AdminService adminService

    def setup() {
        userUtility = Mock()
        userRepository = Mock()
        adminService = new AdminService(userRepository, userUtility)
    }

    //USER LIST
    def "Get user list when context user role is admin, expect list of users with not null id's"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findUsersByRole(_) >> Optional.of(testUserList)

        when:
        def result = adminService.userList()

        then:
        result.forEach { it.id != null }
    }

    def "Get user list when context user role is user, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> false
        0 * userRepository.findUsersByRole(_)

        when:
        adminService.userList()

        then:
        thrown RuntimeException
    }

    //DELETE USER
    def "Delete user when context user role is admin, expect no exceptions"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUser)
        1 * userRepository.save(_)

        when:
        adminService.deleteUser(testUser.id)

        then:
        noExceptionThrown()
    }

    def "Delete user when context user role is user, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> false
        0 * userRepository.save(_)

        when:
        adminService.deleteUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Delete user when user don't exist, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.empty()
        0 * userRepository.save()

        when:
        adminService.deleteUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Delete user when user is already archived, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserArchived)
        0 * userRepository.save()

        when:
        adminService.deleteUser(testUser.id)

        then:
        thrown RuntimeException
    }

    //LOCK USER
    def "Lock user when context user role is admin, expect no exceptions"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUser)
        1 * userRepository.save(_)

        when:
        adminService.lockUser(testUser.id)

        then:
        noExceptionThrown()
    }

    def "Lock user when context user role is user, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> false
        0 * userRepository.save(_)

        when:
        adminService.lockUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Lock user when user don't exist, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.empty()
        0 * userRepository.save(_)

        when:
        adminService.lockUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Lock user when user is status archived, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserArchived)
        0 * userRepository.save(_)

        when:
        adminService.lockUser(testUserArchived.id)

        then:
        thrown RuntimeException
    }

    def "Lock user when user status is locked, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserLocked)
        0 * userRepository.save(_)

        when:
        adminService.lockUser(testUserLocked.id)

        then:
        thrown RuntimeException
    }

    //UNLOCK USER
    def "Unlock user when context user role is admin, expect no exceptions"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserLocked)
        1 * userRepository.save(_)

        when:
        adminService.unlockUser(testUser.id)

        then:
        noExceptionThrown()
    }

    def "Unlock user when context user role is user, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> false
        0 * userRepository.save(_)

        when:
        adminService.unlockUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Unlock user when user don't exist, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.empty()
        0 * userRepository.save(_)

        when:
        adminService.unlockUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Unlock user when user status is active, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUser)
        0 * userRepository.save(_)

        when:
        adminService.unlockUser(testUser.id)

        then:
        thrown RuntimeException
    }

    def "Unlock user when user status is archived, expect RuntimeException"() {
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserArchived)
        0 * userRepository.save(_)

        when:
        adminService.unlockUser(testUserArchived.id)

        then:
        thrown RuntimeException
    }

    //USERS FOR VERIFICATION LIST
    def "Get list of users for verification when context user role is admin, expect list in response"(){
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findUsersByRole(_) >> Optional.of(testUserList)

        when:
        def result = adminService.userForVerificationList()

        then:
        result instanceof List
        result[0] instanceof UserInfoResponse
    }

    def "Get list of users for verification when context user role is user, expect RuntimeException"(){
        setup:
        1 * userUtility.isAdmin() >> false

        when:
        adminService.userForVerificationList()

        then:
        thrown RuntimeException
    }

    //Verify user
    def "Verify user when context user role is admin, expect no exceptions"(){
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUser)
        1 * userRepository.save(_)

        when:
        adminService.verifyUser(testUser.id, positiveVerification)

        then:
        noExceptionThrown()
    }

    def "Verify user when context user role is admin and verification is negative, expect no exceptions"(){
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUser)
        1 * userRepository.save(_)

        when:
        adminService.verifyUser(testUser.id, negativeVerification)

        then:
        noExceptionThrown()
    }

    def "Verify user when context user role is user, expect RuntimeException"(){
        setup:
        1 * userUtility.isAdmin() >> false
        0 * userRepository.save(_)

        when:
        adminService.verifyUser(testUser.id, positiveVerification)

        then:
        thrown RuntimeException
    }

    def "Verify user when user don't exist, expect RuntimeException"(){
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.empty()
        0 * userRepository.save(_)

        when:
        adminService.verifyUser(testUser.id, positiveVerification)

        then:
        thrown RuntimeException
    }

    def "Verify user when user is already verified, expect RuntimeException"(){
        setup:
        1 * userUtility.isAdmin() >> true
        1 * userRepository.findById(_) >> Optional.of(testUserVerified)
        0 * userRepository.save(_)

        when:
        adminService.verifyUser(testUser.id, positiveVerification)

        then:
        thrown RuntimeException
    }
}
