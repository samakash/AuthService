package cscie97.smartcity.model.cmdProcessor;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.service.ModelService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * The CommandProcessor is a utility class for feeding the model service with a set of operations, using command syntax.
 */
public class CommandProcessor {

	private ModelService modelService;
	private LedgerService ledgerService;
	private AuthenticationService authenticationService;
	private int lineNumber = 0;

	/**
	 * contructure of the command processor will get the instance of the model service interface
	 */
	public CommandProcessor() {
		this.modelService = ModelService.getInstance();
		this.ledgerService = LedgerService.getInstance();
		this.authenticationService = AuthenticationService.getInstance();
	}

	/**
	 * Method to process a command line string
	 * @param command takes command as string
	 */
	public void processCommand(String command) {
		String arr[] = command.split(" ");
		lineNumber++;
		try{
			if(!arr[0].startsWith("#") && !arr[0].isEmpty()){
				switch (arr[0]) {
					case "define":
						switch (arr[1]){
							case "city":
								String cityId = extractCmdValue(command,"city");
								String cityName = extractCmdValue(command,"name");
								String accountAddress = extractCmdValue(command,"account");
								float lat = Float.parseFloat(extractCmdValue(command,"lat"));
								float _long = Float.parseFloat(extractCmdValue(command,"long"));
								float radius = Float.parseFloat(extractCmdValue(command,"radius"));
								modelService.createCity("",cityId,cityName,accountAddress,lat,_long,radius);
								break;

							case "street-sign":
								cityId = extractCmdValue(command,"city");
								String deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								boolean enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String displayText = extractCmdValue(command,"text");
								modelService.createStreetSign("",cityId,deviceId,accountAddress,lat,_long,enabled,displayText);
								break;

							case "info-kiosk":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String imgUrl = extractCmdValue(command,"image");
								String redirectingUrl = extractCmdValue(command,"redirectingURL");
								modelService.createInfoKiosk("",cityId,deviceId,accountAddress,lat,_long,enabled,imgUrl,redirectingUrl);

								break;
							case "street-light":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								int brightness = Integer.parseInt(extractCmdValue(command,"brightness"));
								modelService.createStreetLight("",cityId,deviceId,accountAddress,lat,_long,enabled,brightness);

								break;
							case "parking-space":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								double rate = Double.parseDouble(extractCmdValue(command,"rate"));
								modelService.createParkingSpace("",cityId,deviceId,accountAddress,lat,_long,enabled,rate);

								break;
							case "robot":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String activity = extractCmdValue(command,"activity");
								modelService.createRobot("",cityId,deviceId,accountAddress,lat,_long,enabled,activity);

								break;
							case "vehicle":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String type = extractCmdValue(command,"type");
								int capacity = Integer.parseInt(extractCmdValue(command,"capacity"));
								double fee = Double.parseDouble(extractCmdValue(command,"fee"));
								modelService.createVehicle("",cityId,deviceId,accountAddress,lat,_long,enabled,type,capacity,fee);

								break;
							case "resident":
								cityId = extractCmdValue(command,"city");
								String personId = extractCmdValue(command,"id");
								String name = extractCmdValue(command,"name");
								String bioId = extractCmdValue(command,"bio-metric");
								String phone = extractCmdValue(command,"phone");
								String role = extractCmdValue(command,"role");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								accountAddress = extractCmdValue(command,"account");
								modelService.createResident("",cityId,personId,name,bioId,phone,role,lat,_long,accountAddress);

								break;
							case "visitor":
								cityId = extractCmdValue(command,"city");
								personId = extractCmdValue(command,"id");
								bioId = extractCmdValue(command,"bio-metric");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								modelService.createVisitor("",cityId,personId,bioId,lat,_long);

								break;
								default:
									throw new CommandProcessorException(arr[0],"Define command is invalid. Please verify command format",lineNumber);
					}
						break;
					case "update":
						switch (arr[1]){
							case "street-sign":
								String cityId = extractCmdValue(command,"city");
								String deviceId = extractCmdValue(command,"id");
								String accountAddress = extractCmdValue(command,"account");
								boolean enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String displayText = extractCmdValue(command,"text");
								modelService.updateStreetSign("",cityId,deviceId,accountAddress,enabled,displayText);

								break;
							case "info-kiosk":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String img = extractCmdValue(command,"image");
								String redirectingUrl = extractCmdValue(command,"redirectingURL");
								modelService.updateInfoKiosk("", cityId, deviceId, accountAddress, enabled, img, redirectingUrl);

								break;
							case "street-light":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								int brightness = Integer.parseInt(extractCmdValue(command,"brightness"));
								modelService.updateStreetLight("",cityId,deviceId,accountAddress,enabled,brightness);

								break;
							case "parking-space":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								double rate = Double.parseDouble(extractCmdValue(command,"rate"));
								modelService.updateParkingSpace("",cityId,deviceId,accountAddress,enabled,rate);

								break;
							case "robot":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								float lat = Float.parseFloat(extractCmdValue(command,"lat"));
								float _long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								String activity = extractCmdValue(command,"activity");
								modelService.updateRobot("",cityId,deviceId,accountAddress,lat,_long,enabled,activity);

								break;
							case "vehicle":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								accountAddress = extractCmdValue(command,"account");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								enabled = Boolean.valueOf(extractCmdValue(command,"enabled"));
								activity = extractCmdValue(command,"activity");
								double fee = Double.parseDouble(extractCmdValue(command,"fee"));
								modelService.updateVehicle("",cityId, deviceId, accountAddress, lat, _long, enabled, activity, fee);

								break;
							case "resident":
								cityId = extractCmdValue(command,"city");
								String personId = extractCmdValue(command,"id");
								String name = extractCmdValue(command,"name");
								String bioId = extractCmdValue(command,"bio-metric");
								String phone = extractCmdValue(command,"phone");
								String role = extractCmdValue(command,"role");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								accountAddress = extractCmdValue(command,"account");
								modelService.updateResident("", cityId, personId, name, bioId, phone, role, lat, _long, accountAddress);

								break;
							case "visitor":
								cityId = extractCmdValue(command,"city");
								personId = extractCmdValue(command,"id");
								bioId = extractCmdValue(command,"bio-metric");
								lat = Float.parseFloat(extractCmdValue(command,"lat"));
								_long = Float.parseFloat(extractCmdValue(command,"long"));
								modelService.updateVisitor("", cityId, personId, bioId, lat, _long);

								break;
							default:
								throw new CommandProcessorException(arr[0],"Update command is invalid. Please verify command format",lineNumber);
						}
						break;
					case "create":
						switch (arr[1]){
							case "sensor-event":
								String cityId = extractCmdValue(command,"city");
								String deviceId = extractCmdValue(command,"id");
								String type = extractCmdValue(command,"type");
								String value = extractCmdValue(command,"value");
								String subject = extractCmdValue(command,"subject");
								modelService.createSensorEvent("",cityId,deviceId,type,value,subject);

								break;
							case "sensor-output":
								cityId = extractCmdValue(command,"city");
								deviceId = extractCmdValue(command,"id");
								type = extractCmdValue(command,"type");
								value = extractCmdValue(command,"value");
								modelService.createSensorOutput("",cityId,deviceId,type,value);

								break;
							default:
								throw new CommandProcessorException(arr[0],"Create event command is invalid. Please verify command format",lineNumber);
						}
						break;
					case "show":
						switch (arr[1]){
							case "city":
								String cityId = extractCmdValue(command,"city");
								if (modelService.showCity("", cityId) != null) {
									System.out.println(modelService.showCity("",cityId));
								}
								break;
							case "device":
								cityId = extractCmdValue(command,"city");
								String deviceId = extractCmdValue(command,"id");
								if(modelService.showDevice("",cityId,deviceId) != null){
									System.out.println(modelService.showDevice("",cityId,deviceId));
								}
								break;
							case "person":
								cityId = extractCmdValue(command,"city");
								String personId = extractCmdValue(command,"id");
								if(modelService.showPerson("",cityId,personId) != null){
									System.out.println(modelService.showPerson("",cityId,personId));
								}
								break;
							default:
								throw new CommandProcessorException(arr[0],"Show command is invalid. Please verify command format",lineNumber);
						}
						break;
					case "create-ledger":
						String name = extractCmdValue(command,"create-ledger");
						String description = extractCmdValue(command,"description");
						String seed = extractCmdValue(command,"seed");
						ledgerService.createLedger(name,description,seed);
						break;
					case "create-account":
						String accountAddress = extractCmdValue(command,"create-account");
						ledgerService.createAccount(accountAddress);
						break;
					case "get-account-balance":
						accountAddress = extractCmdValue(command,"get-account-balance");
						ledgerService.getAccountBalance(accountAddress);
						break;
					case "process-transaction":
						//process-transaction 1 amount 1000 fee 10 note "fund account" payer master receiver mary
						String txnId = extractCmdValue(command,"process-transaction");
						String amount = extractCmdValue(command,"amount");
						String fee = extractCmdValue(command,"fee");
						String note = extractCmdValue(command,"note");
						String payer = extractCmdValue(command,"payer");
						String receiver = extractCmdValue(command,"receiver");
						ledgerService.processTransaction(txnId, Integer.parseInt(amount),Integer.parseInt(fee),note,payer,receiver);
						break;
					case "get-account-balances":
						ledgerService.getAccountBalances();
						break;
					case "get-block":
						String blockNumber = extractCmdValue(command,"get-block");
						ledgerService.getBlock(Integer.parseInt(blockNumber));
						break;
					case "get-transaction":
						txnId = extractCmdValue(command,"get-transaction");
						ledgerService.getTransaction(txnId);
						break;
					case "validate":
						ledgerService.validateLedger();
						break;
					case "define_permission":
						//define_permission auth_user_admin name "User Administrator" description "Create, Update, Delete Users"
						System.out.println("processing "+ command);
						String perId = extractCmdValue(command,"define_permission");
						String userId = extractCmdValue(command,"name");
						description = extractCmdValue(command,"description");
						authenticationService.createPermission(perId,userId,description);
						break;
					case "define_role":
						//define_role adult_role name "Adult Role" description "Has all permissions of an adult"
						System.out.println("processing "+ command);
						String roleid = extractCmdValue(command,"define_role");
						String roleName = extractCmdValue(command,"name");
						description = extractCmdValue(command,"description");
						authenticationService.createRole(roleid,roleName,description);
						break;
					case "add_permission_to_role":
						//add_permission_to_role role admin_role permission auth_user_admin
						System.out.println("processing "+ command);
						roleid = extractCmdValue(command," role");
						perId = extractCmdValue(command,"permission");
						authenticationService.addPermissionToRole(perId,roleid);
						break;
					case "create_user":
						//create_user resident_1 name "resident adult Jane"
						System.out.println("processing "+ command);
						 userId =  extractCmdValue(command,"create_user");
						 name = extractCmdValue(command,"name");
						 authenticationService.createUser(userId,name);
						break;
					case "add_user_credential":
						System.out.println("processing "+ command);
						//add_user_credential resident_1 type voiceprint value jane-voiceprint
						userId = extractCmdValue(command,"add_user_credential");
						String type = extractCmdValue(command,"type");
						String value = extractCmdValue(command,"value");
						authenticationService.addUserCredential(userId,userId+type,type,value);
						break;
					case "add_role_to_user":
						System.out.println("processing "+ command);
						//add_role_to_user resident_1 role adult_role
						userId = extractCmdValue(command,"add_role_to_user");
						roleid = extractCmdValue(command,"role");
						authenticationService.addRoleToUser(userId,roleid);
						break;
					case "define_resource_role":
						System.out.println("processing "+ command);
						//define_resource_role city1_public_administrator_resource_role name public_administrator_role description "city1 admin"
						roleid = extractCmdValue(command,"define_resource_role");
						name = extractCmdValue(command,"name");
						description = extractCmdValue(command,"description");
						authenticationService.createResourceRole(roleid,name,description);
						break;
					case "add_resource_to_resource_role":
						System.out.println("processing "+ command);
						//add_resource_to_resource_role city1_public_administrator_resource_role resource city description "the entire smart city"
						roleid = extractCmdValue(command,"add_resource_to_resource_role");
						String resourceId = extractCmdValue(command,"resource");
						description = extractCmdValue(command,"description");
						authenticationService.addResourceToResourceRole(roleid,resourceId,description);

						break;
					default:
						throw new CommandProcessorException(arr[0],"Command is not supported. Please verify command format",lineNumber);
				}
			}
		} catch (NumberFormatException e){
			System.out.println("Command line processing failed at line # "+lineNumber+". Please verify data formatting. ");
		} catch (IllegalArgumentException e){
			System.out.println("Command line processing failed at line # "+lineNumber+". Please verify data formatting. ");
		}
		catch (CommandProcessorException e){
			System.out.println(e);
		}

	}

	/**
	 * Method that read a file of commands and run the command line by line
	 * @param fileName the file name in the run directory
	 */
	public void processCommandFile(String fileName) {
		try
		{
			File file = new File(fileName);    //creates a new file instance
			FileReader fr=new FileReader(file);   //reads the file
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
			String line;
			while((line=br.readLine())!=null)
			{
				processCommand(line);
			}
			fr.close();    //closes the stream and release the resources
			System.out.println("reading file is completed.");
		}
		catch(IOException e)
		{
			System.out.println("File is not found or not readable. Please check script file.");
		}

	}

	/**
	 * This method is used to extract a certian value from the command line
	 * @param cmdLine command line string value
	 * @param value desired value to be extracted
	 * @return String of the extracted value form the command line
	 */
	public String extractCmdValue(String cmdLine, String value){
		String extractVal = Arrays.asList(cmdLine.replaceAll("^.*?"+value+" ", "")
				.split(" .*?("+value+"|$)")).get(0);
		Character firstChar = extractVal.charAt(0);
		if (firstChar.equals('\"')){
			String extractSentence = "";
			for (int i= cmdLine.indexOf(value)+value.length()+2; i<cmdLine.length(); i++){
				if(cmdLine.charAt(i) == '\"') break;
				extractSentence+=cmdLine.charAt(i);
			}
			return extractSentence;
		} else{
			return extractVal;
		}

	}

	}



