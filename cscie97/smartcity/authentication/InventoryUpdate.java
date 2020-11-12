package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

import java.util.stream.IntStream;

public class InventoryUpdate implements Visitor {
    @Override
    public void visit(User user) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();

            if (authenticationService.getUserList().containsKey(user.getId()))  {

                authenticationService.getUserList().get(user.getId()).setId(user.getId());
                authenticationService.getUserList().get(user.getId()).setName(user.getName());
                authenticationService.getUserList().get(user.getId()).setAuthToken(user.getAuthToken());
                authenticationService.getUserList().get(user.getId()).setCredentials(user.getCredentials());
                authenticationService.getUserList().get(user.getId()).setEntitlements(user.getEntitlements());

                System.out.println("Update user is completed successfully - User Id: "+user.getId());

            } else {
                throw new AuthenticationException("Update User Failed","User id is not found");
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

                authenticationService.getEntitlementList().get(entitlement.getId()).setId(entitlement.getId());
                authenticationService.getEntitlementList().get(entitlement.getId()).setName(entitlement.getName());
                authenticationService.getEntitlementList().get(entitlement.getId()).setDescription(entitlement.getDescription());

                System.out.println("Update Entitlement is completed successfully - Entitlement Id: "+entitlement.getId());

            } else {
                throw new AuthenticationException("Update Entitlement Failed","entitlement id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }
}
