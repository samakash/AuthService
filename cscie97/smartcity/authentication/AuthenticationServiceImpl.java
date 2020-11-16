package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.model.utils.SmartCityUtils;

import java.util.*;

public class AuthenticationServiceImpl implements AuthenticationService{

    public static AuthenticationServiceImpl instance;
    private HashMap<String, User> usersMap;
    private HashMap<String, Entitlement> entitlementsMap;
    private HashMap<String, AuthToken> authTokensMap;



    private AuthenticationServiceImpl() {
        this.usersMap = new HashMap<>();
        this.entitlementsMap = new HashMap<>();
        this.authTokensMap = new HashMap<>();
    }

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthenticationServiceImpl();
        }
        return instance;
    }

    public HashMap<String, User> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(HashMap<String, User> usersMap) {
        this.usersMap = usersMap;
    }

    public HashMap<String, Entitlement> getEntitlementsMap() {
        return entitlementsMap;
    }

    public void setEntitlementsMap(HashMap<String, Entitlement> entitlementsMap) {
        this.entitlementsMap = entitlementsMap;
    }

    public HashMap<String, AuthToken> getAuthTokensMap() {
        return authTokensMap;
    }

    public void setAuthTokensMap(HashMap<String, AuthToken> authTokensMap) {
        this.authTokensMap = authTokensMap;
    }

    @Override
    public void createPermission(String id, String name, String description) {
        try{
            if(entitlementsMap.containsKey(id)){
                throw new AuthenticationException("Create Permission failed","Permission Id already used");
            }else{
                Entitlement permission = new Permission(id,name,description);
                Visitor visitor = new InventoryVisitor();
                visitor.visit(permission);
                System.out.println("New permission is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void createRole(String id, String name, String description) {
        try{
            if(entitlementsMap.containsKey(id)){
                throw new AuthenticationException("Create Role failed","Role Id already used");
            }else {
                Entitlement role = new Role(id, name, description);
                Visitor visitor = new InventoryVisitor();
                visitor.visit(role);
                System.out.println("New Role is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addPermissionToRole(String permissionId, String roleId) {
        try{
            if(entitlementsMap.containsKey(permissionId) && entitlementsMap.containsKey(roleId)){
                Entitlement role = entitlementsMap.get(roleId);
                role.getEntitlementsList().add(entitlementsMap.get(permissionId));
                Visitor visitor = new InventoryVisitor();
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
        try{
            if(usersMap.containsKey(id)){
                throw new AuthenticationException("Add User Failed","User Id already exists");
            } else{
                User user = new User(id,name);
                Visitor visitor = new InventoryVisitor();
                visitor.visit(user);
                System.out.println("New User is created Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addUserCredential(String userId, String credentialId, String credentialType, String password) {
        try {
            if (usersMap.containsKey(userId)) {
                if (credentialType.equals("password")) {
                    Credential credential = new Login(userId + credentialType, userId, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new InventoryVisitor());
                    System.out.println("New Password credentials added to user: " + userId);
                } else if (credentialType.equals("faceprint")) {
                    Credential credential = new FacePrint(userId + credentialType, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new InventoryVisitor());
                    System.out.println("New faceprint credentials added to user: " + userId);
                } else if (credentialType.equals("voiceprint")) {
                    Credential credential = new VoicePrint(userId + credentialType, hashCredential(password));
                    User user = usersMap.get(userId);
                    user.getCredentials().add(credential);
                    user.accept(new InventoryVisitor());
                    System.out.println("New voiceprint credentials added to user: " + userId);
                } else {
                    throw new AuthenticationException("Add User Credentials failed", "Authentication type is not supported");
                }
            } else {
                throw new AuthenticationException("Add credentials to user failed","User is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void addRoleToUser(String userId, String roleId) {
        try{
            if(entitlementsMap.containsKey(roleId) && usersMap.containsKey(userId)) {
                User user = usersMap.get(userId);
                Entitlement role = entitlementsMap.get(roleId);
                user.getEntitlements().add(role);
                user.accept(new InventoryVisitor());
                System.out.println("Role "+roleId+" has been added to User "+userId+" Successfully");
            } else {
                throw new AuthenticationException("Add Role to User failed","role id or user id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void createResourceRole(String id, String name, String description) {
        try{
            if(entitlementsMap.containsKey(id)){
                throw new AuthenticationException("Create Resource Role failed","Role Id already used");
            }else {
                Entitlement resourceRole = new ResourceRole(id, name, description);
                Visitor visitor = new InventoryVisitor();
                visitor.visit(resourceRole);
                System.out.println("New Resource Role is added Id: "+id);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void addResourceToResourceRole(String roleId, String resourceId, String resourceDescription) {
        try{
            if(entitlementsMap.containsKey(roleId)){
                if(entitlementsMap.get(roleId) instanceof ResourceRole){
                    Resource resource = new Resource(resourceId,resourceDescription);
                    Entitlement role =  entitlementsMap.get(roleId);
                    role.getResources().add(resource);
                    role.accept(new InventoryVisitor());
                    System.out.println("Resource "+resourceId+" has been added to resource Role "+roleId);
                } else{
                    throw new AuthenticationException("Add Resource to ResourceRole Failed","Role id is not for a Resource Role");
                }
            } else {
                throw new AuthenticationException("Add Resource to ResourceRole Failed","Role Id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }

    }

    @Override
    public AuthToken login(String username, String password) {
        AuthToken authToken = null;
        try{
            if(usersMap.containsKey(username)) {
                User user = usersMap.get(username);

                //get timestamo and add 1 hour to it as an expiration time for the autToken
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 1);
                Date exptime = calendar.getTime();

                for (Credential credential: user.getCredentials()) {
                    if (credential instanceof Login){
                        if(((Login)credential).getUsername().equals(username)
                                && ((Login)credential).getPassword().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new InventoryVisitor());
                            user.setAuthToken(authToken);
                            user.accept(new InventoryVisitor());
                            System.out.println("New authToken is generated for user: "+user.getId());
                            break;
                        } else {
                            throw new AuthenticationException("Login Failed","please check username and password");
                        }
                    } else if (credential instanceof FacePrint){
                        if(((FacePrint)credential).getFacePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new InventoryVisitor());
                            user.setAuthToken(authToken);
                            user.accept(new InventoryVisitor());
                            System.out.println("New authToken is generated for user: "+user.getId());
                        }
                    } else if (credential instanceof VoicePrint){
                        if(((VoicePrint)credential).getVoicePrintValue().equals(hashCredential(password))){
                            authToken = new AuthToken(username, username, exptime.toString(), TokenState.active);
                            authToken.setUser(user);
                            authToken.accept(new InventoryVisitor());
                            user.setAuthToken(authToken);
                            user.accept(new InventoryVisitor());
                            System.out.println("New authToken is generated for user: "+user.getId());
                        }
                    }
                }
            } else{
                throw new AuthenticationException("Login Failed","please check username and password");
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
            return authToken;
        }



    @Override
    public void logout(String userId) {
        try{
            if(usersMap.containsKey(userId)) {
                User user = usersMap.get(userId);
                user.getAuthToken().setState(TokenState.expired);
                user.accept(new InventoryVisitor());
            } else{
                throw new AuthenticationException("logout Failed","username is not found");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }
    }


    @Override
    public boolean checkAccess(String authToken, Object requiredPermission, String resource) {
        Visitor checkAccessVisitor = new CheckAccessVisitor();
        try {
            if(authTokensMap.containsKey(authToken)){

                //verify authtoken is active status and not expired
                AuthToken authToken1 = authTokensMap.get(authToken);
                authToken1.accept(checkAccessVisitor);
                //verify user
                User user = authToken1.getUser();
                user.accept(checkAccessVisitor);
                //verify required permission
                if(requiredPermission instanceof String){
                    Entitlement entitlement = entitlementsMap.get(requiredPermission);
                    entitlement.accept(checkAccessVisitor);

                } else if (requiredPermission instanceof List){

                    List<String> ls = (List<String>) requiredPermission;
                    for(String val : ls){
                        Entitlement entitlement2 = entitlementsMap.get(val);
                        entitlement2.accept(checkAccessVisitor);
                    }
                }
                //verify required resources if provided
                if(!resource.equals("")){
                    Resource resource1 = new Resource(resource,resource);
                    resource1.accept(checkAccessVisitor);
                }
                //throw exception if required permissions not found for this user
                if(!checkAccessVisitor.isHasPermissions()){
                    throw new AuthenticationException("Authentication Failed","User doesn't have required permissions");
                }

            }else{
                throw new AuthenticationException("Authentication Failed","authToken is not valid");
            }
        } catch (AuthenticationException ex){
            System.out.println(ex);
        }

        //get check access visitor results
        boolean validStatus = checkAccessVisitor.isValidStatus();
        boolean validExpTime = checkAccessVisitor.isValidExpTime();
        boolean hasPermissions = checkAccessVisitor.isHasPermissions();
        boolean hasResources = checkAccessVisitor.isHasResources();


        if (resource.equals("")){
            if(validStatus && validExpTime && hasPermissions){
                //expire authToken after each use to simplify the command line demo
                logout(checkAccessVisitor.getUserUnderValidation().getId());
                return true;
            } else {
                return false;
            }
        } else{
            if(validStatus && validExpTime && hasPermissions && hasResources){
                //expire authToken after each use to simplify the command line demo
                logout(checkAccessVisitor.getUserUnderValidation().getId());
                return  true;
            } else{
                return false;
            }
        }

    }

    private String hashCredential(String value)  {
        String out = null;
        try {
            out = SmartCityUtils.encrypt(value);
        } catch (Exception e){
            System.out.println(e);
        }
        return out;
    }

}
