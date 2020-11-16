package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

import java.util.HashMap;

/**
 * Authentication service interface
 */
public interface AuthenticationService {
    /**
     * Singletone instance getter for the authentication service
     * @return
     */
    static AuthenticationServiceImpl getInstance(){
        return AuthenticationServiceImpl.getInstance();
    }

    /**
     * This method is used to create a new permssion
     * @param id permission id
     * @param name permission name
     * @param description permission description
     */
    void createPermission(String id, String name, String description);

    /**
     * this method is used to create a new role
     * @param id role id
     * @param name role name
     * @param description role description
     */
    void createRole(String id, String name, String description);

    /**
     * this method is used to add a permssion to a role
     * @param permissionId permission id
     * @param roleId role id
     */
    void addPermissionToRole(String permissionId, String roleId);

    /**
     * this method is used to create  new userUnderValidation
     * @param id userUnderValidation id
     * @param name userUnderValidation name
     */
    void createUser(String id, String name);

    /**
     * this method is for adding new credentials to a userUnderValidation
     * @param userId userUnderValidation id
     * @param credentialId credential id
     * @param credentialType credential type (password, faceprint or voiceprint)
     * @param password password value
     */
    void addUserCredential(String userId, String credentialId, String credentialType, String password);

    /**
     * this method is used to assign a role to a userUnderValidation
     * @param userId userUnderValidation id
     * @param roleId role id
     */
    void addRoleToUser(String userId, String roleId);

    /**
     * this method is used to create a new resource role
     * @param id resource role id
     * @param name role name
     * @param description role description
     */
    void createResourceRole(String id, String name, String description);

    /**
     * THis method is used to create a new resource and add it to a resource role
     * @param roleId role id
     * @param resourceId resource id
     * @param resourceDescription resource desfription
     */
    void addResourceToResourceRole(String roleId, String resourceId,String resourceDescription);

    /**
     * this merhod is used to login a userUnderValidation or authenticate a userUnderValidation in order to generate an authToken
     * @param username userUnderValidation name (userUnderValidation id)
     * @param password userUnderValidation password
     * @return active authToken that cen be used to check access
     */
    AuthToken login(String username,String password);

    /**
     * method to logout a userUnderValidation. this method will expire the authtoken of the userUnderValidation
     * @param userId
     */
    void logout(String userId);

    /**
     * this method will check access for a userUnderValidation. it will verify if the AuthToken is active and the userUnderValidation has sufficient permissions. If resource field is provided,
     * the method will search if the resource exist in any associated resource role for this use.
     * @param authToken authToken id value
     * @param requiredPermission the required permission to check if the userUnderValidation has it
     * @param resource the resource id if its linked to any exiting resource role
     * @return boolean value where true means access granted
     */
    boolean checkAccess(String authToken, Object requiredPermission, String resource);

    /**
     * getter method for all users in the inventory
     * @return
     */
    HashMap<String, User> getUsersMap();

    /**
     * getter method for all entitlements in the inventory
     * @return
     */
    HashMap<String, Entitlement> getEntitlementsMap();

    /**
     * getter method for all authTokens in the inventory
     * @return
     */
    HashMap<String, AuthToken> getAuthTokensMap();



}
