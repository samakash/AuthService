package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService{

    public static AuthenticationServiceImpl instance;
    private List<User> userList;
    private  List<Entitlement> entitlementList;

    private AuthenticationServiceImpl() {
        this.userList = new ArrayList<>();
        this.entitlementList = new ArrayList<>();
    }

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }

    @Override
    public void createPermission(String id, String name, String description) {
        System.out.println("Hi im create permission");

    }

    @Override
    public void createRole(String id, String name, String description) {

    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {

    }

    @Override
    public void createUser(String id, String name) {

    }

    @Override
    public void addUserCredential(String userId, String credentialId, String credentialType, String password) {

    }

    @Override
    public void addRoleToUser(String userId, String roleId) {

    }

    @Override
    public void createResourceRole(String id, String name, String description) {

    }

    @Override
    public void addResourceToResourceRole(String roleId, String resourceId, String resourceDescription) {

    }

    @Override
    public AuthToken login(String username, String password) {
        return null;
    }

    @Override
    public void logout(String userId) {

    }

    @Override
    public boolean checkAccess(String authToken, String requiredPermission, String resource) {
        return false;
    }


    private String hashCredential(String value){
        return null;
    }

}
