package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Entitlement> getEntitlementList() {
        return entitlementList;
    }

    public void setEntitlementList(List<Entitlement> entitlementList) {
        this.entitlementList = entitlementList;
    }

    @Override
    public void createPermission(String id, String name, String description) {
        Entitlement permission = new Permission(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(permission);

    }

    @Override
    public void createRole(String id, String name, String description) {
        Entitlement role = new Role(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(role);
    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {

    }

    @Override
    public void createUser(String id, String name) {
        User user = new User(id,name);
        Visitor visitor = new InventoryAdd();
        visitor.visit(user);
    }

    @Override
    public void addUserCredential(String userId, String credentialId, String credentialType, String password) {

    }

    @Override
    public void addRoleToUser(String userId, String roleId) {

    }

    @Override
    public void createResourceRole(String id, String name, String description) {
        Entitlement resourceRole = new ResourceRole(id,name,description);
        Visitor visitor = new InventoryAdd();
        visitor.visit(resourceRole);
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
