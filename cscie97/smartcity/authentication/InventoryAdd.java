package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.Visitor;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

public class InventoryAdd implements Visitor {

    @Override
    public void visit(User user) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();

            if (authenticationService.getUserList().containsKey(user.getId())) {
                throw new AuthenticationException("Add User Failed","User id already exists");
            } else {
                authenticationService.getUserList().put(user.getId(),user);
                System.out.println("New user is created successfully - User Id: "+user.getId());
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }

    }

    @Override
    public void visit(Entitlement entitlement) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();

            if (authenticationService.getEntitlementList().containsKey(entitlement.getId())) {
                throw new AuthenticationException("Add Entitlement failed"," Entitlement id already exists");
            } else {
                authenticationService.getEntitlementList().put(entitlement.getId(),entitlement);
                System.out.println("New entitlement is created successfully - "+entitlement);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }

    @Override
    public void visit(AuthToken authToken) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();

            if (authenticationService.getAuthTokenList().containsKey(authToken.getId())) {
                throw new AuthenticationException("Add Authtoken failed"," Authtoken id already exists");
            } else {
                authenticationService.getAuthTokenList().put(authToken.getId(),authToken);
                System.out.println("New authToken is created successfully - "+authToken);
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }
}
