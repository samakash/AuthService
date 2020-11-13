package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthenticationServiceImpl implements AuthenticationService{

    public static AuthenticationServiceImpl instance;
    private HashMap<String, User> userList;
    private HashMap<String, Entitlement> entitlementList;



    private AuthenticationServiceImpl() {
        this.userList = new HashMap<>();
        this.entitlementList = new HashMap<>();
    }

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }

    public HashMap<String, User> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }

    public HashMap<String, Entitlement> getEntitlementList() {
        return entitlementList;
    }

    public void setEntitlementList(HashMap<String, Entitlement> entitlementList) {
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
        try{
            if(entitlementList.containsKey(permissionId) && entitlementList.containsKey(roleId)){
                Entitlement role = entitlementList.get(roleId);
                role.getEntitlementsList().add(entitlementList.get(permissionId));
                Visitor visitor = new InventoryUpdate();
                role.accept(visitor);
                System.out.println("Added Permission "+permissionId+" to Role "+roleId+ " successfully");
            } else{
                throw new AuthenticationException("add Permission to Role failed","permission id or role id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }

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
