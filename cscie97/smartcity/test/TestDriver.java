package cscie97.smartcity.test;
import cscie97.smartcity.model.cmdProcessor.CommandProcessor;

public class TestDriver {

    public static void main(String[] args) {

        CommandProcessor commandProcessor = new CommandProcessor();
        //take the args and give to CommandProcessor for commands processing
        commandProcessor.processCommandFile(args[0]);

    }

}

