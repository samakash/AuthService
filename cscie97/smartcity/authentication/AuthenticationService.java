package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.controller.ControllerImpl;

public interface AuthenticationService {


    static AuthenticationServiceImpl getInstance(){
        return AuthenticationServiceImpl.getInstance();
    }

    void createPermission(String id, String name, String description);
    void createRole(String id, String name, String description);
    void addPermissionToRole(String permissionId, String roleId);
    void createUser(String id, String name);
    void addUserCredential(String userId, String credentialId, String credentialType, String password);
    void addRoleToUser(String userId, String roleId);
    void createResourceRole(String id, String name, String description);
    void addResourceToResourceRole(String roleId, String resourceId,String resourceDescription);
    AuthToken login(String username,String password);
    void logout(String userId);
    boolean checkAccess(String authToken, String requiredPermission, String resource);



}
