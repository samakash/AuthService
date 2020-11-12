package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;

import java.util.stream.IntStream;

public class InventoryUpdate implements Visitor {
    @Override
    public void visit(User user) {
        try{
            AuthenticationService authenticationService = AuthenticationService.getInstance();

            if (authenticationService.getUserList().stream().anyMatch(ti -> ti.getId() == user.getId())) {
                int index = IntStream.range(0, authenticationService.getUserList().size())
                        .filter(i -> authenticationService.getUserList().get(i).getId().equals(user.getId())).findFirst().orElse(-1);

                authenticationService.getUserList().get(index).setId(user.getId());
                authenticationService.getUserList().get(index).setName(user.getName());
                authenticationService.getUserList().get(index).setAuthToken(user.getAuthToken());
                authenticationService.getUserList().get(index).setCredentials(user.getCredentials());
                authenticationService.getUserList().get(index).setEntitlements(user.getEntitlements());

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

            if (authenticationService.getEntitlementList().stream().anyMatch(ti -> ti.getId() == entitlement.getId())) {

                int index = IntStream.range(0, authenticationService.getEntitlementList().size())
                        .filter(i -> authenticationService.getEntitlementList().get(i).getId().equals(entitlement.getId())).findFirst().orElse(-1);

                authenticationService.getEntitlementList().get(index).setId(entitlement.getId());
                authenticationService.getEntitlementList().get(index).setName(entitlement.getName());
                authenticationService.getEntitlementList().get(index).setDescription(entitlement.getDescription());

                System.out.println("Update Entitlement is completed successfully - Entitlement Id: "+entitlement.getId());

            } else {
                throw new AuthenticationException("Update User Failed","User id is not found");
            }
        } catch (AuthenticationException e){
            System.out.println(e);
        }
    }
}
